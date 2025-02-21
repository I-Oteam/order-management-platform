package com.ioteam.order_management_platform.ai.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ioteam.order_management_platform.ai.dto.req.RecommendDesRequestDto;
import com.ioteam.order_management_platform.ai.dto.res.AnswerAiResponseDto;
import com.ioteam.order_management_platform.ai.entity.AIResponse;
import com.ioteam.order_management_platform.ai.repository.AiRepository;
import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.menu.exception.MenuException;
import com.ioteam.order_management_platform.restaurant.entity.Restaurant;
import com.ioteam.order_management_platform.restaurant.repository.RestaurantRepository;
import com.ioteam.order_management_platform.user.entity.User;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

@Service
@Transactional
public class AiService {

	@Value("${gemini.api.url}")
	private String geminiApiUrl;
	@Value("${gemini.api.key}")
	private String geminiApiKey;

	private final RestaurantRepository restaurantRepository;
	private final AiRepository aiRepository;
	private final WebClient webClient;

	public AiService(RestaurantRepository restaurantRepository, AiRepository aiRepository,
		WebClient.Builder webClient) {
		this.restaurantRepository = restaurantRepository;
		this.aiRepository = aiRepository;
		this.webClient = webClient.build();
	}

	public AnswerAiResponseDto recommendMenuDescription(UserDetailsImpl userDetails,
		RecommendDesRequestDto requestDto) {

		// 사용자 유효성 검증 (owner이면서 해당 가게 주인인지? 필요한가
		User user = userDetails.getUser();
		// 받아온 resId로 가게명, 음식점 카테고리 조회
		Restaurant restaurant = restaurantRepository.findByResIdAndDeletedAtIsNull(requestDto.getResId())
			.orElseThrow(() -> new CustomApiException(MenuException.INVALID_RESTAURANT_ID));

		// // 가게명, 메뉴명, 음식점 카테고리와 OWNER의 입력 키워드로 상품 설명 추천 요청
		String question = "가게 이름: " + restaurant.getResName()
			+ ", 메뉴 이름: " + requestDto.getRmName()
			+ ", 가게 카테고리: " + restaurant.getCategory();

		// // 입력 텍스트의 글자수를 제한 (키워드는 10자씩? 키워드 리스트로 받아올 예정인데 리스트 내부 데이터도 제한 가능한가

		// // 실제 요청 텍스트 마지막에 “답변을 최대한 간결하게 50자 이하로” 라는 텍스트를 요청시에 삽입하여 사용량을 줄이는 처리를 추가
		question += "위의 내용을 기반으로 <메뉴 설명> 내용을 추천해줘. 답변을 최대한 간결하게 50자 이하로 해줘.";

		// API 호출
		String aiAnswer = requestGemini(question);
		// OWNER의 입력 키워드의 경우 최대 5개

		// AI의 응답의 문장만 DB에 저장
		AIResponse aiResponse = RecommendDesRequestDto.toEntity(user, question, aiAnswer);
		AIResponse response = aiRepository.save(aiResponse);

		return AnswerAiResponseDto.of(response);
	}

	private String requestGemini(String question) {

		Map<String, Object> requestBody = Map.of(
			"contents", new Object[] {
				Map.of("parts", new Object[] {
					Map.of("text", question)
				})
			}
		);

		String aiAnswer = webClient.post()
			.uri(geminiApiUrl + geminiApiKey)
			.header("Content-Type", "application/json")
			.bodyValue(requestBody)
			.retrieve()
			.bodyToMono(String.class)
			.block();

		try {
			JsonNode root = new ObjectMapper().readTree(aiAnswer);
			return root.get("candidates")
				.get(0)
				.get("content")
				.get("parts")
				.get(0)
				.get("text")
				.asText();
		} catch (JsonProcessingException e) {
			throw new CustomApiException("AI 응답 파싱 중 오류 발생");
		}
	}
}

package com.ioteam.order_management_platform.ai.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import com.ioteam.order_management_platform.ai.dto.req.RecommendDesRequestDto;
import com.ioteam.order_management_platform.ai.dto.res.AnswerAiResponseDto;
import com.ioteam.order_management_platform.ai.dto.res.GeminiResponseDto;
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

		Restaurant restaurant = restaurantRepository.findByResIdAndDeletedAtIsNull(requestDto.getResId())
			.orElseThrow(() -> new CustomApiException(MenuException.INVALID_RESTAURANT_ID));

		String question = "가게 이름: " + restaurant.getResName()
			+ ", 메뉴 이름: " + requestDto.getRmName()
			+ ", 가게 카테고리: " + restaurant.getCategory().getRcName()
			+ ", 키워드: " + requestDto.getKeywords().toString();

		question += " 주어진 정보를 기반으로 <메뉴 설명> 내용을 추천해줘. 답변을 최대한 간결하게 50자 이하로 해줘.";

		System.out.println(question);
		// API 호출
		AnswerAiResponseDto aiAnswer = requestGemini(question);

		// AI의 응답 문장만 DB에 저장
		AIResponse aiResponse = RecommendDesRequestDto.toEntity(user, question, aiAnswer);
		AIResponse response = aiRepository.save(aiResponse);

		return AnswerAiResponseDto.of(response);
	}

	private AnswerAiResponseDto requestGemini(String question) {
		//TODO: exceptio 처리
		Map<String, Object> requestBody = Map.of(
			"contents", new Object[] {
				Map.of("parts", new Object[] {
					Map.of("text", question)
				})
			}
		);

		GeminiResponseDto aiAnswer = webClient.post()
			.uri(geminiApiUrl + geminiApiKey)
			.header("Content-Type", "application/json")
			.bodyValue(requestBody)
			.retrieve()
			.bodyToMono(GeminiResponseDto.class)
			.block();

		return AnswerAiResponseDto.fromGemini(aiAnswer);
	}
}

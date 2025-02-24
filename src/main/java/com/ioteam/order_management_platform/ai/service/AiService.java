package com.ioteam.order_management_platform.ai.service;

import static org.springframework.http.MediaType.*;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import com.ioteam.order_management_platform.ai.dto.req.RecommendDesRequestDto;
import com.ioteam.order_management_platform.ai.dto.res.AnswerAiResponseDto;
import com.ioteam.order_management_platform.ai.dto.res.GeminiResponseDto;
import com.ioteam.order_management_platform.ai.entity.AIResponse;
import com.ioteam.order_management_platform.ai.exception.AIException;
import com.ioteam.order_management_platform.ai.repository.AiRepository;
import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.menu.exception.MenuException;
import com.ioteam.order_management_platform.restaurant.entity.Restaurant;
import com.ioteam.order_management_platform.restaurant.repository.RestaurantRepository;
import com.ioteam.order_management_platform.user.entity.User;
import com.ioteam.order_management_platform.user.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AiService {

	@Value("${gemini.api.url}")
	private String geminiApiUrl;
	@Value("${gemini.api.key}")
	private String geminiApiKey;

	private final RestaurantRepository restaurantRepository;
	private final AiRepository aiRepository;
	private final RestClient restClient;

	public AnswerAiResponseDto recommendMenuDescription(UserDetailsImpl userDetails,
		RecommendDesRequestDto requestDto) {

		User user = userDetails.getUser();
		Restaurant restaurant = restaurantRepository.findByResIdAndDeletedAtIsNull(requestDto.getResId())
			.orElseThrow(() -> new CustomApiException(MenuException.INVALID_RESTAURANT_ID));

		if (!restaurant.isOwner(userDetails)) {
			throw new CustomApiException(MenuException.NOT_AUTHORIZED_FOR_MENU);
		}

		String question = "가게 이름: " + restaurant.getResName()
			+ ", 메뉴 이름: " + requestDto.getRmName()
			+ ", 가게 카테고리: " + restaurant.getCategory().getRcName()
			+ ", 키워드: " + requestDto.getKeywords().toString();

		question += " 주어진 정보를 기반으로 <메뉴 설명> 내용을 추천해줘. 답변을 최대한 간결하게 50자 이하로 해줘.";

		AnswerAiResponseDto aiAnswer = requestGemini(question);

		AIResponse aiResponse = RecommendDesRequestDto.toEntity(user, question, aiAnswer);
		AIResponse response = aiRepository.save(aiResponse);

		return AnswerAiResponseDto.of(response);
	}

	private AnswerAiResponseDto requestGemini(String question) {
		Map<String, Object> requestBody = Map.of(
			"contents", new Object[] {
				Map.of("parts", new Object[] {
					Map.of("text", question)
				})
			}
		);

		try {
			ResponseEntity<GeminiResponseDto> aiAnswer = restClient.post()
				.uri(geminiApiUrl + geminiApiKey)
				.contentType(APPLICATION_JSON)
				.body(requestBody)
				.retrieve()
				.toEntity(GeminiResponseDto.class);

			if (aiAnswer.getBody() == null) {
				throw new CustomApiException(AIException.INVALID_AI_RESPONSE);
			}
			return AnswerAiResponseDto.fromGemini(aiAnswer.getBody());
		} catch (Exception e) {
			throw new CustomApiException(AIException.AI_SERVICE_UNAVAILABLE);
		}
	}
}
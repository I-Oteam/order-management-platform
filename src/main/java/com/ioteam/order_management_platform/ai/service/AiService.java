package com.ioteam.order_management_platform.ai.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ioteam.order_management_platform.ai.dto.req.GeminiRequestDto;
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
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AiService {

	@Value("${gemini.api.key}")
	private String geminiApiKey;

	private final RestaurantRepository restaurantRepository;
	private final AiRepository aiRepository;
	private final GeminiAiService geminiAiService;

	public AnswerAiResponseDto recommendMenuDescription(UserDetailsImpl userDetails,
		RecommendDesRequestDto requestDto) {

		User user = userDetails.getUser();
		Restaurant restaurant = restaurantRepository.findByResIdAndDeletedAtIsNull(requestDto.getResId())
			.orElseThrow(() -> new CustomApiException(MenuException.INVALID_RESTAURANT_ID));

		if (!restaurant.isOwner(userDetails)) {
			throw new CustomApiException(MenuException.NOT_AUTHORIZED_FOR_MENU);
		}
		// todo. 질문 프롬프트 생성 부분 리팩토링
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
		GeminiRequestDto requestBody = GeminiRequestDto.of(question);

		try {
			GeminiResponseDto aiAnswer = geminiAiService.requestGemini(geminiApiKey, requestBody);

			if (aiAnswer == null) {
				throw new CustomApiException(AIException.INVALID_AI_RESPONSE);
			}
			return AnswerAiResponseDto.fromGemini(aiAnswer);
		} catch (Exception e) {
			log.info(e.getMessage());
			throw new CustomApiException(AIException.AI_SERVICE_UNAVAILABLE);
		}
	}
}
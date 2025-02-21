package com.ioteam.order_management_platform.ai.dto.res;

import java.util.UUID;

import com.ioteam.order_management_platform.ai.entity.AIResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AnswerAiResponseDto {
	private UUID arId;
	private String arAnswer;

	public static AnswerAiResponseDto of(AIResponse aiResponse) {
		return AnswerAiResponseDto
			.builder()
			.arId(aiResponse.getArId())
			.arAnswer(aiResponse.getArAnswer())
			.build();
	}
}

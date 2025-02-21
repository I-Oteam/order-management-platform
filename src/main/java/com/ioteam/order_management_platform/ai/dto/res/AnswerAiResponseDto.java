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
	private String arQuestion;
	private String arAnswer;
	private String arModel;

	public static AnswerAiResponseDto of(AIResponse aiResponse) {
		return AnswerAiResponseDto
			.builder()
			.arId(aiResponse.getArId())
			.arQuestion(aiResponse.getArQuestion())
			.arAnswer(aiResponse.getArAnswer())
			.arModel(aiResponse.getArModel())
			.build();
	}

	public static AnswerAiResponseDto fromGemini(GeminiResponseDto responseDto) {
		return AnswerAiResponseDto
			.builder()
			.arAnswer(responseDto.getCandidates().get(0)
				.getContent()
				.getParts().get(0)
				.getText().replace("\n", ""))
			.arModel(responseDto.getModelVersion())
			.build();
	}
}
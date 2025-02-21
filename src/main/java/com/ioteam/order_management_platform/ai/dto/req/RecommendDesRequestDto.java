package com.ioteam.order_management_platform.ai.dto.req;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;

import com.ioteam.order_management_platform.ai.entity.AIResponse;
import com.ioteam.order_management_platform.user.entity.User;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class RecommendDesRequestDto {

	@NotNull
	private UUID resId; // resId 받아서 가게 이름, 가게 카테고리 조회
	@NotNull
	@Length(min = 1, max = 100)
	private String rmName;
	private List<String> keywords = new ArrayList<>();

	public static AIResponse toEntity(User user, String question, String aiAnswer) {
		return AIResponse
			.builder()
			.user(user)
			.arQuestion(question)
			.arAnswer(aiAnswer)
			// .arModel()
			.build();
	}

}

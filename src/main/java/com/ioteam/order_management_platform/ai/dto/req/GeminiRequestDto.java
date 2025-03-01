package com.ioteam.order_management_platform.ai.dto.req;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GeminiRequestDto {

	private Content contents;

	@Getter
	@Builder
	public static class Content {
		private List<Part> parts;
	}

	@Getter
	@Builder
	public static class Part {
		private String text;
	}

	public static GeminiRequestDto of(String question) {
		return GeminiRequestDto.builder()
			.contents(new Content(List.of(new Part(question))))
			.build();
	}
}

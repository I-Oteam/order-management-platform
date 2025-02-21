package com.ioteam.order_management_platform.ai.dto.res;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GeminiResponseDto {
	private List<Candidate> candidates;
	private String modelVersion;

	@Getter
	public static class Candidate {
		private Content content;

		@Getter
		public static class Content {
			private List<Part> parts;

			@Getter
			public static class Part {
				private String text;
			}
		}

	}
}

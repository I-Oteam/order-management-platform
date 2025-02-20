package com.ioteam.order_management_platform.review.dto.req;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ModifyReviewRequestDto {
	@NotNull
	@Min(0)
	@Max(5)
	private Integer reviewScore;
	@NotBlank
	@Length(max = 1000)
	private String reviewContent;
	@Length(max = 100)
	private String reviewImageUrl;
	@NotNull
	private Boolean isPublic;
}

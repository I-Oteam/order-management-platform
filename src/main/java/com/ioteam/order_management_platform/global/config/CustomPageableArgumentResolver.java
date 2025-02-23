package com.ioteam.order_management_platform.global.config;

import java.util.Set;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CustomPageableArgumentResolver extends PageableHandlerMethodArgumentResolver {

	private static final Set<Integer> ALLOWED_PAGE_SIZES = Set.of(10, 30, 50); // 사이즈 제한
	private static final int DEFAULT_PAGE_SIZE = 10; // 기본값

	@Override
	public Pageable resolveArgument(MethodParameter methodParameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory) {

		Pageable pageable = super.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

		int pageSize = pageable.getPageSize();

		// 요청된 페이지 사이즈가 허용된 값이 아닐 경우 기본값으로 설정
		if (!ALLOWED_PAGE_SIZES.contains(pageSize)) {
			return PageRequest.of(pageable.getPageNumber(), DEFAULT_PAGE_SIZE, pageable.getSort());
		}

		return pageable;
	}
}

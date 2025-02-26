package com.ioteam.order_management_platform.global.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws
		ServletException, IOException {

		ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

		long start = System.currentTimeMillis();
		filterChain.doFilter(requestWrapper, responseWrapper);
		long end = System.currentTimeMillis();
		LoggingData.of(requestWrapper, responseWrapper, (end - start) / 1000.0)
			.toPrettyLog();

		responseWrapper.copyBodyToResponse();
	}

	record LoggingData(
		String requestUri,
		String method,
		int status,
		Map<String, String> headers,
		Map<String, String> queryString,
		String requestBody,
		String responseBody,
		double elapsedTime
	) {

		static LoggingData of(ContentCachingRequestWrapper requestWrapper,
			ContentCachingResponseWrapper responseWrapper, double elapsedTime) {
			return new LoggingData(
				requestWrapper.getRequestURI(),
				requestWrapper.getMethod(),
				responseWrapper.getStatus(),
				getHeaders(requestWrapper),
				getQueryParameter(requestWrapper),
				contentBody(requestWrapper.getContentAsByteArray()),
				contentBody(responseWrapper.getContentAsByteArray()),
				elapsedTime);
		}

		private static Map<String, String> getHeaders(HttpServletRequest request) {
			Map<String, String> headerMap = new HashMap<>();

			Enumeration<String> headerArray = request.getHeaderNames();
			while (headerArray.hasMoreElements()) {
				String headerName = headerArray.nextElement();
				headerMap.put(headerName, request.getHeader(headerName));
			}
			return headerMap;
		}

		private static Map<String, String> getQueryParameter(HttpServletRequest request) {
			Map<String, String> queryMap = new HashMap<>();
			request.getParameterMap()
				.forEach((key, values) -> queryMap.put(key, String.join(";", values)));
			return queryMap;
		}

		private static String contentBody(final byte[] contents) {
			return new String(contents);
		}

		private void toPrettyLog() {

			log.info("""
					     
					:::: METHOD: {}, URI: {}, STATUS: {} ::::
					> Headers: {}
					> QueryString: {}
					> Request Body: {}
					> Response Body: {}
					> Elapsed Time: {}
					""",
				method,
				requestUri,
				status,
				headers,
				queryString,
				requestBody,
				responseBody,
				elapsedTime);
		}
	}
}

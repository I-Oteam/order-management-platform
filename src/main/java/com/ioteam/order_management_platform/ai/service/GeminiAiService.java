package com.ioteam.order_management_platform.ai.service;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.ioteam.order_management_platform.ai.dto.res.GeminiResponseDto;

@FeignClient(name = "GeminiAiService", url = "${gemini.api.url}")
public interface GeminiAiService {

	@PostMapping("/")
	GeminiResponseDto requestGemini(@RequestParam("key") String apiKey, @RequestBody
	Map<String, Object> requestBody);
}

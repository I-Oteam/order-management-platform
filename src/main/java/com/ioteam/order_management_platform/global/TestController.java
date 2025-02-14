package com.ioteam.order_management_platform.global;

import com.ioteam.order_management_platform.global.dto.CommonResponse;
import com.ioteam.order_management_platform.global.exception.CustomApiException;
import com.ioteam.order_management_platform.global.exception.type.BaseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/test/{test}")
    public ResponseEntity<CommonResponse<String>> test(@PathVariable String test) {
        if (test.equals("test")) throw new CustomApiException(BaseException.INVALID_INPUT);

        return ResponseEntity.ok(new CommonResponse<>("test", "test"));
    }
}

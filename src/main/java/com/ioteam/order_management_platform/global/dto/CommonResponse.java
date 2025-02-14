package com.ioteam.order_management_platform.global.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Getter
@Setter
public class CommonResponse<T> {
    private String message;
    private T result;

    public CommonResponse(String message, T result) {
        this.message = message;
        this.result = result;
    }
}
package com.platform.config;

import com.platform.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        return Result.error("系统繁忙，请稍后重试");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result<String> handleIllegalArgument(IllegalArgumentException e) {
        return Result.error(400, e.getMessage());
    }
}

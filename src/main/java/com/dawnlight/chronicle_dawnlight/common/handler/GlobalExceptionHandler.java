package com.dawnlight.chronicle_dawnlight.common.handler;

import com.dawnlight.chronicle_dawnlight.common.Result;
import com.dawnlight.chronicle_dawnlight.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public Result<String> exceptionHandler(BaseException ex) {
        return Result.error(ex.getMessage());
    }
    @ExceptionHandler
    public Result<String> exceptionDBHandler(DataIntegrityViolationException ex) {
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler
    public Result<String> exceptionHandler(Exception ex) {
        if (ex.getMessage().contains("6379")){
            log.error("Redis未启动");
            return Result.error("Redis未启动");
        }
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }
}

package com.example.classhelperwall.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handleException(HttpServletRequest request, Exception e) {
        log.error("服务器内部错误: {}", request.getRequestURI(), e);
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", "服务器内部错误");
        response.put("timestamp", System.currentTimeMillis());
        
        return response;
    }
    
    @ExceptionHandler(RuntimeException.class)
    public Map<String, Object> handleRuntimeException(HttpServletRequest request, RuntimeException e) {
        log.error("业务异常: {}", request.getRequestURI(), e);
        
        Map<String, Object> response = new HashMap<>();
        response.put("code", HttpStatus.BAD_REQUEST.value());
        response.put("message", "业务错误: " + e.getMessage());
        response.put("timestamp", System.currentTimeMillis());
        
        return response;
    }
    
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Map<String, Object> handleMaxSizeException() {
        Map<String, Object> response = new HashMap<>();
        response.put("code", HttpStatus.BAD_REQUEST.value());
        response.put("message", "文件大小超出限制，最大支持5MB");
        response.put("timestamp", System.currentTimeMillis());
        
        return response;
    }
}
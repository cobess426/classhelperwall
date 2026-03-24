package com.example.classhelperwall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 将 /api/uploads/** 映射到物理路径 D:/class_helper_uploads/
        registry.addResourceHandler("/api/uploads/**")
                .addResourceLocations("file:D:/class_helper_uploads/");
    }
}
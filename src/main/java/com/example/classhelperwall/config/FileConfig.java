package com.example.classhelperwall.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class FileConfig {
    
    @Value("${app.upload.local-path:./uploads/}")
    private String uploadLocalPath;
    
    @Bean
    public Boolean initUploadDirectory() {
        try {
            Path uploadPath = Paths.get(uploadLocalPath);
            
            // 如果目录不存在，则创建
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                System.out.println("上传目录创建成功: " + uploadPath.toAbsolutePath());
            }
            
            // 检查目录是否可写
            File uploadDir = uploadPath.toFile();
            if (!uploadDir.canWrite()) {
                throw new RuntimeException("上传目录不可写: " + uploadPath.toAbsolutePath());
            }
            
            return true;
        } catch (IOException e) {
            System.err.println("创建上传目录失败: " + e.getMessage());
            throw new RuntimeException("初始化上传目录失败", e);
        }
    }
}
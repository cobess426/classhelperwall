package com.example.classhelperwall.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.classhelperwall.service.FileService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    
    @Value("${app.upload.local-path:./uploads/}")
    private String uploadLocalPath;
    
    @Value("${app.upload.url-prefix:http://localhost:8080/api/uploads/}")
    private String uploadUrlPrefix;
    
    // 允许的文件类型
    private static final String[] ALLOWED_IMAGE_TYPES = {
        "image/jpeg", "image/jpg", "image/png", "image/gif", "image/bmp", "image/webp"
    };
    
    // 最大文件大小 5MB
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
    
    @Override
    public String uploadFile(MultipartFile file, String fileType) {
        // 验证文件
        if (!isAllowedFileType(file) || !isAllowedFileSize(file)) {
            throw new RuntimeException("文件类型或大小不符合要求");
        }
        
        try {
            // 创建存储目录
            String datePath = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
            String storagePath = uploadLocalPath + fileType + "/" + datePath + "/";
            Path directoryPath = Paths.get(storagePath);
            
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            
            // 生成唯一文件名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
            String filePath = storagePath + uniqueFileName;
            
            // 保存文件
            File destFile = new File(filePath);
            file.transferTo(destFile);
            
            // 返回访问URL
            return uploadUrlPrefix + fileType + "/" + datePath + "/" + uniqueFileName;
            
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean isAllowedFileType(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null) {
            return false;
        }
        
        for (String allowedType : ALLOWED_IMAGE_TYPES) {
            if (allowedType.equalsIgnoreCase(contentType)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isAllowedFileSize(MultipartFile file) {
        return file.getSize() <= MAX_FILE_SIZE;
    }
    
    @Override
    public boolean deleteFile(String fileUrl) {
        try {
            // 从URL中提取文件路径
            String relativePath = extractRelativePathFromUrl(fileUrl);
            if (relativePath == null || relativePath.isEmpty()) {
                return false;
            }
            
            String filePath = uploadLocalPath + relativePath;
            File file = new File(filePath);
            
            if (file.exists() && file.isFile()) {
                return file.delete();
            }
            return false;
            
        } catch (Exception e) {
            throw new RuntimeException("删除文件失败: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String extractFileNameFromUrl(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return "";
        }
        
        // 提取相对路径
        String relativePath = extractRelativePathFromUrl(fileUrl);
        if (relativePath == null || relativePath.isEmpty()) {
            return "";
        }
        
        // 返回文件名
        int lastSlashIndex = relativePath.lastIndexOf("/");
        if (lastSlashIndex != -1) {
            return relativePath.substring(lastSlashIndex + 1);
        }
        return relativePath;
    }
    
    /**
     * 从URL中提取相对路径
     */
    private String extractRelativePathFromUrl(String fileUrl) {
        if (fileUrl == null || fileUrl.isEmpty()) {
            return "";
        }
        
        // 移除URL前缀
        if (fileUrl.startsWith(uploadUrlPrefix)) {
            return fileUrl.substring(uploadUrlPrefix.length());
        }
        
        return "";
    }
}
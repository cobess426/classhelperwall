package com.example.classhelperwall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.classhelperwall.service.FileService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class FileController {
    
    @Autowired
    private FileService fileService;
    
    /**
     * 上传文件
     */
    @PostMapping("/upload")
    public Map<String, Object> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "type", defaultValue = "common") String type) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 验证文件类型
            if (!fileService.isAllowedFileType(file)) {
                response.put("code", 400);
                response.put("message", "不支持的文件类型");
                return response;
            }
            
            // 验证文件大小
            if (!fileService.isAllowedFileSize(file)) {
                response.put("code", 400);
                response.put("message", "文件大小超过限制（最大5MB）");
                return response;
            }
            
            // 上传文件
            String fileUrl = fileService.uploadFile(file, type);
            
            if (fileUrl != null && !fileUrl.isEmpty()) {
                Map<String, Object> data = new HashMap<>();
                data.put("url", fileUrl);
                data.put("name", file.getOriginalFilename());
                data.put("size", file.getSize());
                data.put("type", file.getContentType());
                
                response.put("code", 200);
                response.put("message", "文件上传成功");
                response.put("data", data);
            } else {
                response.put("code", 500);
                response.put("message", "文件上传失败");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "服务器错误: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 删除文件
     */
    @DeleteMapping("/delete")
    public Map<String, Object> deleteFile(@RequestParam("url") String fileUrl) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = fileService.deleteFile(fileUrl);
            
            if (success) {
                response.put("code", 200);
                response.put("message", "文件删除成功");
            } else {
                response.put("code", 500);
                response.put("message", "文件删除失败");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "删除失败: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 验证文件
     */
    @PostMapping("/validate")
    public Map<String, Object> validateFile(@RequestParam("file") MultipartFile file) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean isTypeValid = fileService.isAllowedFileType(file);
            boolean isSizeValid = fileService.isAllowedFileSize(file);
            
            Map<String, Object> data = new HashMap<>();
            data.put("isTypeValid", isTypeValid);
            data.put("isSizeValid", isSizeValid);
            data.put("size", file.getSize());
            data.put("type", file.getContentType());
            data.put("name", file.getOriginalFilename());
            
            if (isTypeValid && isSizeValid) {
                response.put("code", 200);
                response.put("message", "文件验证通过");
            } else {
                response.put("code", 400);
                response.put("message", "文件验证失败");
            }
            
            response.put("data", data);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "验证失败: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 获取文件信息
     */
    @GetMapping("/info")
    public Map<String, Object> getFileInfo(@RequestParam("url") String fileUrl) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String fileName = fileService.extractFileNameFromUrl(fileUrl);
            
            if (fileName != null && !fileName.isEmpty()) {
                Map<String, Object> data = new HashMap<>();
                data.put("fileName", fileName);
                data.put("url", fileUrl);
                
                response.put("code", 200);
                response.put("message", "查询成功");
                response.put("data", data);
            } else {
                response.put("code", 404);
                response.put("message", "文件不存在");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "查询失败: " + e.getMessage());
        }
        
        return response;
    }
}
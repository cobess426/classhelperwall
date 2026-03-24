package com.example.classhelperwall.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    
    /**
     * 上传文件
     * @param file 文件
     * @param fileType 文件类型（questions/answers）
     * @return 文件访问URL
     */
    String uploadFile(MultipartFile file, String fileType);
    
    /**
     * 检查文件类型是否允许
     * @param file 文件
     * @return 是否允许
     */
    boolean isAllowedFileType(MultipartFile file);
    
    /**
     * 检查文件大小是否允许
     * @param file 文件
     * @return 是否允许
     */
    boolean isAllowedFileSize(MultipartFile file);
    
    /**
     * 删除文件
     * @param fileUrl 文件URL
     * @return 是否成功
     */
    boolean deleteFile(String fileUrl);
    
    /**
     * 从URL中提取文件名
     * @param fileUrl 文件URL
     * @return 文件名
     */
    String extractFileNameFromUrl(String fileUrl);
}
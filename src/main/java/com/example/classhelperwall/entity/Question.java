package com.example.classhelperwall.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Question {
    /**
     * 问题ID
     */
    private Integer id;
    
    /**
     * 学号
     */
    private String studentId;
    
    /**
     * 学生姓名
     */
    private String studentName;
    
    /**
     * 问题图片URL
     */
    private String imageUrl;
    
    /**
     * 问题状态：PENDING-待解答, ANSWERED-已解答
     */
    private String status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
    
    /**
     * 默认构造函数
     */
    public Question() {
        this.status = "PENDING";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
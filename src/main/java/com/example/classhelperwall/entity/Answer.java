package com.example.classhelperwall.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Answer {
    /**
     * 回答ID
     */
    private Integer id;
    
    /**
     * 对应问题ID
     */
    private Integer questionId;
    
    /**
     * 回答者学号
     */
    private String studentId;
    
    /**
     * 回答者姓名
     */
    private String studentName;
    
    /**
     * 回答内容
     */
    private String content;
    
    /**
     * 回答图片URL
     */
    private String imageUrl;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 默认构造函数
     */
    public Answer() {
        this.createdAt = LocalDateTime.now();
    }
}
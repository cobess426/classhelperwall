package com.example.classhelperwall.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Score {
    /**
     * 学号
     */
    private String studentId;
    
    /**
     * 学生姓名
     */
    private String studentName;
    
    /**
     * 积分
     */
    private Integer points;
    
    /**
     * 提问次数
     */
    private Integer questionCount;
    
    /**
     * 回答次数
     */
    private Integer answerCount;
    
    /**
     * 最后活跃时间
     */
    private LocalDateTime lastActiveAt;
    
    /**
     * 默认构造函数
     */
    public Score() {
        this.points = 0;
        this.questionCount = 0;
        this.answerCount = 0;
        this.lastActiveAt = LocalDateTime.now();
    }
}
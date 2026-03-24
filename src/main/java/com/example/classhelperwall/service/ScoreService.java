package com.example.classhelperwall.service;

import java.util.List;

import com.example.classhelperwall.entity.Score;

public interface ScoreService {
    
    /**
     * 初始化学生积分记录
     * @param studentId 学号
     * @param studentName 学生姓名
     * @return 是否成功
     */
    boolean initStudentScore(String studentId, String studentName);
    
    /**
     * 根据学号获取积分
     * @param studentId 学号
     * @return 积分记录
     */
    Score getScoreByStudentId(String studentId);
    
    /**
     * 获取积分排行榜
     * @return 积分排行榜
     */
    List<Score> getScoreRanking();
    
    /**
     * 分页获取积分排行榜
     * @param page 页码
     * @param size 每页大小
     * @return 积分排行榜
     */
    List<Score> getScoreRankingByPage(Integer page, Integer size);
    
    /**
     * 增加学生积分
     * @param studentId 学号
     * @param points 增加的积分
     * @return 是否成功
     */
    boolean addPoints(String studentId, Integer points);
    
    /**
     * 增加提问次数
     * @param studentId 学号
     * @return 是否成功
     */
    boolean increaseQuestionCount(String studentId);
    
    /**
     * 增加回答次数
     * @param studentId 学号
     * @return 是否成功
     */
    boolean increaseAnswerCount(String studentId);
    
    /**
     * 获取学生排名
     * @param studentId 学号
     * @return 排名
     */
    Integer getRankByStudentId(String studentId);
    
    /**
     * 获取前十名
     * @return 前十名列表
     */
    List<Score> getTop10Scores();
    
    /**
     * 获取学生总数
     * @return 学生总数
     */
    Integer getStudentCount();
    
    /**
     * 获取最高积分
     * @return 最高积分
     */
    Integer getMaxPoints();
}
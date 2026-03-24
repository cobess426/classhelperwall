package com.example.classhelperwall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.classhelperwall.entity.Score;
import com.example.classhelperwall.mapper.ScoreMapper;
import com.example.classhelperwall.service.ScoreService;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService {
    
    @Autowired
    private ScoreMapper scoreMapper;
    
    @Override
    public boolean initStudentScore(String studentId, String studentName) {
        // 检查是否已存在
        Score existingScore = scoreMapper.selectScoreByStudentId(studentId);
        if (existingScore != null) {
            return true; // 已存在，无需初始化
        }
        
        // 创建新记录
        Score score = new Score();
        score.setStudentId(studentId);
        score.setStudentName(studentName);
        
        int result = scoreMapper.insertScore(score);
        return result > 0;
    }
    
    @Override
    public Score getScoreByStudentId(String studentId) {
        return scoreMapper.selectScoreByStudentId(studentId);
    }
    
    @Override
    public List<Score> getScoreRanking() {
        return scoreMapper.selectAllScores();
    }
    
    @Override
    public List<Score> getScoreRankingByPage(Integer page, Integer size) {
        if (page == null || page < 1) page = 1;
        if (size == null || size < 1) size = 10;
        
        int offset = (page - 1) * size;
        return scoreMapper.selectScoresByPage(offset, size);
    }
    
    @Override
    @Transactional
    public boolean addPoints(String studentId, Integer points) {
        if (points == null || points == 0) {
            return false;
        }
        
        int result = scoreMapper.addPoints(studentId, points);
        return result > 0;
    }
    
    @Override
    @Transactional
    public boolean increaseQuestionCount(String studentId) {
        int result = scoreMapper.increaseQuestionCount(studentId);
        if (result > 0) {
            // 每次提问+2分
            scoreMapper.addPoints(studentId, 2);
            return true;
        }
        return false;
    }
    
    @Override
    @Transactional
    public boolean increaseAnswerCount(String studentId) {
        int result = scoreMapper.increaseAnswerCount(studentId);
        if (result > 0) {
            // 每次回答+5分
            scoreMapper.addPoints(studentId, 5);
            return true;
        }
        return false;
    }
    
    @Override
    public Integer getRankByStudentId(String studentId) {
        return scoreMapper.getRankByStudentId(studentId);
    }
    
    @Override
    public List<Score> getTop10Scores() {
        return scoreMapper.selectTop10Scores();
    }
    
    @Override
    public Integer getStudentCount() {
        return scoreMapper.countStudents();
    }
    
    @Override
    public Integer getMaxPoints() {
        Integer maxPoints = scoreMapper.getMaxPoints();
        return maxPoints != null ? maxPoints : 0;
    }
}
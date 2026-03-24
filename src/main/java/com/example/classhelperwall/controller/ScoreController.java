package com.example.classhelperwall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.classhelperwall.entity.Score;
import com.example.classhelperwall.service.ScoreService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/scores")
public class ScoreController {
    
    @Autowired
    private ScoreService scoreService;
    
    /**
     * 初始化学生积分记录
     */
    @PostMapping("/init")
    public Map<String, Object> initStudentScore(
            @RequestParam("studentId") String studentId,
            @RequestParam("studentName") String studentName) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = scoreService.initStudentScore(studentId, studentName);
            
            if (success) {
                response.put("code", 200);
                response.put("message", "积分记录初始化成功");
            } else {
                response.put("code", 500);
                response.put("message", "积分记录初始化失败");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "服务器错误: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 根据学号获取积分
     */
    @GetMapping("/student/{studentId}")
    public Map<String, Object> getScoreByStudentId(@PathVariable String studentId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Score score = scoreService.getScoreByStudentId(studentId);
            
            if (score != null) {
                // 获取排名
                Integer rank = scoreService.getRankByStudentId(studentId);
                
                Map<String, Object> data = new HashMap<>();
                data.put("score", score);
                data.put("rank", rank);
                
                response.put("code", 200);
                response.put("message", "查询成功");
                response.put("data", data);
            } else {
                response.put("code", 404);
                response.put("message", "积分记录不存在");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "查询失败: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 获取积分排行榜
     */
    @GetMapping("/ranking")
    public Map<String, Object> getScoreRanking() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Score> ranking = scoreService.getScoreRanking();
            response.put("code", 200);
            response.put("message", "查询成功");
            response.put("data", ranking);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "查询失败: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 分页获取积分排行榜
     */
    @GetMapping("/ranking/page")
    public Map<String, Object> getScoreRankingByPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Score> ranking = scoreService.getScoreRankingByPage(page, size);
            Integer total = scoreService.getStudentCount();
            
            Map<String, Object> data = new HashMap<>();
            data.put("list", ranking);
            data.put("current", page);
            data.put("size", size);
            data.put("total", total);
            
            response.put("code", 200);
            response.put("message", "查询成功");
            response.put("data", data);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "查询失败: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 获取前十名
     */
    @GetMapping("/top10")
    public Map<String, Object> getTop10Scores() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Score> top10 = scoreService.getTop10Scores();
            response.put("code", 200);
            response.put("message", "查询成功");
            response.put("data", top10);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "查询失败: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 增加学生积分
     */
    @PostMapping("/add")
    public Map<String, Object> addPoints(
            @RequestParam("studentId") String studentId,
            @RequestParam("points") Integer points) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = scoreService.addPoints(studentId, points);
            
            if (success) {
                response.put("code", 200);
                response.put("message", "积分增加成功");
            } else {
                response.put("code", 500);
                response.put("message", "积分增加失败");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "操作失败: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 获取积分统计
     */
    @GetMapping("/statistics")
    public Map<String, Object> getScoreStatistics() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Integer studentCount = scoreService.getStudentCount();
            Integer maxPoints = scoreService.getMaxPoints();
            
            Map<String, Object> stats = new HashMap<>();
            stats.put("studentCount", studentCount);
            stats.put("maxPoints", maxPoints);
            
            response.put("code", 200);
            response.put("message", "查询成功");
            response.put("data", stats);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "查询失败: " + e.getMessage());
        }
        
        return response;
    }
}
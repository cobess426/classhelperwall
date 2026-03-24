package com.example.classhelperwall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.classhelperwall.entity.Question;
import com.example.classhelperwall.service.QuestionService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    
    @Autowired
    private QuestionService questionService;
    
    /**
     * 发布问题
     */
    @PostMapping
    public Map<String, Object> createQuestion(
            @RequestParam("studentId") String studentId,
            @RequestParam("studentName") String studentName,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            Question question = questionService.publishQuestion(studentId, studentName, image);
            
            if (question != null) {
                response.put("code", 200);
                response.put("message", "问题发布成功");
                response.put("data", question);
            } else {
                response.put("code", 500);
                response.put("message", "问题发布失败");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "服务器错误: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 获取所有问题
     */
    @GetMapping
    public Map<String, Object> getAllQuestions() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Question> questions = questionService.getAllQuestions();
            response.put("code", 200);
            response.put("message", "查询成功");
            response.put("data", questions);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "查询失败: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 分页查询问题
     */
    @GetMapping("/page")
    public Map<String, Object> getQuestionsByPage(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Question> questions = questionService.getQuestionsByPage(page, size);
            Integer total = questionService.getQuestionCount();
            
            Map<String, Object> pageData = new HashMap<>();
            pageData.put("list", questions);
            pageData.put("current", page);
            pageData.put("size", size);
            pageData.put("total", total);
            
            response.put("code", 200);
            response.put("message", "查询成功");
            response.put("data", pageData);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "查询失败: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 获取问题详情
     */
    @GetMapping("/{id}")
    public Map<String, Object> getQuestionById(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Question question = questionService.getQuestionById(id);
            
            if (question != null) {
                response.put("code", 200);
                response.put("message", "查询成功");
                response.put("data", question);
            } else {
                response.put("code", 404);
                response.put("message", "问题不存在");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "查询失败: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 获取待解答的问题
     */
    @GetMapping("/pending")
    public Map<String, Object> getPendingQuestions() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Question> questions = questionService.getPendingQuestions();
            response.put("code", 200);
            response.put("message", "查询成功");
            response.put("data", questions);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "查询失败: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 获取已解答的问题
     */
    @GetMapping("/answered")
    public Map<String, Object> getAnsweredQuestions() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Question> questions = questionService.getAnsweredQuestions();
            response.put("code", 200);
            response.put("message", "查询成功");
            response.put("data", questions);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "查询失败: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 根据学号查询问题
     */
    @GetMapping("/student/{studentId}")
    public Map<String, Object> getQuestionsByStudentId(@PathVariable String studentId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Question> questions = questionService.getQuestionsByStudentId(studentId);
            response.put("code", 200);
            response.put("message", "查询成功");
            response.put("data", questions);
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "查询失败: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 更新问题状态
     */
    @PutMapping("/{id}/status")
    public Map<String, Object> updateQuestionStatus(
            @PathVariable Integer id,
            @RequestParam String status) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = questionService.updateQuestionStatus(id, status);
            
            if (success) {
                response.put("code", 200);
                response.put("message", "状态更新成功");
            } else {
                response.put("code", 500);
                response.put("message", "状态更新失败");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "更新失败: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 删除问题
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteQuestion(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = questionService.deleteQuestion(id);
            
            if (success) {
                response.put("code", 200);
                response.put("message", "删除成功");
            } else {
                response.put("code", 500);
                response.put("message", "删除失败");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "删除失败: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 获取问题统计
     */
    @GetMapping("/statistics")
    public Map<String, Object> getQuestionStatistics() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Integer total = questionService.getQuestionCount();
            Integer pending = questionService.getPendingQuestionCount();
            
            Map<String, Object> stats = new HashMap<>();
            stats.put("total", total);
            stats.put("pending", pending);
            stats.put("answered", total - pending);
            
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
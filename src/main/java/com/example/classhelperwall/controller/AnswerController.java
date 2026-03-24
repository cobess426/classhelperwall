package com.example.classhelperwall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.classhelperwall.entity.Answer;
import com.example.classhelperwall.service.AnswerService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {
    
    @Autowired
    private AnswerService answerService;
    
    /**
     * 发布回答
     */
    @PostMapping
    public Map<String, Object> createAnswer(
            @RequestParam("questionId") Integer questionId,
            @RequestParam("studentId") String studentId,
            @RequestParam("studentName") String studentName,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            Answer answer = answerService.publishAnswer(questionId, studentId, studentName, content, image);
            
            if (answer != null) {
                response.put("code", 200);
                response.put("message", "回答发布成功");
                response.put("data", answer);
            } else {
                response.put("code", 500);
                response.put("message", "回答发布失败");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "服务器错误: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 根据ID获取回答详情
     */
    @GetMapping("/{id}")
    public Map<String, Object> getAnswerById(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Answer answer = answerService.getAnswerById(id);
            
            if (answer != null) {
                response.put("code", 200);
                response.put("message", "查询成功");
                response.put("data", answer);
            } else {
                response.put("code", 404);
                response.put("message", "回答不存在");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "查询失败: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 根据问题ID获取回答列表
     */
    @GetMapping("/question/{questionId}")
    public Map<String, Object> getAnswersByQuestionId(@PathVariable Integer questionId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Answer> answers = answerService.getAnswersByQuestionId(questionId);
            Integer count = answerService.getAnswerCountByQuestionId(questionId);
            
            Map<String, Object> data = new HashMap<>();
            data.put("list", answers);
            data.put("count", count);
            
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
     * 根据学号获取回答列表
     */
    @GetMapping("/student/{studentId}")
    public Map<String, Object> getAnswersByStudentId(@PathVariable String studentId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            List<Answer> answers = answerService.getAnswersByStudentId(studentId);
            Integer count = answerService.getAnswerCountByStudentId(studentId);
            
            Map<String, Object> data = new HashMap<>();
            data.put("list", answers);
            data.put("count", count);
            
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
     * 更新回答
     */
    @PutMapping("/{id}")
    public Map<String, Object> updateAnswer(
            @PathVariable Integer id,
            @RequestParam(value = "content", required = false) String content,
            @RequestParam(value = "image", required = false) MultipartFile image) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = answerService.updateAnswer(id, content, image);
            
            if (success) {
                response.put("code", 200);
                response.put("message", "回答更新成功");
            } else {
                response.put("code", 500);
                response.put("message", "回答更新失败");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "更新失败: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * 删除回答
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteAnswer(@PathVariable Integer id) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            boolean success = answerService.deleteAnswer(id);
            
            if (success) {
                response.put("code", 200);
                response.put("message", "回答删除成功");
            } else {
                response.put("code", 500);
                response.put("message", "回答删除失败");
            }
        } catch (Exception e) {
            response.put("code", 500);
            response.put("message", "删除失败: " + e.getMessage());
        }
        
        return response;
    }
}
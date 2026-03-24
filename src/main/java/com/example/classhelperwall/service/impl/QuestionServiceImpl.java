package com.example.classhelperwall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.classhelperwall.entity.Question;
import com.example.classhelperwall.mapper.QuestionMapper;
import com.example.classhelperwall.service.FileService;
import com.example.classhelperwall.service.QuestionService;
import com.example.classhelperwall.service.ScoreService;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    
    @Autowired
    private QuestionMapper questionMapper;
    
    @Autowired
    private FileService fileService;
    
    @Autowired
    private ScoreService scoreService;
    
    @Override
    @Transactional
    public Question publishQuestion(String studentId, String studentName, MultipartFile imageFile) {
        // 创建问题对象
        Question question = new Question();
        question.setStudentId(studentId);
        question.setStudentName(studentName);
        
        // 处理图片上传
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = fileService.uploadFile(imageFile, "questions");
            question.setImageUrl(imageUrl);
        }
        
        // 保存问题
        int result = questionMapper.insertQuestion(question);
        if (result > 0) {
            // 更新积分
            scoreService.increaseQuestionCount(studentId);
            return question;
        }
        
        return null;
    }
    
    @Override
    public Question getQuestionById(Integer id) {
        return questionMapper.selectQuestionById(id);
    }
    
    @Override
    public List<Question> getAllQuestions() {
        return questionMapper.selectAllQuestions();
    }
    
    @Override
    public List<Question> getQuestionsByPage(Integer page, Integer size) {
        if (page == null || page < 1) page = 1;
        if (size == null || size < 1) size = 10;
        
        int offset = (page - 1) * size;
        return questionMapper.selectQuestionsByPage(offset, size);
    }
    
    @Override
    public List<Question> getPendingQuestions() {
        return questionMapper.selectQuestionsByStatus("PENDING");
    }
    
    @Override
    public List<Question> getAnsweredQuestions() {
        return questionMapper.selectQuestionsByStatus("ANSWERED");
    }
    
    @Override
    public List<Question> getQuestionsByStudentId(String studentId) {
        return questionMapper.selectQuestionsByStudentId(studentId);
    }
    
    @Override
    @Transactional
    public boolean updateQuestionStatus(Integer id, String status) {
        int result = questionMapper.updateQuestionStatus(id, status);
        return result > 0;
    }
    
    @Override
    @Transactional
    public boolean deleteQuestion(Integer id) {
        // 获取问题信息
        Question question = questionMapper.selectQuestionById(id);
        if (question == null) {
            return false;
        }
        
        // 删除图片文件
        if (question.getImageUrl() != null && !question.getImageUrl().isEmpty()) {
            fileService.deleteFile(question.getImageUrl());
        }
        
        // 删除问题
        int result = questionMapper.deleteQuestion(id);
        return result > 0;
    }
    
    @Override
    public Integer getQuestionCount() {
        return questionMapper.countQuestions();
    }
    
    @Override
    public Integer getPendingQuestionCount() {
        return questionMapper.countPendingQuestions();
    }
}
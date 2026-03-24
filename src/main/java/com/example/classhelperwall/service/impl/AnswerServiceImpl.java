package com.example.classhelperwall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.classhelperwall.entity.Answer;
import com.example.classhelperwall.mapper.AnswerMapper;
import com.example.classhelperwall.mapper.QuestionMapper;
import com.example.classhelperwall.service.AnswerService;
import com.example.classhelperwall.service.FileService;
import com.example.classhelperwall.service.ScoreService;

import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {
    
    @Autowired
    private AnswerMapper answerMapper;
    
    @Autowired
    private QuestionMapper questionMapper;
    
    @Autowired
    private FileService fileService;
    
    @Autowired
    private ScoreService scoreService;
    
    @Override
    @Transactional
    public Answer publishAnswer(Integer questionId, String studentId, String studentName, 
                                String content, MultipartFile imageFile) {
        // 检查问题是否存在
        com.example.classhelperwall.entity.Question question = questionMapper.selectQuestionById(questionId);
        if (question == null) {
            throw new RuntimeException("问题不存在");
        }
        
        // 创建回答对象
        Answer answer = new Answer();
        answer.setQuestionId(questionId);
        answer.setStudentId(studentId);
        answer.setStudentName(studentName);
        answer.setContent(content);
        
        // 处理图片上传
        if (imageFile != null && !imageFile.isEmpty()) {
            String imageUrl = fileService.uploadFile(imageFile, "answers");
            answer.setImageUrl(imageUrl);
        }
        
        // 保存回答
        int result = answerMapper.insertAnswer(answer);
        if (result > 0) {
            // 更新积分
            scoreService.increaseAnswerCount(studentId);
            // 更新问题状态为已解答
            questionMapper.updateQuestionStatus(questionId, "ANSWERED");
            return answer;
        }
        
        return null;
    }
    
    @Override
    public Answer getAnswerById(Integer id) {
        return answerMapper.selectAnswerById(id);
    }
    
    @Override
    public List<Answer> getAnswersByQuestionId(Integer questionId) {
        return answerMapper.selectAnswersByQuestionId(questionId);
    }
    
    @Override
    public List<Answer> getAnswersByStudentId(String studentId) {
        return answerMapper.selectAnswersByStudentId(studentId);
    }
    
    @Override
    @Transactional
    public boolean updateAnswer(Integer id, String content, MultipartFile imageFile) {
        // 获取原回答
        Answer answer = answerMapper.selectAnswerById(id);
        if (answer == null) {
            return false;
        }
        
        // 更新内容
        answer.setContent(content);
        
        // 处理图片更新
        if (imageFile != null && !imageFile.isEmpty()) {
            // 删除原图片
            if (answer.getImageUrl() != null && !answer.getImageUrl().isEmpty()) {
                fileService.deleteFile(answer.getImageUrl());
            }
            
            // 上传新图片
            String newImageUrl = fileService.uploadFile(imageFile, "answers");
            answer.setImageUrl(newImageUrl);
        }
        
        int result = answerMapper.updateAnswer(answer);
        return result > 0;
    }
    
    @Override
    @Transactional
    public boolean deleteAnswer(Integer id) {
        // 获取回答信息
        Answer answer = answerMapper.selectAnswerById(id);
        if (answer == null) {
            return false;
        }
        
        // 删除图片文件
        if (answer.getImageUrl() != null && !answer.getImageUrl().isEmpty()) {
            fileService.deleteFile(answer.getImageUrl());
        }
        
        // 删除回答
        int result = answerMapper.deleteAnswer(id);
        
        // 如果该问题没有其他回答，则将问题状态改为待解答
        if (result > 0) {
            int answerCount = answerMapper.countAnswersByQuestionId(answer.getQuestionId());
            if (answerCount == 0) {
                questionMapper.updateQuestionStatus(answer.getQuestionId(), "PENDING");
            }
        }
        
        return result > 0;
    }
    
    @Override
    public Integer getAnswerCountByQuestionId(Integer questionId) {
        return answerMapper.countAnswersByQuestionId(questionId);
    }
    
    @Override
    public Integer getAnswerCountByStudentId(String studentId) {
        return answerMapper.countAnswersByStudentId(studentId);
    }
}
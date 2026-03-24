package com.example.classhelperwall.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.classhelperwall.entity.Answer;

import java.util.List;

public interface AnswerService {
    
    /**
     * 发布回答
     * @param questionId 问题ID
     * @param studentId 回答者学号
     * @param studentName 回答者姓名
     * @param content 回答内容
     * @param imageFile 回答图片文件
     * @return 回答
     */
    Answer publishAnswer(Integer questionId, String studentId, String studentName, 
                         String content, MultipartFile imageFile);
    
    /**
     * 根据ID获取回答详情
     * @param id 回答ID
     * @return 回答详情
     */
    Answer getAnswerById(Integer id);
    
    /**
     * 根据问题ID获取回答列表
     * @param questionId 问题ID
     * @return 回答列表
     */
    List<Answer> getAnswersByQuestionId(Integer questionId);
    
    /**
     * 根据学号获取回答列表
     * @param studentId 学号
     * @return 回答列表
     */
    List<Answer> getAnswersByStudentId(String studentId);
    
    /**
     * 更新回答
     * @param id 回答ID
     * @param content 回答内容
     * @param imageFile 回答图片文件
     * @return 是否成功
     */
    boolean updateAnswer(Integer id, String content, MultipartFile imageFile);
    
    /**
     * 删除回答
     * @param id 回答ID
     * @return 是否成功
     */
    boolean deleteAnswer(Integer id);
    
    /**
     * 获取问题的回答数
     * @param questionId 问题ID
     * @return 回答数
     */
    Integer getAnswerCountByQuestionId(Integer questionId);
    
    /**
     * 获取学生的回答数
     * @param studentId 学号
     * @return 回答数
     */
    Integer getAnswerCountByStudentId(String studentId);
}
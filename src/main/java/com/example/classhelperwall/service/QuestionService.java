package com.example.classhelperwall.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.classhelperwall.entity.Question;

import java.util.List;

public interface QuestionService {
    
    /**
     * 发布问题
     * @param studentId 学号
     * @param studentName 学生姓名
     * @param imageFile 问题图片文件
     * @return 发布的问题
     */
    Question publishQuestion(String studentId, String studentName, MultipartFile imageFile);
    
    /**
     * 根据ID获取问题详情
     * @param id 问题ID
     * @return 问题详情
     */
    Question getQuestionById(Integer id);
    
    /**
     * 获取所有问题列表
     * @return 问题列表
     */
    List<Question> getAllQuestions();
    
    /**
     * 分页查询问题
     * @param page 页码
     * @param size 每页大小
     * @return 问题列表
     */
    List<Question> getQuestionsByPage(Integer page, Integer size);
    
    /**
     * 获取待解答的问题列表
     * @return 待解答问题列表
     */
    List<Question> getPendingQuestions();
    
    /**
     * 获取已解答的问题列表
     * @return 已解答问题列表
     */
    List<Question> getAnsweredQuestions();
    
    /**
     * 根据学号查询问题
     * @param studentId 学号
     * @return 问题列表
     */
    List<Question> getQuestionsByStudentId(String studentId);
    
    /**
     * 更新问题状态
     * @param id 问题ID
     * @param status 状态
     * @return 是否成功
     */
    boolean updateQuestionStatus(Integer id, String status);
    
    /**
     * 删除问题
     * @param id 问题ID
     * @return 是否成功
     */
    boolean deleteQuestion(Integer id);
    
    /**
     * 获取问题总数
     * @return 问题总数
     */
    Integer getQuestionCount();
    
    /**
     * 获取待解答问题数
     * @return 待解答问题数
     */
    Integer getPendingQuestionCount();
}
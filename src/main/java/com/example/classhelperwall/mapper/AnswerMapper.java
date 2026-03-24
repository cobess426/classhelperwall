package com.example.classhelperwall.mapper;

import com.example.classhelperwall.entity.Answer;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AnswerMapper {
    
    /**
     * 插入回答
     */
    @Insert("INSERT INTO answer (question_id, student_id, student_name, content, image_url, created_at) " +
            "VALUES (#{questionId}, #{studentId}, #{studentName}, #{content}, #{imageUrl}, #{createdAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertAnswer(Answer answer);
    
    /**
     * 根据ID查询回答
     */
    @Select("SELECT * FROM answer WHERE id = #{id}")
    Answer selectAnswerById(Integer id);
    
    /**
     * 根据问题ID查询回答
     */
    @Select("SELECT * FROM answer WHERE question_id = #{questionId} ORDER BY created_at DESC")
    List<Answer> selectAnswersByQuestionId(Integer questionId);
    
    /**
     * 根据学号查询回答
     */
    @Select("SELECT * FROM answer WHERE student_id = #{studentId} ORDER BY created_at DESC")
    List<Answer> selectAnswersByStudentId(String studentId);
    
    /**
     * 更新回答内容
     */
    @Update("UPDATE answer SET content = #{content}, image_url = #{imageUrl} WHERE id = #{id}")
    int updateAnswer(Answer answer);
    
    /**
     * 删除回答
     */
    @Delete("DELETE FROM answer WHERE id = #{id}")
    int deleteAnswer(Integer id);
    
    /**
     * 根据问题ID删除所有回答
     */
    @Delete("DELETE FROM answer WHERE question_id = #{questionId}")
    int deleteAnswersByQuestionId(Integer questionId);
    
    /**
     * 统计某个问题的回答数量
     */
    @Select("SELECT COUNT(*) FROM answer WHERE question_id = #{questionId}")
    int countAnswersByQuestionId(Integer questionId);
    
    /**
     * 统计某个学生的回答数量
     */
    @Select("SELECT COUNT(*) FROM answer WHERE student_id = #{studentId}")
    int countAnswersByStudentId(String studentId);
}
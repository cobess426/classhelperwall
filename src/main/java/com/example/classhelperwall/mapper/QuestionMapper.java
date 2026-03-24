package com.example.classhelperwall.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import com.example.classhelperwall.entity.Question;

import java.util.List;

@Repository
@Mapper
public interface QuestionMapper {
    
    /**
     * 插入问题
     */
    @Insert("INSERT INTO question (student_id, student_name, image_url, status, created_at, updated_at) " +
            "VALUES (#{studentId}, #{studentName}, #{imageUrl}, #{status}, #{createdAt}, #{updatedAt})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertQuestion(Question question);
    
    /**
     * 根据ID查询问题
     */
    @Select("SELECT * FROM question WHERE id = #{id}")
    Question selectQuestionById(Integer id);
    
    /**
     * 查询所有问题（按创建时间倒序）
     */
    @Select("SELECT * FROM question ORDER BY created_at DESC")
    List<Question> selectAllQuestions();
    
    /**
     * 分页查询问题
     */
    @Select("SELECT * FROM question ORDER BY created_at DESC LIMIT #{offset}, #{pageSize}")
    List<Question> selectQuestionsByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
    
    /**
     * 根据状态查询问题
     */
    @Select("SELECT * FROM question WHERE status = #{status} ORDER BY created_at DESC")
    List<Question> selectQuestionsByStatus(String status);
    
    /**
     * 根据学号查询问题
     */
    @Select("SELECT * FROM question WHERE student_id = #{studentId} ORDER BY created_at DESC")
    List<Question> selectQuestionsByStudentId(String studentId);
    
    /**
     * 更新问题状态
     */
    @Update("UPDATE question SET status = #{status}, updated_at = NOW() WHERE id = #{id}")
    int updateQuestionStatus(@Param("id") Integer id, @Param("status") String status);
    
    /**
     * 更新问题图片URL
     */
    @Update("UPDATE question SET image_url = #{imageUrl}, updated_at = NOW() WHERE id = #{id}")
    int updateQuestionImageUrl(@Param("id") Integer id, @Param("imageUrl") String imageUrl);
    
    /**
     * 删除问题
     */
    @Delete("DELETE FROM question WHERE id = #{id}")
    int deleteQuestion(Integer id);
    
    /**
     * 统计问题总数
     */
    @Select("SELECT COUNT(*) FROM question")
    int countQuestions();
    
    /**
     * 统计待解答问题数
     */
    @Select("SELECT COUNT(*) FROM question WHERE status = 'PENDING'")
    int countPendingQuestions();
}
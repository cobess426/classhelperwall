package com.example.classhelperwall.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import com.example.classhelperwall.entity.Score;

import java.util.List;

@Repository
@Mapper
public interface ScoreMapper {
    
    /**
     * 插入或初始化学生积分记录
     */
    @Insert("INSERT INTO score (student_id, student_name, points, question_count, answer_count, last_active_at) " +
            "VALUES (#{studentId}, #{studentName}, #{points}, #{questionCount}, #{answerCount}, #{lastActiveAt})")
    int insertScore(Score score);
    
    /**
     * 根据学号查询积分
     */
    @Select("SELECT * FROM score WHERE student_id = #{studentId}")
    Score selectScoreByStudentId(String studentId);
    
    /**
     * 查询所有学生积分（按积分倒序）
     */
    @Select("SELECT * FROM score ORDER BY points DESC")
    List<Score> selectAllScores();
    
    /**
     * 分页查询学生积分
     */
    @Select("SELECT * FROM score ORDER BY points DESC, last_active_at DESC LIMIT #{offset}, #{pageSize}")
    List<Score> selectScoresByPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
    
    /**
     * 更新学生积分
     */
    @Update("UPDATE score SET points = #{points}, question_count = #{questionCount}, " +
            "answer_count = #{answerCount}, last_active_at = #{lastActiveAt} " +
            "WHERE student_id = #{studentId}")
    int updateScore(Score score);
    
    /**
     * 增加学生积分
     */
    @Update("UPDATE score SET points = points + #{points}, last_active_at = NOW() WHERE student_id = #{studentId}")
    int addPoints(@Param("studentId") String studentId, @Param("points") Integer points);
    
    /**
     * 增加提问次数
     */
    @Update("UPDATE score SET question_count = question_count + 1, last_active_at = NOW() WHERE student_id = #{studentId}")
    int increaseQuestionCount(String studentId);
    
    /**
     * 增加回答次数
     */
    @Update("UPDATE score SET answer_count = answer_count + 1, last_active_at = NOW() WHERE student_id = #{studentId}")
    int increaseAnswerCount(String studentId);
    
    /**
     * 删除学生积分记录
     */
    @Delete("DELETE FROM score WHERE student_id = #{studentId}")
    int deleteScore(String studentId);
    
    /**
     * 统计学生总数
     */
    @Select("SELECT COUNT(*) FROM score")
    int countStudents();
    
    /**
     * 获取最高积分
     */
    @Select("SELECT MAX(points) FROM score")
    Integer getMaxPoints();
    
    /**
     * 获取排名
     */
    @Select("SELECT COUNT(*) + 1 FROM score WHERE points > (SELECT points FROM score WHERE student_id = #{studentId})")
    Integer getRankByStudentId(String studentId);
    
    /**
     * 获取前十名
     */
    @Select("SELECT * FROM score ORDER BY points DESC, last_active_at DESC LIMIT 10")
    List<Score> selectTop10Scores();
}
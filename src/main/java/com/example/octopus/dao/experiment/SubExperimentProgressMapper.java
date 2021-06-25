package com.example.octopus.dao.experiment;

import com.example.octopus.entity.experiment.SubExperimentProgress;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/24 2:51 下午
 */
@Mapper
public interface SubExperimentProgressMapper {

    /**
     *  根据ID查询
     */
    @Select("SELECT * FROM sub_experiment_progress WHERE id = #{id}")
    SubExperimentProgress getById(long id);

    /**
     *  根据子实验id查询
     */
    @Select("SELECT * FROM sub_experiment_progress WHERE sub_experiment_id = #{subExperimentId}")
    List<SubExperimentProgress> listBySubExperimentId(long subExperimentId);

    /**
     *  根据学生id查询，返回这个学生所有的学习记录
     */
    @Select("SELECT * FROM sub_experiment_progress WHERE stu_number = #{stuNumber}")
    List<SubExperimentProgress> listByStuNumber(long stuNumber);

    /**
     *  根据学生id和子实验id查询，返回这个学生有关这个子实验所有的学习记录
     */
    @Select("SELECT * FROM sub_experiment_progress WHERE stu_number = #{stuNumber} AND sub_experiment_id = #{subExperimentId}")
    List<SubExperimentProgress> listByStuNumberAndSubExperimentId(long stuNumber, long subExperimentId);

    /**
     *  计算该学生总计有效学习时间
     */
    @Select("SELECT SUM(valid_study_time) FROM sub_experiment_progress WHERE stu_number = #{stuNumber}")
    int countValidStudyTimeByStuNumber(long stuNumber);

    /**
     *  计算该学生在某子实验上总计有效学习时间
     */
    @Select("SELECT SUM(valid_study_time) FROM sub_experiment_progress WHERE stu_number = #{stuNumber} AND sub_experiment_id=#{subExperimentId}")
    int countValidStudyTimeByStuNumberAndSubExpId(long stuNumber, long subExperimentId);

    /**
     *  新增子实验学习时间记录
     */
    @Insert("INSERT INTO sub_experiment_progress (sub_experiment_id, stu_number, start_time, end_time, valid_study_time) VALUES (#{subExperimentId},#{stuNumber},#{startTime},#{endTime},#{validStudyTime})")
    boolean insert(SubExperimentProgress subExperimentProgress);

    /**
     *  更新子实验学习时间记录
     */
    @Update("UPDATE sub_experiment_progress SET sub_experiment_id=#{subExperimentId}, stu_number=#{stuNumber}, start_time=#{startTime}, end_time=#{endTime}, valid_study_time=#{validStudyTime} WHERE id =#{id}")
    boolean update(SubExperimentProgress subExperimentProgress);

    /**
     *  删除子实验学习时间记录
     */
    @Delete("DELETE FROM sub_experiment_progress WHERE id = #{id}")
    boolean delete(long id);

}
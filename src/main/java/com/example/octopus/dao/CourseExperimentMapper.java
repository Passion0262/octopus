package com.example.octopus.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/10 2:48 下午
 */
@Mapper
public interface CourseExperimentMapper {
    /**
     * 查询课程对应的实验任务id
     * @param courseId 课程id
     * @return experiment实体
     */
    @Select("SELECT experiment_id FROM course_experiment WHERE course_id = #{courseId}")
    Long getExperimentIdByCourseId(long courseId);
}

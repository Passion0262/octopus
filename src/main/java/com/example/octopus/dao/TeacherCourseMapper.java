package com.example.octopus.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/8 3:00 下午
 */
@Mapper
public interface TeacherCourseMapper {

    /**
     * 根据教师编号返回所有该教师教授的courseIdList
     * @param teaNumber 老师编号
     * @return courseid list
     */
    @Select("SELECT course_id FROM teacher_course WHERE tea_number = #{teaNumber}")
    List<Long> listCourseIdsByTeaNumber(long teaNumber);

    /**
     * 根据课程编号返回教授该课程的教师id list
     * @param courseId 课程id
     * @return 教师id list
     */
    @Select("SELECT tea_number FROM teacher_course WHERE course_id = #{courseId}")
    List<Long> listTeaNumbersByCourseId(long courseId);

}

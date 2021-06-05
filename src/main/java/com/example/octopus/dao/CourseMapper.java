package com.example.octopus.dao;

import com.example.octopus.entity.user.Course;
import com.example.octopus.entity.user.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/28 5:04 下午
 * @modified By：
 */
@Mapper
public interface CourseMapper {

    /**
     * 查询所有课程并返回
     * @return 课程list
     */
    @Select("SELECT * FROM course")
    List<Course> listCourses();

    /**
     * 根据课程id返回课程
     * @param id 课程id
     * @return
     */
    @Select(("SELECT * FROM course where id = #{id}"))
    Course getCourseById(long id);


    /**
     * 增加课程实体
     * @param course  课程实体
     * @return 成功为true，失败为false
     */
    @Insert("INSERT INTO course (course_name,teacher,classification,description,image_path,experiment_mission_id,start_time,end_time,apply_time,num_allowed,num_participated,status) VALUES (#{courseName},#{teacher},#{classification},#{description},#{imagePath},#{experimentMissionId},#{startTime},#{endTime},#{applyTime},#{numAllowed},#{numParticipated},#{status})")
    boolean insertCourse(Course course);

    /**
     * 修改课程
     * @param course  课程实体
     * @return 成功为true，失败为false
     */
    @Update("Update course SET course_name = #{courseName},teacher=#{teacher},classification=#{classification},description=#{description},image_path=#{imagePath},experiment_mission_id=#{experimentMissionId},start_time=#{startTime},end_time=#{endTime},apply_time=#{applyTime},num_allowed=#{numAllowed},num_participated=#{numParticipated},status=#{status} WHERE id = #{id}")
    boolean updateCourse(Course course);

//    boolean chooseCourse()

    /**
     * 根据courseid删除课程
     * @param courseId 课程id
     * @return 成功为true，失败为false
     */
    @Delete("DELETE FROM course WHERE id = #{courseId}")
    boolean deleteCourseById(long courseId);

}

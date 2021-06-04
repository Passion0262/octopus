package com.example.octopus.dao;

import com.example.octopus.entity.user.Course;
import com.example.octopus.entity.user.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
     * 如果courseid存在则更新，如果不存在则添加。
     * @param course  课程实体
     * @return 成功为true，失败为false
     */
    @Update("Update course SET course_name = #{course_name},teacher=#{teacher},classification=#{classification},description=#{description},image_path=#{image_path},experiment_mission_id=#{experiment_mission_id},start_time=#{start_time},end_time=#{end_time},apply_time=#{apply_time},num_allowed=#{num_allowed},num_participated=#{num_participated},status=#{status} WHERE id = #{id}")
    boolean updateCourse(Course course);

//    boolean chooseCourse()

    /**
     * 根据courseid删除课程
     * @param courseid 课程id
     * @return 成功为true，失败为false
     */
    boolean deleteCourseById(long courseid);

}

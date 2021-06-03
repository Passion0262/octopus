package com.example.octopus.dao;

import com.example.octopus.entity.user.Course;
import com.example.octopus.entity.user.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
    List<Course> listAllCourses();

    /**
     * 根据课程id返回课程
     * @param id 课程id
     * @return
     */
    @Select(("SELECT * FROM course where id = #{id}"))
    Course getCourse(long id);


    /**
     * 如果courseid存在则更新，如果不存在则添加。
     * @param course  课程实体
     * @return 成功为true，失败为false
     */
    boolean saveCourse(Course course);

//    boolean chooseCourse()

    /**
     * 根据courseid删除课程
     * @param courseid 课程id
     * @return 成功为true，失败为false
     */
    boolean deleteCourseById(long courseid);

}

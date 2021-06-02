package com.example.octopus.service;

import com.example.octopus.entity.user.Course;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/26 6:26 下午
 * @modified By：
 */
public interface CourseService {

    /**
     * 查询所有的课程
     * @return  返回所有课程实体
     */
    List<Course> findAllCourses();

    /**
     * 根据课程id查询课程
     * @param id 课程id
     * @return 课程实体
     */
    Course findCourseById(long id);

    /**
     * 如果courseid存在则更新，如果不存在则添加。
     * @param course  课程实体
     * @return 成功为true，失败为false
     */
    boolean saveCourse(Course course);

    /**
     * 根据courseid删除课程
     * @param courseid 课程id
     * @return 成功为true，失败为false
     */
    boolean deleteCourseById(long courseid);

}

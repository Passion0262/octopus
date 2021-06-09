package com.example.octopus.service;

import com.example.octopus.entity.user.Course;
import com.example.octopus.entity.user.Student;
import com.example.octopus.entity.user.StudentCourse;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/26 6:26 下午
 * @modified By：
 */
public interface CourseService {

    /**
     * 根据课程id查询课程
     * @param id 课程id
     * @return 课程实体
     */
    Course getCourseById(long id);

    /**
     * 查询所有的课程
     * @return  返回所有课程实体
     */
    List<Course> listCourses();

    /**
     * 根据学生学号查询该学生选的课
     * @param stuNumber 学号
     * @return 课程list
     */
    List<Course> listCoursesByStuNumber(long stuNumber);

    /**
     * 根据教师编号查询该教师教授的课程list
     * @param teaNumber 教师编号
     * @return courseList
     */
    List<Course> listCoursesByTeaNumber(long teaNumber);

    /**
     * 查询学生是否选某一门课
     * @param stuNumber 学号
     * @param courseId 课程id
     * @return 选了这门课为true，没选是false
     */
    boolean isChosen(long stuNumber, long courseId);

    /**
     * 新增课程
     * @param course  课程实体
     * @return 成功为true，失败为false
     */
    boolean insertCourse(Course course);

    /**
     * 新增学生选课记录 根据学生id，课程id
     * @param stuNumber 学号
     * @param courseId 课程id
     * @return 是否成功选课
     */
    boolean insertChooseCourse(long stuNumber, long courseId);

    /**
     * 删除学生选课记录
     * @param stuNumber 学号
     * @param courseId 课程id
     * @return 是否成功删除
     */
    boolean deleteChooseCourse(long stuNumber, long courseId);

    /**
     * 更新课程
     * @param course 课程实体
     * @return 是否成功更新
     */
    boolean updateCourse(Course course);

    /**
     * 根据courseid删除课程
     * @param courseid 课程id
     * @return 成功为true，失败为false
     */
    boolean deleteCourseById(long courseid);

}

package com.example.octopus.dao;

import com.example.octopus.entity.user.Course;
import com.example.octopus.entity.user.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
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
    List<Course> queryAllCourses();

    /**
     * 根据课程id返回课程
     * @param id 课程id
     * @return
     */
    @Select(("SELECT * FROM course where id = #{id}"))
    Course queryCourse(long id);

    /**
     * 根据学生学号查询所选所有课程
     * @param stuNumber 学生学号
     * @return 该学生选的所有课程
     */
    @Select("SELECT * FROM student_course where stuNumber = #{stuNumber}")
    List<Course> queryCourseByStuNumber(String stuNumber);

    /**
     * 查询学生是否选某一门课
     * @param stuNumber 学号
     * @param courseName 课程名
     * @return 选了这门课为true，没选是false
     */
    @Select("SELECT COUNT(id) FROM student_course WHERE stuNumber = #{stuNumber} AND courseName = #{courseName}")
    boolean queryCourseIsChosen(String stuNumber, String courseName);

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

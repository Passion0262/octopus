package com.example.octopus.dao;

import com.example.octopus.entity.user.Course;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/3 2:27 下午
 */
@Mapper
public interface StudentCourseMapper {

    /**
     * 学生选课
     * @param courseId 课程id
     * @param stuNumber 学生学号
     * @return 成功为true，失败为false
     */
    @Insert("INSERT INTO student_course (id,course_id,stu_number,apply_time) VALUES(null,#{courseId},#{stuNumber},CURRENT_TIMESTAMP)")
    boolean insertStudentCourse(long courseId, long stuNumber);

    /**
     * 根据学生学号返回所有该学生选的课的courseId
     * @param stuNumber 学生学号
     * @return 该学生选的所有课程的courseId list
     */
    @Select("SELECT course_id FROM student_course where stu_number = #{stuNumber}")
    List<Long> listCourseIdsByStuNumber(long stuNumber);

    /**
     * 查询学生是否选某一门课
     * @param stuNumber 学号
     * @param courseId 课程id
     * @return 选了这门课为true，没选是false
     */
    @Select("SELECT COUNT(*) FROM student_course WHERE stu_number = #{stuNumber} AND course_id = #{courseId}")
    boolean queryCourseIsChosen(long stuNumber, long courseId);

    /**
     * 学生取消选课
     * @param stuNumber 学号
     * @param courseId 课程id
     * @return 成功为true，失败为false
     */
    @Delete("DELETE FROM student_course WHERE stu_number = #{stuNumber} AND course_id = #{courseId}")
    boolean deteleChooseCourse(long stuNumber,long courseId);
}

package com.example.octopus.dao;

import com.example.octopus.entity.user.Course;
import com.example.octopus.entity.user.StudentCourse;
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
     * @param studentCourse 学生-课程实体
     * @return 成功为true，失败为false
     */
    @Insert("INSERT INTO student_course (id,stu_number,course_id,course_name,stu_name,stu_major,stu_class,apply_time) VALUES(null,#{stuNumber},#{courseId},#{courseName},#{stuName},#{stuMajor},#{stuClass},CURRENT_TIMESTAMP)")
    boolean insertStudentCourse(StudentCourse studentCourse);

    /**
     * 根据id返回学生选课记录
     * @param id student-course表id
     * @return student-course实体类
     */
    @Select("SELECT * FROM student_course WHERE id = #{id}")
    StudentCourse getById(long id);

    /**
     * 根据courseId返回对应的student-course list
     * @param courseId 课程id
     * @return student-course list
     */
    @Select("SELECT * FROM student_course WHERE course_id = #{courseId}")
    List<StudentCourse> listByCourseId(long courseId);

    /**
     * 根据学生学号返回所有该学生选的课的courseId
     * @param stuNumber 学生学号
     * @return 该学生选的所有课程的courseId list
     */
    @Select("SELECT course_id FROM student_course WHERE stu_number = #{stuNumber}")
    List<Long> listCourseIdsByStuNumber(long stuNumber);

    /**
     * 根据courseId返回所有选了该课程的学生的stuNumber
     * @param courseId 课程id
     * @return 所有选了该课程的学生的stuNumber list
     */
    @Select("SELECT UNIQUE stu_number FROM student_course WHERE course_id = #{courseId}")
    List<Long> listStuNumbersByCourseId(long courseId);



    /**
     * 查询学生是否选某一门课
     * @param stuNumber 学号
     * @param courseId 课程id
     * @return 选了这门课为true，没选是false
     */
    @Select("SELECT COUNT(*) FROM student_course WHERE stu_number = #{stuNumber} AND course_id = #{courseId}")
    boolean getCourseIsChosen(long stuNumber, long courseId);

    /**
     * 根据id删除学生-课程记录
     * @param id 记录id
     * @return 成功为true，失败为false
     */
    @Delete("DELETE FROM student_course WHERE id = #{id}")
    boolean deleteStudentCourse(long id);

    /**
     * 根据学号和课程id删除学生-课程记录
     * @param stuNumber 学号
     * @param courseId 课程id
     * @return 成功为true，失败为false
     */
    @Delete("DELETE FROM student_course WHERE stu_number = #{stuNumber} AND course_id = #{courseId}")
    boolean deteleStudentCourse(long stuNumber, long courseId);

}

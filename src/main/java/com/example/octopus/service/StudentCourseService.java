package com.example.octopus.service;

import com.example.octopus.entity.user.StudentCourse;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/9 4:14 下午
 */
public interface StudentCourseService {

    /**
     * 显示所有课程-学生信息
     * @return 返回所有课程-学生列表
     */
    List<StudentCourse> listStudentCourses();

    /**
     * 根据id返回student-course记录
     * @param id student-course表id
     * @return student-course实体类
     */
    StudentCourse getById(long id);

    /**
     * 根据学生学号返回所有该学生选的课的courseId
     * @param stuNumber 学生学号
     * @return 该学生选的所有课程的courseId list
     */
    List<Long> listCourseIdsByStuNumber(long stuNumber);

    /**
     * 根据教师号返回教授的课程的student-course list
     * @param teaNumber 教师编号
     * @return student-course list
     */
    List<StudentCourse> listStudentCoursesByTeaNumber(long teaNumber);

    /**
     * 根据学生号返回学生所选的student_course list
     * @param stuNumber 学生号
     * @return student_course list
     */
    List<StudentCourse> listStudentCoursesByStuNumber(long stuNumber);

    /**
     * 查询学生是否选某一门课
     * @param stuNumber 学号
     * @param courseId 课程id
     * @return 选了这门课为true，没选是false
     */
    boolean isChosen(long stuNumber, long courseId);

    /**
     * 根据学生id，课程id，更新或新增学生选课记录
     * @param stuNumber 学号
     * @param courseId 课程id
     * @return 是否成功更新或成功选课
     */
    boolean insertStudentCourse(long stuNumber, long courseId);

    /**
     * （不推荐使用）仅限新增学生选课记录
     * @param studentCourse 学生选课实体
     * @return 是否成功选课
     */
    boolean insertStudentCourse(StudentCourse studentCourse);

    /**
     * 删除学生选课记录
     * @param stuNumber 学号
     * @param courseId 课程id
     * @return 是否成功删除
     */
    boolean deleteStudentCourse(long stuNumber, long courseId);

    boolean deleteStudentCourse(long studentCourseId);

    /**
     * 一整个班级的学生全部选课
     */
    boolean insertByClass(String className, long course_id);

}

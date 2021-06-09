package com.example.octopus.service;

import com.example.octopus.entity.user.StudentCourse;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/9 4:14 下午
 */
public interface StudentCourseService {

    /**
     * 根据id返回学生选课记录
     * @param id student-course表id
     * @return student-course实体类
     */
    StudentCourse getById(long id);

    /**
     * 根据教师编号返回教授的课程的student-course list
     * @param teaNumber 教师编号
     * @return student-course list
     */
    List<StudentCourse> listStudentCoursesByTeaNumber(long teaNumber);

}

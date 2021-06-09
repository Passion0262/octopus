package com.example.octopus.service.impl;

import com.example.octopus.dao.StudentCourseMapper;
import com.example.octopus.dao.TeacherCourseMapper;
import com.example.octopus.entity.user.StudentCourse;
import com.example.octopus.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/9 4:15 下午
 */
public class StudentCourseServiceImpl implements StudentCourseService {

    @Autowired
    StudentCourseMapper studentCourseMapper;

    @Autowired
    TeacherCourseMapper teacherCourseMapper;

    @Override
    public StudentCourse getById(long id) {
        return studentCourseMapper.getById(id);
    }

    @Override
    public List<StudentCourse> listStudentCoursesByTeaNumber(long teaNumber) {
        ArrayList<StudentCourse> studentCourseList = new ArrayList<>();
        List<Long> courseIdList = teacherCourseMapper.listCourseIdsByTeaNumber(teaNumber);
        for (long courseId:courseIdList) {
            studentCourseList.addAll(studentCourseMapper.listByCourseId(courseId));
        }
        return studentCourseList;
    }
}

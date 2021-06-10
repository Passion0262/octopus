package com.example.octopus.service.impl;

import com.example.octopus.dao.CourseMapper;
import com.example.octopus.dao.StudentCourseMapper;
import com.example.octopus.dao.TeacherCourseMapper;
import com.example.octopus.dao.UserMapper;
import com.example.octopus.entity.user.Course;
import com.example.octopus.entity.user.Student;
import com.example.octopus.entity.user.StudentCourse;
import com.example.octopus.service.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/9 4:15 下午
 */
@Service
public class StudentCourseServiceImpl implements StudentCourseService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    StudentCourseMapper studentCourseMapper;

    @Autowired
    TeacherCourseMapper teacherCourseMapper;

    @Override
    public StudentCourse getById(long id) {
        return studentCourseMapper.getById(id);
    }

    @Override
    public List<Long> listCourseIdsByStuNumber(long stuNumber) {
        return studentCourseMapper.listCourseIdsByStuNumber(stuNumber);
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

    @Override
    public boolean isChosen(long stuNumber, long courseId) {
        return studentCourseMapper.getCourseIsChosen(stuNumber, courseId);
    }

    @Override
    public boolean insertStudentCourse(long stuNumber, long courseId) {
        Student s = userMapper.getStudentByStuNumber(stuNumber);
        Course c = courseMapper.getCourseById(courseId);
        StudentCourse studentCourse = new StudentCourse(1L,stuNumber,courseId,c.getCourseName(),s.getName(),s.getMajor(),s.getClassName(),null);
        return insertStudentCourse(studentCourse);
    }

    @Override
    public boolean insertStudentCourse(StudentCourse studentCourse) {
        return studentCourseMapper.insertStudentCourse(studentCourse);
    }

    @Override
    public boolean deleteStudentCourse(long stuNumber, long courseId) {
        return studentCourseMapper.deteleStudentCourse(stuNumber,courseId);
    }
}

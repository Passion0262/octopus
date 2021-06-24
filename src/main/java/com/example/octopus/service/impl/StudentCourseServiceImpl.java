package com.example.octopus.service.impl;

import com.example.octopus.dao.*;
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
    MajorMapper majorMapper;

    @Autowired
    ClassMapper classMapper;

    @Autowired
    StudentCourseMapper studentCourseMapper;

    @Autowired
    TeacherCourseMapper teacherCourseMapper;

    @Override
    public List<StudentCourse> listStudentCourses(){
        return studentCourseMapper.listStudentCourses();
    }

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
        if (isChosen(stuNumber,courseId)){
            deleteStudentCourse(stuNumber,courseId);
        }
        Student s = userMapper.getStudentByStuNumber(stuNumber);
        Course c = courseMapper.getCourseById(courseId);
        StudentCourse studentCourse = new StudentCourse(1L,stuNumber,courseId,c.getCourseName(),s.getName(),majorMapper.getNameById(s.getMajorId()),classMapper.getNameById(s.getClassId()),null);
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

    @Override
    public boolean insertByClass(String className, long course_id) {
        List<Student> studentList = userMapper.listByClassName(className);
        String courseName = courseMapper.getCourseById(course_id).getCourseName();
        for (Student s:studentList) {
            StudentCourse studentCourse = new StudentCourse(1L,s.getStuNumber(),course_id,courseName,s.getName(),majorMapper.getNameById(s.getMajorId()),className,null);
            studentCourseMapper.insertStudentCourse(studentCourse);
        }
        return true;
    }
}

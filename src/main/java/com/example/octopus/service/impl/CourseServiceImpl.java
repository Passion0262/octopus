package com.example.octopus.service.impl;

import com.example.octopus.dao.CourseMapper;
import com.example.octopus.dao.StudentCourseMapper;
import com.example.octopus.dao.TeacherCourseMapper;
import com.example.octopus.dao.UserMapper;
import com.example.octopus.entity.user.Course;
import com.example.octopus.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/26 5:28 下午
 * @modified By：
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    CourseMapper courseMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    StudentCourseMapper studentCourseMapper;

    @Autowired
    TeacherCourseMapper teacherCourseMapper;

    @Override
    public Course getCourseById(long id) {
        return courseMapper.getCourseById(id);
    }

    @Override
    public List<Course> listCourses() {
        return courseMapper.listCourses();
    }

    @Override
    public List<Course> listCoursesByStuNumber(long stuNumber) {
        List<Course> courseList = new ArrayList<>();
        List<Long> courseIdList =  studentCourseMapper.listCourseIdsByStuNumber(stuNumber);
        for (long id:courseIdList){
            courseList.add(courseMapper.getCourseById(id));
        }
        return courseList;
    }

    @Override
    public List<Course> listCoursesByTeaNumber(long teaNumber) {
//        List<Course> courseList = new ArrayList<>();
//        List<Long> courseIdList = teacherCourseMapper.listCourseIdsByTeaNumber(teaNumber);
//        for (long id:courseIdList){
//            courseList.add(courseMapper.getCourseById(id));
//        }
//        return courseList;
        return courseMapper.listCoursesByTeaId(teaNumber);
    }

    @Override
    public boolean isFull(long courseId) {
        Course course = getCourseById(courseId);
        return course.getNumParticipated() >= course.getNumAllowed();
    }

    @Override
    public boolean insertCourse(Course course) {
        return courseMapper.insertCourse(course);
    }


    @Override
    public boolean updateCourse(Course course) {
        return courseMapper.updateCourse(course);
    }

    @Override
    public boolean deleteCourseById(long courseid) {
        return courseMapper.deleteCourseById(courseid);
    }
}

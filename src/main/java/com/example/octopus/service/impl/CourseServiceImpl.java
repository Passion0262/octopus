package com.example.octopus.service.impl;

import com.example.octopus.dao.CourseMapper;
import com.example.octopus.dao.StudentCourseMapper;
import com.example.octopus.entity.user.Course;
import com.example.octopus.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    StudentCourseMapper studentCourseMapper;

    @Override
    public boolean insertChooseCourse(long stuNumber, long courseId) {
        return studentCourseMapper.insertStudentCourse(stuNumber,courseId);
    }

    @Override
    public boolean deleteChooseCourse(long stuNumber, long courseId) {
        return studentCourseMapper.deteleChooseCourse(stuNumber,courseId);
    }


    @Override
    public List<Course> listCourses() {
        return courseMapper.listCourses();
    }

    @Override
    public Course findCourseById(long id) {
        return courseMapper.getCourseById(id);
    }

    @Override
    public List<Course> findCourseByStuNumber(long stuNumber) {
        return studentCourseMapper.getCourseByStuNumber(stuNumber);
    }

    @Override
    public boolean isChosen(long stuNumber, long courseId) {
        return studentCourseMapper.queryCourseIsChosen(stuNumber, courseId);
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

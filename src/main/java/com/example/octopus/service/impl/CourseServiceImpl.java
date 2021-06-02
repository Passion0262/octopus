package com.example.octopus.service.impl;

import com.example.octopus.dao.CourseMapper;
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

    @Override
    public List<Course> findAllCourses() {
        return courseMapper.queryAllCourses();
    }

    @Override
    public Course findCourseById(long id) {
        return courseMapper.queryCourse(id);
    }

    @Override
    public boolean saveCourse(Course course) {
        return courseMapper.saveCourse(course);
    }

    @Override
    public boolean deleteCourseById(long courseid) {
        return courseMapper.deleteCourseById(courseid);
    }
}

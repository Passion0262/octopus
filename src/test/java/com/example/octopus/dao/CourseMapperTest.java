package com.example.octopus.dao;

import com.example.octopus.entity.user.Course;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/1 9:24 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class CourseMapperTest {

    @Autowired
    CourseMapper courseMapper;

    @Test
    void listCourses() {
    }

    @Test
    void getCourseById() {
    }

    @Test
    void insertCourse() {
    }

    @Test
    void updateCourse() {
//        Course course = courseMapper.getCourseById(1);
//        course.setCourseName("大数据");
//        courseMapper.updateCourse(course);
    }

    @Test
    void deleteCourseById() {
//        courseMapper.deleteCourseById(7L);
    }

	@Test
	void testListCourses() {
        System.out.println(courseMapper.listCourses());
	}
}
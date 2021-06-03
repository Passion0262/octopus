package com.example.octopus.service.impl;

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
 * @date ：Created in 2021/6/1 9:26 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class CourseServiceImplTest {

    private static final long STUDENT_ID = 1L;
    private static final long COURSE_ID = 1L;

    @Autowired
    CourseServiceImpl courseService;

    @Test
    void insertChooseCourse() {
        boolean result = courseService.insertChooseCourse(STUDENT_ID,COURSE_ID);
        assertTrue(result);
    }

    @Test
    void findAllCourses() {
        List<Course> list = courseService.findAllCourses();
        for (Course c:list) {
            System.out.println(c.getCourseName());
        }
        assertEquals(3,list.size());
    }

    @Test
    void saveCourse() {
    }

    @Test
    void deleteCourseById() {
    }
}
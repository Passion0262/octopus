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
    void saveCourse() {
    }

    @Test
    void deleteCourseById() {
    }

    @Test
    void ListCourses() {
//        List<Course> list = courseService.listCourses();
//        for (Course c:list) {
//            System.out.println(c.getCourseName());
//        }
//        assertEquals(3,list.size());
    }

    @Test
    void getCourseById() {
    }

    @Test
    void listCourseByStuNumber() {
        List<Course> list = courseService.listCoursesByStuNumber(1L);
        list.forEach(System.out::println);
    }

    @Test
    void isChosen() {
    }

    @Test
    void InsertChooseCourse() {
    }

    @Test
    void deleteChooseCourse() {
    }

    @Test
    void insertCourse() {
    }

    @Test
    void updateCourse() {
    }

    @Test
    void testDeleteCourseById() {
    }
}
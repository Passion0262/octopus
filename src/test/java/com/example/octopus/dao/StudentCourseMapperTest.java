package com.example.octopus.dao;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/3 2:54 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class StudentCourseMapperTest {

    private final static long STUNUMBER = 1L;
    private final static long COURSE_ID = 1L;

    @Autowired
    StudentCourseMapper studentCourseMapper;

    @Test
    void insertStudentCourse() {
        boolean result = studentCourseMapper.insertStudentCourse(STUNUMBER,COURSE_ID);
        assertTrue(result);
    }

    @Test
    void queryCourseByStuNumber() {
    }

    @Test
    void queryCourseIsChosen() {
    }
}
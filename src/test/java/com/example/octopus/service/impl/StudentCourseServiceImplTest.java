package com.example.octopus.service.impl;

import com.example.octopus.service.StudentCourseService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/10 2:36 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class StudentCourseServiceImplTest {

    @Autowired
    StudentCourseService studentCourseService;

    @Test
    void getById() {
    }

    @Test
    void listCourseIdsByStuNumber() {
        List<Long> courseIdList = studentCourseService.listCourseIdsByStuNumber(1L);
        courseIdList.forEach(System.out::println);
    }

    @Test
    void listStudentCoursesByTeaNumber() {
    }

    @Test
    void isChosen() {
    }

    @Test
    void insertStudentCourse() {
    }

    @Test
    void testInsertStudentCourse() {
    }

    @Test
    void deleteStudentCourse() {
    }

    @Test
    void insertByClass() {
        studentCourseService.insertByClass("计算机",1L);
    }
}
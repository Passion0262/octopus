package com.example.octopus.dao;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/8 3:09 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class TeacherCourse_MapperTest {

    private static final Long TEACHER_ID = 1L;
    private static final Long CLASS_ID = 3L;

//    @Autowired
//    TeacherCourseMapper teacherCourse_mapper;

//    @Test
//    void listClass_idsByTeaNumber() {
//        List<Long> list = teacherCourse_mapper.listClass_idsByTeaNumber(TEACHER_ID);
//        list.forEach(System.out::println);
//    }
//
//    @Test
//    void listTeaNumbersByClass_Id() {
//        List<Long> list = teacherCourse_mapper.listTeaNumbersByClass_Id(CLASS_ID);
//        list.forEach(System.out::println);
//    }
}
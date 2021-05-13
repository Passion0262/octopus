package com.example.octopus.service.impl;

import com.example.octopus.entity.Student;
import com.example.octopus.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/13 12:53 下午
 * @modified By：
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class StudentServiceImplTest {

    @Autowired
    StudentService studentService = new StudentServiceImpl();

    @Test
    void getStudentList() {
        List<Student> list = studentService.getStudentList();
        assertEquals(2, list.size());
    }

    @Test
    void findStudentById() {
        Student stu = studentService.findStudentById(1L);
        assertEquals("小明", stu.getName());
    }
}
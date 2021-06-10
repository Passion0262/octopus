package com.example.octopus.dao;

import com.example.octopus.entity.user.Student;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Time;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/10 5:13 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void listAllStudents() {
    }

    @Test
    void getStudentByStuNumber() {
    }

    @Test
    void getStudentByName() {
    }

    @Test
    void getStudentByStuNumberAndPassword() {
    }

    @Test
    void insertStudent() {
        Student student = new Student(3L,"1","1","1","1","1",1,null, Time.valueOf(LocalTime.MAX));
        userMapper.insertStudent(student);
    }

    @Test
    void updateStudent() {
    }

    @Test
    void updateLoginInfoByStuNumber() {
    }

    @Test
    void resetPassword() {
    }

    @Test
    void updatePhoneByStuNumber() {
    }

    @Test
    void deleteByStuNumber() {
    }
}
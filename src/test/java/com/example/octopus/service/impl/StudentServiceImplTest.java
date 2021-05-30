package com.example.octopus.service.impl;

import com.example.octopus.entity.user.Student;
import com.example.octopus.service.UserService;
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
    UserService userService = new UserServiceImpl();

    @Test
    void getStudentList() {
        List<Student> list = userService.getStudentList();
        assertEquals(2, list.size());
    }

    @Test
    void findStudentById() {
        Student stu = userService.findStudentByStuNumber("1");
        assertEquals("小明", stu.getName());
    }

    @Test
    void login() {
        String stuNumber = "6201924124";
        String password = "xiaoming";
        Student stu = userService.login(stuNumber, password);
        assertEquals("13888888888",stu.getPhoneNumber());
    }

//    @Test
//    void register() {
//    }

    @Test
    void updateLoginInfo() {
        String stuNumber = "6201924124";
        userService.updateLoginInfo(stuNumber);
    }
}
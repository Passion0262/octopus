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
class UserServiceImplTest {

    long stuNumber = 1L;
    String password = "123";

    @Autowired
    UserService userService = new UserServiceImpl();

    @Test
    void getStudentList() {
        List<Student> list = userService.getStudentList();
        assertEquals(2, list.size());
    }

    @Test
    void findStudentById() {
        Student stu = userService.findStudentByStuNumber(stuNumber);
        assertEquals("admin", stu.getName());
    }

    @Test
    void login() {
        Student stu = userService.login(stuNumber, password);
        assertEquals("12345678",stu.getPhoneNumber());
    }

//    @Test
//    void register() {
//    }

    @Test
    void updateLoginInfo() {
        userService.updateLoginInfo(stuNumber);
    }

    @Test
    void updatePhoneNumber() {
        String new_phone = "33333";
        boolean result = userService.updatePhoneNumber(stuNumber,new_phone);
        assertTrue(result);
    }
}
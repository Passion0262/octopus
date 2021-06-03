package com.example.octopus.service.impl;

import com.example.octopus.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/2 9:11 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class UserServiceImplTest {

    @Autowired
    UserService userService;

    @Test
    void getStudentList() {
    }

    @Test
    void findStudentByStuNumber() {
    }

    @Test
    void findStudentByName() {
    }

    @Test
    void login() {
    }

    @Test
    void updateLoginInfo() {
    }

    @Test
    void resetPassword() {
    }

    @Test
    void updatePhoneNumber() {
        String stuNumber = "1";
        String new_phone = "33333";
        boolean result = userService.updatePhoneNumber(stuNumber,new_phone);
        assertTrue(result);
    }
}
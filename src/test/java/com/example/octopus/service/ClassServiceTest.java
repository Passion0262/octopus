package com.example.octopus.service;

import com.example.octopus.entity.user.Class_;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/8 3:39 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class ClassServiceTest {

    @Autowired
    ClassService classService;

    @Test
    void listClass_s() {
    }

    @Test
    void getClass_Byid() {
    }

    @Test
    void listClass_sByTeaNumber() {
        List<Class_> list = classService.listClass_sByTeaNumber(1L);
        list.forEach(System.out::println);
    }

    @Test
    void deleteByClassName() {
        classService.deleteByClassName("大数据");
    }
}
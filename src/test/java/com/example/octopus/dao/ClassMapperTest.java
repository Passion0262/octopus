package com.example.octopus.dao;

import com.example.octopus.entity.user.Class_;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author ：shadow
 * @date ：Created in 2021/6/8 3:58 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class ClassMapperTest {

    @Autowired
    ClassMapper classMapper;

    @Test
    void insertClass() {
//        Class_ class_ = new Class_(1L,"大数据","计算机","jack",null);
//        classMapper.insertClass(class_);
    }

    @Test
    void listClass_s() {
    }

    @Test
    void getById() {
    }
}
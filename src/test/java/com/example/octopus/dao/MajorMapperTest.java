package com.example.octopus.dao;

import com.example.octopus.entity.user.Major;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/5 8:36 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class MajorMapperTest {

    @Autowired
    MajorMapper majorMapper;

    @Test
    void listMajors() {
    }

    @Test
    void getByMajorCode() {
    }

    @Test
    void insertCourse() {
        Major major = new Major(1,"1212","计算机",null,"as");
        majorMapper.insertMajor(major);
    }

    @Test
    void updateCourse() {
        Major major = new Major(3,"123","计算机test",null,"as");
        majorMapper.updateMajor(major);
    }

    @Test
    void deleteMajorById() {
        majorMapper.deleteMajorById(3);
    }
}
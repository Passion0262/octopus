package com.example.octopus.service.impl;

import com.example.octopus.service.SubExperimentProgressService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/7/6 7:53 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class SubExperimentProgressServiceImplTest {

    @Autowired
    SubExperimentProgressService subExperimentProgressService;

    @Test
    void insert() {
        subExperimentProgressService.insert(3,1);
    }

    @Test
    void update() {
        subExperimentProgressService.update(3,1);
    }
}
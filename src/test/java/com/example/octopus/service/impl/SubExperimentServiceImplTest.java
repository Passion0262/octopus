package com.example.octopus.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/11 11:34 上午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class SubExperimentServiceImplTest {

    @Autowired
    SubExperimentServiceImpl subExperimentService;

    @Test
    void listSubExperimentsByModuleId() {
    }

    @Test
    void getById() {
    }

    @Test
    void getSubExperimentNumsByExperimentId() {
    }

    @Test
    void getSubExperimentIdByVideoId() {
        System.out.println(subExperimentService.getSubExperimentIdByVideoId(1L));
    }

    @Test
    void getSubExperimentByVideoId() {
    }
}
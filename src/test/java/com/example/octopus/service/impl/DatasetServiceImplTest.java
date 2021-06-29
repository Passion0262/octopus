package com.example.octopus.service.impl;

import com.example.octopus.service.DatasetService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/28 5:20 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class DatasetServiceImplTest {

    @Autowired
    DatasetService datasetService;

    @Test
    void increaseDownloadNum() {
        datasetService.increaseDownloadNum(1L);
    }
}
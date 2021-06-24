package com.example.octopus.dao.experiment;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/24 3:57 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class SubExperimentReportSaveMapperTest {

    @Autowired
    SubExperimentReportSaveMapper subExperimentReportSaveMapper;

    @Test
    void listByStuNumberAndSubExperimentId() {
    }

    @Test
    void getLatest() {
        System.out.println(subExperimentReportSaveMapper.getLatest(1L, 1L));
    }
}
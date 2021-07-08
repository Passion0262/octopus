package com.example.octopus.service.impl;

import com.example.octopus.entity.experiment.SubExperimentReportSubmit;
import com.example.octopus.service.SubExperimentReportSubmitService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/7/8 3:55 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class SubExperimentReportSubmitServiceImplTest {

    @Autowired
    SubExperimentReportSubmitService subExperimentReportSubmitService;

    @Test
    void listByTeaNumber() {
        subExperimentReportSubmitService.listByTeaNumber(1);
    }
}
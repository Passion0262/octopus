package com.example.octopus.dao.experiment;

import com.example.octopus.entity.experiment.SubExperimentReportSubmit;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/7/7 3:06 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class SubExperimentReportSubmitMapperTest {

    @Autowired
    SubExperimentReportSubmitMapper subExperimentReportSubmitMapper;

    @Test
    void insert() {
        SubExperimentReportSubmit submit = new SubExperimentReportSubmit(1,5,5,"22",null,true,null,1,1);
        subExperimentReportSubmitMapper.insert(submit);
    }
}
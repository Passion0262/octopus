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
    void listALL() {
        System.out.println(subExperimentReportSubmitMapper.listAll());
    }

    @Test
    void listBySubExperimentId() {
        System.out.println(subExperimentReportSubmitMapper.listBySubExperimentId(1));
    }

    @Test
    void listByTeaNumber() {
        System.out.println(subExperimentReportSubmitMapper.listByTeaNumber(3));
    }

    @Test
    void getByStuNumberAndSubExperimentId() {
        System.out.println(subExperimentReportSubmitMapper.getByStuNumberAndSubExperimentId(1,2));
    }

    @Test
    void getById() {
        System.out.println(subExperimentReportSubmitMapper.getById(6));
    }

    @Test
    void insert() {
        SubExperimentReportSubmit submit = new SubExperimentReportSubmit(2,1,"expName",5,"stuName","22",null,true,null,1,"teaName",1);
        subExperimentReportSubmitMapper.insert(submit);
    }

    @Test
    void updateBySubmit() {
    }

    @Test
    void updateByExamine() {
    }

    @Test
    void deleteById() {
    }
}
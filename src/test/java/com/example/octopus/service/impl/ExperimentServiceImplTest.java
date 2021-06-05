package com.example.octopus.service.impl;

import com.example.octopus.entity.experiment.Experiment;
import com.example.octopus.service.ExperimentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/2 4:41 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class ExperimentServiceImplTest {

    private static final long STUDENT_ID = 1L;

    @Autowired
    ExperimentService experimentService;

    @Test
    void listExperiments() {
        List<Experiment> list = experimentService.listExperiments();
        assertEquals(1,list.size());
    }

    @Test
    void listChosenByStuNumber() {
        List<Experiment> list= experimentService.listChosenByStuNumber(STUDENT_ID);
        for (Experiment exp:list) {
            System.out.println(exp.getName());
        }
    }
}
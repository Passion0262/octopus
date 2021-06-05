package com.example.octopus.dao;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/2 4:14 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class ExperimentMapperTest {

    private static final long STUDENG_ID = 1L;

    @Autowired
    ExperimentMapper experimentMapper;

    @Test
    void queryAllExperiments() {
//        List<Experiment> list = experimentMapper.queryAllExperiments();
//        assertEquals(1,list.size());
    }

    @Test
    void listChosenExperiments() {
        List<Long> list = experimentMapper.listChosenExperiments(STUDENG_ID);
        for (long i:list) {
            System.out.println(i);
        }
    }
}
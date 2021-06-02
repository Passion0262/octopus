package com.example.octopus.dao;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/2 4:14 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class ExperimentMapperTest {

    @Autowired
    ExperimentMapper experimentMapper;

    @Test
    void queryAllExperiments() {
//        List<Experiment> list = experimentMapper.queryAllExperiments();
//        assertEquals(1,list.size());
    }
}
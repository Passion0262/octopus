package com.example.octopus.dao;

import com.example.octopus.dao.experiment.ExperimentMapper;
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

    private static final long STUDENG_ID = 1L;

    @Autowired
    ExperimentMapper experimentMapper;

}
package com.example.octopus.dao.experiment;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
class SubExperimentProgressMapperTest {
	@Autowired
	SubExperimentProgressMapper subExperimentProgressMapper;

	@Test
	void getAllOperateTime() {
		System.out.println(subExperimentProgressMapper.getAllOperateTime());
	}

	@Test
	void getOperateTimeByTeacherId() {
	}

	@Test
	void update() {
//		subExperimentProgressMapper.update();
	}
}
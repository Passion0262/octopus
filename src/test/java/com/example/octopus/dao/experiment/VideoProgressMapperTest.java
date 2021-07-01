package com.example.octopus.dao.experiment;

import com.example.octopus.entity.VOs.VideoStudySummaryVO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class VideoProgressMapperTest {

    @Autowired
	VideoProgressMapper videoProgressMapper;

    @Test
    void getByVideoIdAndStuNumber() {
		System.out.println(videoProgressMapper.getLatestByVideoIdAndStuNumber(1L, 2L));
	}

	@Test
	void getVideoStudySummaryByTeaId() {
	}

	@Test
	void getAllVideoStudySummary() {
		List<VideoStudySummaryVO> videoStudySummaryVOS = videoProgressMapper.getAllVideoStudySummary();
		System.out.println(videoStudySummaryVOS.get(1).getProgress());
	}

	@Test
	void getVideoStudySummaryByTeacherId() {
		System.out.println(videoProgressMapper.getVideoStudySummaryByTeacherId(3));
	}

	@Test
	void getFinishedVideoIdsByStuNumber() {
		System.out.println(videoProgressMapper.getFinishedVideoIdsByStuNumber(2));
	}

    @Test
    void countStudyTime() {
		System.out.println(videoProgressMapper.countStudyTime(55, 2));
	}
}
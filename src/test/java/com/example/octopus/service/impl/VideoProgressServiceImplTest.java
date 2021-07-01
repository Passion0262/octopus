package com.example.octopus.service.impl;

import com.example.octopus.service.VideoProgressService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/30 6:09 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class VideoProgressServiceImplTest {

    @Autowired
    VideoProgressService videoProgressService;

    @Test
    void getCourseProgress() {
        System.out.println(videoProgressService.getCourseProgress(1L, 2L));
    }
}
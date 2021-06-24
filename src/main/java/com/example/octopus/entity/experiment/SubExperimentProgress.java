package com.example.octopus.entity.experiment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 子实验学习时间记录
 * @author ：shadow
 * @date ：Created in 2021/6/23 8:57 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubExperimentProgress {

    private long id;

    private long subExperimentId;

    private long stuNumber;

    private Timestamp startTime;

    private Timestamp endTime;

    private int validStudyTime;

}

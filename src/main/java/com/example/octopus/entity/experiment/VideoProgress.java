package com.example.octopus.entity.experiment;

import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/19 4:03 下午
 */
@Data
public class VideoProgress {

    private long id;

    private long videoId;

    private long stuNumber;

    private Timestamp startTime;     //开始学习时间

    private Timestamp endTime;       //结束学习时间

    private int studyTime;      //有效学习时长,单位:秒

    private int progress;       //进度,百分比,范围：0-100

    private int lastVideoProgress;      //上次视频进度,单位：秒

}

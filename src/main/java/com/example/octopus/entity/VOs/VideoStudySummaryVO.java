package com.example.octopus.entity.VOs;

import lombok.Data;
import org.apache.ibatis.annotations.Delete;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * @author: Hao
 * @date: 2021/7/5 17:49
 * 视频学习汇总视图类
 * 相关操作在video的service、impl、mapper中
 */
@Data
public class VideoStudySummaryVO {
	private long stuNumber;
	private String stuName;
	private long courseId;
	private String courseName;
	private int studyTimeTotal;  //学习总时长
	private Timestamp firstStartTime;  //首次开始学习时间
	private Timestamp lastEndTime;  //末次结束学习时间
}

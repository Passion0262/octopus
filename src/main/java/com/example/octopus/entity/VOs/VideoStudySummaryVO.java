package com.example.octopus.entity.VOs;

import lombok.Data;
import org.apache.ibatis.annotations.Delete;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * 视频学习汇总视图类，相关操作在video的service、impl、mapper中
 * @author: Hao
 * @date: 2021/7/5 17:49
 */
@Data
public class VideoStudySummaryVO {
	private long stuNumber;
	private String stuName;
	private long courseId;
	private String courseName;
	private int studyTimeTotal;  //学习总时长，单位为秒
	private String duration = "";  //学习时长，x小时x分x秒
	private Timestamp firstStartTime;  //首次开始学习时间
	private Timestamp lastEndTime;  //末次结束学习时间

	public void sec2time() {
		int sec, min;
		int hour = this.studyTimeTotal / 3600;

		if (hour > 0) {
			min = this.studyTimeTotal % 3600 / 60;
			this.duration = hour + "小时";
		} else min = this.studyTimeTotal / 60;

		sec = this.studyTimeTotal % 60;
		this.duration += min + "分" + sec + "秒";
	}
}

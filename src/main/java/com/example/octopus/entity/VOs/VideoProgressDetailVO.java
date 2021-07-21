package com.example.octopus.entity.VOs;

import lombok.Data;
import org.bouncycastle.util.Times;

import java.sql.Timestamp;

/**
 * @author: Hao
 * @date: 2021/7/5 18:54
 * 视频学习详情视图类
 * 相关操作在videoProcess的service、impl、mapper中
 */
@Data
public class VideoProgressDetailVO {
	private long stuNumber;
	private String name;
	private long videoId;
	private String videoName;
	private long courseId;
	private String courseName;
	private int studyTimeSum;  //该学生学习此视频的时长
	private String duration="";  //学习时长，x小时x分x秒
	private Timestamp veryStartTime;  //首次学习时间
	private Timestamp veryLastTime;  //末次学习时间
	public void sec2time() {
		int sec, min;
		int hour = this.studyTimeSum / 3600;

		if (hour > 0) {
			min = this.studyTimeSum % 3600 / 60;
			this.duration = hour + "小时";
		} else min = this.studyTimeSum / 60;

		sec = this.studyTimeSum % 60;
		this.duration += min + "分" + sec + "秒";
	}
}

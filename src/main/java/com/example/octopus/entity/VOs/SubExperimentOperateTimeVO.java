package com.example.octopus.entity.VOs;

import com.example.octopus.entity.experiment.SubExperimentProgress;
import lombok.Data;

/**
 * @author: Hao
 * @date: 2021/6/26 16:11
 * 实验操作时长视图类
 */
@Data
public class SubExperimentOperateTimeVO extends SubExperimentProgress {
	private String name;  //学生名
	private String className;  //班级名
	private String majorName;  //专业名
	private String courseName;  //课程名（开课计划）
	private String subExperimentName;  //实验任务名
	private String duration = "";  //学习时长，x小时x分x秒

	public void sec2time() {
		int sec, min;
		int hour = this.getValidStudyTime() / 3600;

		if (hour > 0) {
			min = this.getValidStudyTime() % 3600 / 60;
			this.duration = hour + "小时";
		} else min = this.getValidStudyTime() / 60;

		sec = this.getValidStudyTime() % 60;
		this.duration += min + "分" + sec + "秒";
	}
}

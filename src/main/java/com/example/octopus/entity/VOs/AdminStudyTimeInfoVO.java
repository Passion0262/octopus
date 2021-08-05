package com.example.octopus.entity.VOs;

import lombok.Data;

import java.sql.Date;

/**
 * 管理员/教师登录信息显示视图表，用于显示近几天的视频和实验学习时长
 * @author: Hao
 * @date: 2021/7/10 11:22
 */

@Data
public class AdminStudyTimeInfoVO {
	private int sumStudyTime;
	private Date studyDate;
	private Date today;
}

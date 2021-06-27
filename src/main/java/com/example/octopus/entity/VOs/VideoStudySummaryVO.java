package com.example.octopus.entity.VOs;

import com.example.octopus.entity.experiment.VideoProgress;
import lombok.Data;

/**
 * @author: Hao
 * @date: 2021/6/26 11:32
 * 不映射数据库表的前端显示类：视频学习汇总
 */

@Data
public class VideoStudySummaryVO extends VideoProgress {
	private String name;  //学生名
	private String className;  //班级名
	private String majorName;  //专业名
	private String courseName;  //课程名

}

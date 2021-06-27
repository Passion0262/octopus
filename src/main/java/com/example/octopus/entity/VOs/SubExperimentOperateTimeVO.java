package com.example.octopus.entity.VOs;

import com.example.octopus.entity.experiment.SubExperimentProgress;
import lombok.Data;

/**
 * @author: Hao
 * @date: 2021/6/26 16:11
 * 不映射数据库表的前端显示类：实验操作时长
 */
@Data
public class SubExperimentOperateTimeVO extends SubExperimentProgress {
	private String name;  //学生名
	private String className;  //班级名
	private String majorName;  //专业名
	private String courseName;  //课程名（开课计划）
	private String subExperimentName;  //实验任务名

}

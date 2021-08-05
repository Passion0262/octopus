package com.example.octopus.entity.VOs;

import lombok.Data;

/**
 * 子实验详情视图类，相关操作存放位置与SubExperiment相同
 * @author: Hao
 * @date: 2021/7/12 11:10
 */
@Data
public class SubExperimentDetailVO {
	//实验下分多个模块，每个模块下又分多个子实验
	//子实验和模块的id是自动生成的，具体要看他们的number才能显明顺序

	private long subExperimentId;  //子实验id
	private String subExperimentName;  //子实验名
	private int subExperimentNumber;  //子实验小节号

	private long moduleId;  //模块id
	private String moduleName;  //模块名
	private int moduleNumber;  //模块号

	private long experimentId;  //实验id
	private String experimentName;  //实验名

	private long courseId;  //课程id：注意管理员显示全部，故应为course_static_id；普通教师显示自己的，故应为开课表中的course_id
	private String courseName;  //课程名
}

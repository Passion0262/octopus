package com.example.octopus.entity.VOs;

import lombok.Data;

/**
 * @author: Hao
 * @date: 2021/7/12 18:11
 * 子项目详情视图类，相关操作存放位置与SubProject相同
 */

@Data
public class SubProjectDetailVO {
	//项目下分多个模块，每个模块下又分多个子项目
	//子项目和模块的id是自动生成的，具体要看他们的number才能显明顺序

	private long subProjectId;  //子实验id
	private String subProjectName;  //子实验名
	private int subProjectNumber;  //子实验号

	private long moduleId;  //模块id
	private String moduleName;  //模块名
	private int moduleNumber;  //模块号

	private long projectId;  //项目id
	private String projectName;  //项目名


}

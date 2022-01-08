package com.example.octopus.entity.vo.experiment;

import lombok.Data;

/**
 * 实验报告汇总视图类，与SubExperimentReportSubmit相关位置相同
 * @author: Hao
 * @date: 2021/7/22 19:01
 */
@Data
public class SubExperimentReportSummaryVO {
	private long experimentId;
	private String experimentName;
	private long subExperimentId;
	private String subExperimentName;

	private long classId;  //班级号
	private String className;  //班级名

	private int examinedNum;  //已批阅人数
	private int unexaminedNum;  //未批阅人数
	private int notSubmitNum;  //未提交人数（已经做过实验，在sub_experiment_progress中有记录，但是没有提交）
}

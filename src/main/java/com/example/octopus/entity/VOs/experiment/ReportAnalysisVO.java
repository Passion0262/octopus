package com.example.octopus.entity.VOs.experiment;

import lombok.Data;

/**
 * 实验报告管理-->报告分析：在同一子实验下，按照班级，得到各个成绩区间人数
 * @author: Hao
 * @date: 2021/8/5 10:23
 */

@Data
public class ReportAnalysisVO {
	private long classId;
	private String className;
	private int[] scores;
}

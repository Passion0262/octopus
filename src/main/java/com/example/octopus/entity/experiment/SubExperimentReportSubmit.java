package com.example.octopus.entity.experiment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 子实验报告，用于提交，只可以提交一次
 * @author ：shadow
 * @date ：Created in 2021/6/24 1:54 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubExperimentReportSubmit {

    private long id;

    private long subExperimentId;

    private long stuNumber;

    private String content; // 报告内容

//    private String reportPath;  //报告pdf的存储路径

    private Timestamp submitTime;

    private boolean examined;  //是否已经审核

    private Timestamp examinedTime;  //审核时间

    private long teaNumber;  //审核教师

    private int score;  //报告得分

}

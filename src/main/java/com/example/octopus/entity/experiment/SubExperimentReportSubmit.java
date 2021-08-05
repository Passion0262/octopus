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

    private String subExperimentName;   //不在sub_experiment_report_submit表中，子实验名字

    private long stuNumber;

    private String stuName;     //不在sub_experiment_report_submit表中，学生名字

    private long classId;  //不在sub_experiment_report_submit表中，学生所在班级号

    private String className;  //不在sub_experiment_report_submit表中，学生所在班级名

    private String content; // 报告内容

    private Timestamp submitTime;

    private boolean examined;  //是否已经审核

    private Timestamp examinedTime;  //审核时间

    private long teaNumber;  //审核教师，不需要手动输入，使用触发器自动查找填写

    private String teaName;     //不在sub_experiment_report_submit表中，教师名字

    private int score;  //报告得分

    /**
     * 使用此构造函数来进行插入操作
     */
    public SubExperimentReportSubmit(long subExperimentId, long stuNumber, String content) {
        this.subExperimentId = subExperimentId;
        this.stuNumber = stuNumber;
        this.content = content;
    }
}

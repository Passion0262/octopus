package com.example.octopus.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: Hao
 * @date: 2021/5/22 12:46
 *   实验报告管理
 */

@Entity
@Data
@Table
public class ExperimentReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String stuNumber;  //学生学号

    private String stuName;  //学生姓名

    private String mission;  //所属任务

    private String project;  //所属计划

    private String reportSource;  //报告来源

    private Float missionTotalScore;  //任务总分

    private Float missionScore;  //任务得分

    private Boolean examine;  //是否审核

    private String examineTime;  //审核时间

    private String uploadTime;  //提交时间

    private Float verifyPassRate;  //检测通过率

    private String verifyTime;  //检测时间

    private String experimentRealOperateTime;  //实验实际操作时长

    private String missionMonitor;  //任务监控

}

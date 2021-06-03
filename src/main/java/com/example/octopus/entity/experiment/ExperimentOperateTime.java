package com.example.octopus.entity.experiment;

import lombok.Data;


/**
 * @author: Hao
 * @date: 2021/5/22 13:05
 *   实验操作时长
 */
@Data
public class ExperimentOperateTime {
    private long id;

    private String stuNumber;  //学生学号

    private String stuName;  //学生姓名

    private String className;  //班级

    private String lessonPlan;  //开课计划

    private String experimentMission;  //实验任务

    private String operateTotalTime;  //操作总时长

    private String operateFirstTime;  //首次操作时间

    private String operateLastTime;  //末次操作时间

    private String operateDetail;  //操作详情

    private String experimentMonitor;  //实验监控


}

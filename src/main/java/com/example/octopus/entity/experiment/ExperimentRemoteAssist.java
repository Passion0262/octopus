package com.example.octopus.entity.experiment;

import lombok.Data;


/**
 * @author: Hao
 * @date: 2021/5/22 13:23
 *   实验远程协助
 */

@Data
public class ExperimentRemoteAssist {
    private long id;

    private String stuNumber;  //学生学号

    private String stuName;  //学生姓名

    private String lessonPlan;  //开课计划

    private String machineNum;  //机器编号

    private String requestTime;  //请求时间

    private Boolean process;  //是否处理

    private String processTime;  //处理时间

    private String processPerson;  //处理人
}

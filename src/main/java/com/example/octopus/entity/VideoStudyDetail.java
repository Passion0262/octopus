package com.example.octopus.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: Hao
 * @date: 2021/5/22 12:32
 *   视频学习详情
 */
@Entity
@Data
@Table
public class VideoStudyDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String stuNumber;  //学生学号

    private String stuName;  //学生姓名

    private String className;  //课程名称

    private String lectureName;  //讲座名称

    private String studyDate;  //讲座学习日期

    private String studyTime;  //讲座学习时长

    private String firstTime;  //讲座首次学习时间

    private String lastTime;  //讲座末次学习时间

    private String exceedTime;  //讲座超出学习时长

    private String firstIp;  //首次学习IP

    private String lastIp;  //末次学习IP

    private String testTime;  //考核时间

    private String project;  //所属计划

    private Boolean pass;  //是否通过

}

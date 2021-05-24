package com.example.octopus.entity;

import com.example.octopus.entity.user.Student;
import lombok.Data;

import javax.persistence.*;

/**
 * @author: Hao
 * @date: 2021/5/22 12:32
 * 视频学习进度表（存储学生视频学习进度）
 */
@Entity
@Data
@Table
public class VideoStudyProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "stuNumber")
    private Student student;  //学生学号

    private String stuName;  //学生姓名

    private String courseName;  //课程名称

    @ManyToOne
    @JoinColumn(name = "videoName")
    private VideoStudyOrigin videoStudyOrigin;  //视频id

    private String notePath;  //笔记存放地址

    private String studyDate;  //视频学习日期

    private String studyTime;  //视频学习时长

    private String firstTime;  //视频首次学习时间

    private String lastTime;  //视频末次学习时间

    //private String exceedTime;  //讲座超出学习时长

    private String firstIp;  //首次学习IP

    private String lastIp;  //末次学习IP

//    private String testTime;  //考核时间
//
//    private String project;  //所属计划
//
//    @Column(columnDefinition = "enum('通过','未通过','未审核')")
//    private String pass;  //是否通过

}

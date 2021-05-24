package com.example.octopus.entity.experiment;

import com.example.octopus.entity.user.Course;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/22 12:55 下午
 * @modified By：Hao
 * 实验任务表
 */
@Entity
@Table
@Data
public class Experiment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;    //编号

    @ManyToOne
    @JoinColumn(name = "courseName")
    private Course course;  //实验所属课程

    private String experimentName;  //实验任务名称

    private String experimentImagePath;  //实验任务简图地址

    private String experimentBrief;  //实验任务简介

    private String creator;  //实验创建人

    private Date createTime;  //实验创建时间


//    private String missionName;     //任务名称
//
//    private String experimentType;  //实验分类
//
//    private String environmentType;     //环境类型
//
//    private String experimentEnvironment;   //实验环境
//
//    private String environmentName; //环境名称
//
//    private String missionType; //任务类型
//
//    private String tag;     //相关标签
//
//    private int totalScore;     //任务总分
//
//    private int sortValue;  //排序值
//
//    private int timeLimit;  //时限
//
//    private String pictureUrl;      //缩图


}

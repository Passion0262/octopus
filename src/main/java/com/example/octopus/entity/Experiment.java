package com.example.octopus.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/22 12:55 下午
 * @modified By：
 */
@Entity
@Table
@Data
public class Experiment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;    //编号

    private String missionName;     //任务名称

    private String experimentType;  //实验分类

    private String environmentType;     //环境类型

    private String experimentEnvironment;   //实验环境

    private String environmentName; //环境名称

    private String missionType; //任务类型

    private String tag;     //相关标签

    private int totalScore;     //任务总分

    private int sortValue;  //排序值

    private int timeLimit;  //时限

    private String pictureUrl;      //缩图



}

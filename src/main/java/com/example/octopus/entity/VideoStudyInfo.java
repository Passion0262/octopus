package com.example.octopus.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author: Hao
 * @date: 2021/5/22 12:19
 *  视频学习汇总
 */
@Entity
@Data
@Table
public class VideoStudyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String stuNumber;  //学生学号

    private String stuName;  //学生姓名

    private String className;  //课程名称

    private String learningTime;  //学习时长

    private String firstTime;  //首次学习时间

    private String lastTime;  //末次学习时间

    private String exceedTime;  //超出学习时长

}

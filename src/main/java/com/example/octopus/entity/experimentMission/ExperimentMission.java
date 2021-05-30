package com.example.octopus.entity.experimentMission;

import com.example.octopus.entity.user.Course;
import lombok.Data;

import javax.persistence.*;

/**
 * @author: Hao
 * @date: 2021/5/22 12:46
 * 实验子任务原始内容表
 */

@Entity
@Data
@Table
public class ExperimentMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @ManyToOne
//    @JoinColumn(name = "courseId", nullable = false)
//    private Course course;  //视频所属课程

//    @ManyToOne
//    @JoinColumn(name = "experimentId", nullable = false)
//    private Experiment experimentId;  //所属实验id

    @Column(nullable = false)
    private String experimentName;  //实验任务名称

    @Column(nullable = false)
    private int experimentModuleNo;  //实验所属模块号

    @Column(nullable = false)
    private String experimentModuleName;  //实验所属模块名

    @Column(nullable = false)
    private int missionNo;  //任务序号

    @Column(nullable = false)
    private String missionName;  //任务名

    @Column(nullable = false)
    private String guidancePath;  //任务指导存放地址

    @Column(nullable = false)
    private String reportModelPath;  //实验报告模板存放地址

    @Column(nullable = false)
    private String discussionPath;  //文字交流存放地址

}

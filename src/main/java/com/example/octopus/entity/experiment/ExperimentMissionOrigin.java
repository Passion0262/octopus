package com.example.octopus.entity.experiment;

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
public class ExperimentMissionOrigin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "courseId", nullable = false)
    private Course course;  //视频所属课程

    @ManyToOne
    @JoinColumn(name = "experimentId", nullable = false)
    private Experiment experiment;  //所属实验id

    private String experimentName;  //实验任务名称

    private int experimentModuleNo;  //实验所属模块号

    private String experimentModuleName;  //实验所属模块名

    private int missionNo;  //任务序号

    private String missionName;  //任务名

    private String guidancePath;  //任务指导存放地址

    private String reportModelPath;  //实验报告模板存放地址

    private String discussionPath;  //文字交流存放地址

}

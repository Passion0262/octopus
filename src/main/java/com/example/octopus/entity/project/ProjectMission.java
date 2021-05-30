package com.example.octopus.entity.project;

import com.example.octopus.entity.user.Course;
import lombok.Data;

import javax.persistence.*;

/**
 * @author: Hao
 * @date: 2021/5/25 11:05
 * 项目任务表
 */

@Entity
@Data
@Table
public class ProjectMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @ManyToOne
//    @JoinColumn(name = "projectId", nullable = false)
//    private Project projectId;  //任务所属项目id

    @Column(nullable = false)
    private String projectname;    //项目名称

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

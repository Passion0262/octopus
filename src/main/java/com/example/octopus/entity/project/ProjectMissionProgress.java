package com.example.octopus.entity.project;

import com.example.octopus.entity.user.Student;
import lombok.Data;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

/**
 * @author: Hao
 * @date: 2021/5/25 11:07
 * 项目任务进度表（存储学生项目任务进度）
 */

@Entity
@Data
@Table
public class ProjectMissionProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @ManyToOne
//    @JoinColumn(name = "stuNumber")
//    private Student student;  //学生学号

    private String stuName;  //学生姓名

//    @ManyToOne
//    @JoinColumn(name = "missionId")
//    private ProjectMission missionId;  //项目任务id

    @Column(nullable = false)
    private String missionName;  //任务名

    private Time operateTotalTime;  //操作总时长

    private Date operateFirstTime;  //首次操作时间

    private Date operateLastTime;  //末次操作时间

    //private String operateDetail;  //操作详情

    private String monitor;  //实验监控

    private String reportPath;  //学生任务报告存放地址

    private Date reportSubmitTime;  //任务报告提交时间

    @Column(columnDefinition = "enum('审核通过','审核不通过')")
    private String reportPassed;  //任务报告审核通过与否

    private String reportExaminer;  //任务报告审查人

    private Date reportExamTime;  //任务报告审查时间

    private Float reportScore;  //任务报告得分

}

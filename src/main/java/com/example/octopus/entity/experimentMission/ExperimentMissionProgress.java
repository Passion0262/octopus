package com.example.octopus.entity.experimentMission;

import com.example.octopus.entity.user.Student;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: Hao
 * @date: 2021/5/24 21:48
 * 实验子任务进度表（存储学生任务进度）
 */

@Entity
@Data
@Table
public class ExperimentMissionProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @ManyToOne
//    @JoinColumn(name = "stuNumber")
//    private Student student;  //学生学号

    private String stuName;  //学生姓名

    private String courseName;  //课程名称

//    @ManyToOne
//    @JoinColumn(name = "missionId")
//    private ExperimentMission experiMissionOrigin;  //子任务id

    private Date operateTotalTime;  //操作总时长

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

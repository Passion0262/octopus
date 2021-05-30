package com.example.octopus.entity.user;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * 课程
 * @author ：shadow
 * @date ：Created in 2021/5/24 2:37 下午
 * @modified By：
 */
@Data
public class Course {

    private long id;

//    @Column(nullable = false,columnDefinition = "varchar(20) comment '课程名称'")
    private String courseName;

//    @Column(nullable = false,columnDefinition = "varchar(30) comment '授课老师'")
    private String teacher;

//    @Column(nullable = false,columnDefinition = "varchar(30) comment '分类'")
    private String classification;

//    @Column(nullable = false,columnDefinition = "varchar(255) comment '课程简介'")
    private String courseBrief;

//    @Column(nullable = false,columnDefinition = "varchar(255) comment '课程标图地址'")
    private String courseImagePath;

//    @Column(nullable = false,columnDefinition = "date comment '开始时间'")
    private Date startTime;

//    @Column(columnDefinition = "date comment '结束时间'")
    private Date endTime;

//    @Column(columnDefinition = "int comment '允许参加人数'")
    private int numAllowed;

//    @Column(nullable = false,columnDefinition = "int default 0 comment '已参加人数'")
    private int numParticipated;

//    @Column(nullable = false,columnDefinition = "enum('有效','无效') default '有效'")
    private String status;

//    @Column(nullable = false,columnDefinition = "date comment '开通申请时间'")
    private Date applyTime;
}

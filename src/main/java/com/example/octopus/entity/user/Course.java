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
@Entity
@Table
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "varchar(20) comment '课程名称'")
    private String courseName;

    @Column(columnDefinition = "varchar(30) comment '授课老师'")
    private String teacher;

    @Column(columnDefinition = "date comment '开始时间'")
    private Date startTime;

    @Column(columnDefinition = "date comment '结束时间'")
    private Date endTime;

    @Column(columnDefinition = "int comment '允许参加人数'")
    private int numAllowed;

    @Column(columnDefinition = "int comment '已参加人数'")
    private int numParticipated;

    @Column(columnDefinition = "varchar(20) comment '课程状态（有效/无效）'")
    private String status;

    @Column(columnDefinition = "date comment '开通申请时间'")
    private Date applyTime;
}

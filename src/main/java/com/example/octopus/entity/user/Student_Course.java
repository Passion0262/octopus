package com.example.octopus.entity.user;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/22 11:53 上午
 * @modified By：
 * 学生-课程关系表
 */
@Entity
@Table
@Data
public class Student_Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, columnDefinition = "varchar(20) comment '课程名称'")
    private String courseName;

    @Column(nullable = false, name = "teaNumber", columnDefinition = "varchar(20) comment '授课教师号'")
    private String teaNumber;

    @Column(nullable = false, columnDefinition = "varchar(30) comment '授课老师'")
    private String teacher;

    @Column(nullable = false, columnDefinition = "varchar(20) comment '学生姓名'")
    private String stuName;

    @Column(nullable = false, columnDefinition = "varchar(20) comment '学生专业'")
    private String stuMajor;

    @Column(nullable = false, columnDefinition = "varchar(20) comment '学生班级'")
    private String stuClass;

    @Column(nullable = false, columnDefinition = "date comment '申请时间'")
    private Date applyTime;

    @Column(columnDefinition = "varchar(20) comment '审核人员'")
    private String auditor;

    @Column(columnDefinition = "date comment '审核时间'")
    private Date auditTime;
}

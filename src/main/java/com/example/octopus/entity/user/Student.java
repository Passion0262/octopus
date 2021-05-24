package com.example.octopus.entity.user;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/12 2:26 下午
 * @modified By：
 */
@Entity
@Data
@Table
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "varchar(20) comment '学号'")
    private String stuNumber;

    @Column(columnDefinition = "varchar(20) comment '姓名'")
    private String name;

    @Column(columnDefinition = "varchar(30) comment '密码'")
    private String password;

    @Column(columnDefinition = "varchar(20) comment '所属专业'")
    private String major;

    @Column(columnDefinition = "varchar(20) comment '所属班级'")
    private String className;

    @Column(columnDefinition = "varchar(12) comment '联系手机'")
    private String phoneNumber;

    @Column(columnDefinition = "int comment '登陆次数'")
    private int loginNumber;

    @Column(columnDefinition = "date comment '最近登陆时间'")
    private Date lastLoginTime;

    @Column(columnDefinition = "time comment '学习总是长'")
    private Time studyTime;
}

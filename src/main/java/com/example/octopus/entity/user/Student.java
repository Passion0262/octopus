package com.example.octopus.entity.user;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/12 2:26 下午
 * @modified By：Hao
 * 学生表
 */
@Entity
@Data
@Table
public class Student {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
    @Column(name="stuNumber")
    private String stuNumber;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String major;

    @ManyToOne
    @JoinColumn(name = "className")
    private Class_ class_;
//    @Column(name= "className")
//    private String className;

    @Column(nullable = false,name="phoneNumber")
    private String phoneNumber;

    @Column(nullable = false,name="loginNumber")
    private int loginNumber;

    @Column(name="lastLoginTime")
    private Date lastLoginTime;

    @Column(name="studyTime")
    private Time studyTime;
}

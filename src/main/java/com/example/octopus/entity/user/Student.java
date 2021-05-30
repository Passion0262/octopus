package com.example.octopus.entity.user;

import lombok.Data;

import java.sql.Date;
import java.sql.Time;
import java.util.Set;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/12 2:26 下午
 * @modified By：Hao
 * 学生表
 */
@Data
public class Student {

    private String stuNumber;

    private String name;

    private String password;

    private String major;

    private String className;

    private String phoneNumber;

    private int loginNumber;

    private Date lastLoginTime;

    private Time studyTime;
}

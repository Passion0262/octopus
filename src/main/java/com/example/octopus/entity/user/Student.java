package com.example.octopus.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    private long stuNumber;

    private String name;

    // 数据库student表中不存储password，但为方便，在实体类中保留。对password进行操作时需要在implement中调用sysUserRoleMapper中的方法进行操作
    private String password;

    private long majorId;

    private long classId;

    private String phoneNumber;

    private int loginNumber;

    private Date lastLoginTime;

    private Time studyTime;
}

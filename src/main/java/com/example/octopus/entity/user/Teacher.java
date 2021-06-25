package com.example.octopus.entity.user;

import lombok.Data;

import java.sql.Date;

/**
 * @author: Hao
 * @date: 2021/5/24 19:26
 * 教师及管理员表（通过Boolean adminRights标记）
 */
@Data
public class Teacher {

    private long teaNumber;

    private String name;

    // 数据库teacher表中不存储password，但为方便，在实体类中保留。对password进行操作时需要在implement中调用sysUserRoleMapper中的方法进行操作
    private String password;

    private String majorCode;   //教授的专业的代码

    private Boolean adminRights;  //是否拥有管理权限。默认无权限

    private String phone;

    private String school;

    private int loginNumber;

    private Date lastLoginTime;

}

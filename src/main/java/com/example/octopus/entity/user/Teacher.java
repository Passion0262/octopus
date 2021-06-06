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

//    @Column(nullable = false, columnDefinition = "varchar(20) comment '姓名'")
    private String name;

//    @Column(nullable = false, columnDefinition = "varchar(20) comment '密码'")
    private String password;

    private String majorCode;   //教授的专业的代码

//    @Column(nullable = false, columnDefinition = "bool default false")
    private Boolean adminRights;  //是否拥有管理权限。默认无权限

//    @Column(nullable = false,name = "phone")
    private String phone;

//    @Column(name = "loginNumber")
    private int loginNumber;

//    @Column(name = "lastLoginTime")
    private Date lastLoginTime;

}

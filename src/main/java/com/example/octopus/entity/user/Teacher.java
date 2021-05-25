package com.example.octopus.entity.user;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * @author: Hao
 * @date: 2021/5/24 19:26
 * 教师及管理员表（通过Boolean adminRights标记）
 */
@Entity
@Data
@Table
public class Teacher {
    @Id
    @Column(name = "teaNumber", columnDefinition = "varchar(20) comment '教师号'")
    private String teaNumber;

    @Column(nullable = false, columnDefinition = "varchar(20) comment '姓名'")
    private String name;

    @Column(nullable = false, columnDefinition = "varchar(20) comment '密码'")
    private String password;

    @Column(nullable = false, columnDefinition = "bool default false")
    private Boolean adminRights;  //是否拥有管理权限。默认无权限

    @Column(nullable = false,name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "loginNumber")
    private int loginNumber;

    @Column(name = "lastLoginTime")
    private Date lastLoginTime;

}
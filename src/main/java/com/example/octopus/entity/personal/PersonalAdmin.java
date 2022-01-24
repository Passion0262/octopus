package com.example.octopus.entity.personal;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 个人用户管理员实体类
 * @author: Hao
 * @date: 2022/1/8 10:56
 */
@Data
public class PersonalAdmin {
	private String adminTel;  //个人用户管理员电话，登录账号，11位

	private String password;  //表中无password，为方便故在实体类中保留

	private int loginNumber;  //登录次数

	private Timestamp lastLoginTime; //上次登录时间

	private Timestamp thisLoginTime; //本次登录时间

}

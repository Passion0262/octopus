package com.example.octopus.entity.user;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 个人用户信息实体类personal_user
 * @author: Hao
 * @date: 2021/12/28 19:51
 */
@Data
public class PersonalUser {

	private long personalTel;  //个人电话，即登录账号，11位

	// 数据库personal_user表中不存储password，但为方便，在实体类中保留。对password进行操作时需要在implement中调用sysUserRoleMapper中的方法进行操作
	private String password;

	private boolean tourist;  //是否为游客（如不是才去检索其购买的课程）

	private int loginNumber;  //登录次数

	private Timestamp lastLoginTime; //上次登录时间

	private Timestamp thisLoginTime; //本次登录时间

}

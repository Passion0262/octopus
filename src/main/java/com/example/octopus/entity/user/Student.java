package com.example.octopus.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * 学生表
 * @author ：shadow
 * @date ：Created in 2021/5/12 2:26 下午
 * @modified By：Hao
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

	private String majorName;  //专业名，不在student表中

	private long classId;

	private String school;

	private String className;  //班级名，不在student表中

	private String phoneNumber;

	private int loginNumber;

	private Timestamp lastLoginTime;

	private Time studyTime;

	public Student(long stuNumber, String name, String phoneNumber) {
		this.stuNumber = stuNumber;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	public Student(long stuNumber, String name, String password, long majorId, long classId, String phoneNumber) {
		this.stuNumber = stuNumber;
		this.name = name;
		this.password = password;
		this.majorId = majorId;
		this.classId = classId;
		this.phoneNumber = phoneNumber;
	}

}

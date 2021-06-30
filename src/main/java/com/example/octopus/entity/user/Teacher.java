package com.example.octopus.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Hao
 * @date: 2021/5/24 19:26
 * 教师及管理员表（通过Boolean adminRights标记）
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {

	private long teaNumber;

	private String teaName;

	// 数据库teacher表中不存储password，但为方便，在实体类中保留。对password进行操作时需要在implement中调用sysUserRoleMapper中的方法进行操作
	private String password;

	private long majorId;   //教授的专业的代码

	private String majorName;  //专业名，不在teacher表中

	private Boolean adminRights;  //是否拥有管理权限。默认无权限

	private String phone;

	private String school;

	private int loginNumber;

	private Date lastLoginTime;


	public Teacher(long teaNumber, String teaName, String password, long majorId, Boolean adminRights, String phone, String school) {
		this.teaNumber = teaNumber;
		this.teaName = teaName;
		this.password = password;
		this.majorId = majorId;
		this.adminRights = adminRights;
		this.phone = phone;
		this.school = school;
	}

	public Teacher mergeUpdate(Teacher old, Teacher b) {
		try {
			return compare(old, b);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}


	public static <T> T compare(T old, T Obj2) throws Exception {
		Field[] fs = old.getClass().getDeclaredFields();
		for (Field f : fs) {
			f.setAccessible(true);
			Object v1 = f.get(old);
			Object v2 = f.get(Obj2);

			if (v2 == null) f.set(Obj2, v1);

		}
		return Obj2;
	}

}

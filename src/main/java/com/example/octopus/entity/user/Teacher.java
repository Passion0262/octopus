package com.example.octopus.entity.user;

import lombok.Data;

import java.lang.reflect.Field;
import java.sql.Date;

/**
 * @author: Hao
 * @date: 2021/5/24 19:26
 * 教师及管理员表（通过Boolean adminRights标记）
 */
@Data
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

	public Teacher() {
	}

	public Teacher(long teaNumber, String teaName, String password, long majorId, Boolean adminRights, String phone, String school) {
		this.teaNumber = teaNumber;
		this.teaName = teaName;
		this.password = password;
		this.majorId = majorId;
		this.adminRights = adminRights;
		this.phone = phone;
		this.school = school;
	}


//	public Teacher mergeUpdateTea(Teacher orginTea, Teacher newTea) throws IllegalAccessException {
//		Class orginTeaClass = orginTea.getClass();
//		Class newTeaClass = newTea.getClass();
//		Field[] orginFields = orginTeaClass.getDeclaredFields();
//		Field[] newFields = newTeaClass.getDeclaredFields();
//
//		for (int i = 0; i < newFields.length; i++) {
//			Field orginF = orginFields[i];
//			Field newF = newFields[i];
//            orginF.setAccessible(true);
//            newF.setAccessible(true);
//            if (newF.get(newTea)==null){
//                newF.s
//            }
//
//            System.out.println("属性名:" + f.getName() + " 属性值:" + f.get(t));
//		}
//
//		return newTea;
//	}
}

package com.example.octopus.service;

import com.example.octopus.entity.user.SysUserRole;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/29 5:03 下午
 * @modified By：
 */
public interface SysUserRoleService {

	/**
	 * 查询角色
	 */
	List<SysUserRole> listByUserId(long number);


	/**
	 * 在sys_user_role表中，通过用户号（包括学生号和教师号）来查找对应的角色权限
	 *
	 * @param stuNumber 用户号，包括学生号和教师号，非单一的学生号
	 * @return 返回角色权限代码
	 */
	int getRoleIdByUserId(long stuNumber);

	/**
	 * 根据用户账号查询
	 */
	SysUserRole getByUserId(long stuNumber);

	/**
	 * 添加sysUserRole
	 */
	boolean addUserRole(SysUserRole sysUserRole);

	/**
	 * 通过账号+手机号重置密码
	 *
	 * @param number   账号，学生号与教师号皆可
	 * @param phone    手机号
	 * @param password 更新的密码
	 * @return 重置成功与否
	 */
	boolean updatePasswordByIdAndPhone(long number, String phone, String password);


}

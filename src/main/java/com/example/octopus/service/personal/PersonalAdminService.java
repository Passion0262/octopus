package com.example.octopus.service.personal;

import com.example.octopus.entity.personal.PersonalAdmin;

/**
 * @author: Hao
 * @date: 2022/1/8 11:40
 */

public interface PersonalAdminService {
	/**
	 * 通过个人用户系统管理员账号获取管理员信息
	 */
	PersonalAdmin getPersonalAdmin(String adminTel);

	/**
	 * 更改密码 实体类中只需有personalTel和password即可
	 */
	boolean changePassword(PersonalAdmin personalAdmin);

	/**
	 * 更新登录信息
	 */
	boolean updateLoginInfo(String adminTel);

}

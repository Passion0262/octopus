package com.example.octopus.service.personal;

import com.example.octopus.entity.personal.PersonalUser;

import java.util.List;

/**
 * @author: Hao
 * @date: 2021/12/29 9:39
 */

public interface PersonalUserService {
	/**
	 * 返回所有个人用户列表
	 */
	List<PersonalUser> listAllPersonalUser();

	/**
	 * 通过个人用户电话号（账号）获取个人用户信息
	 * @param personalTel 个人用户电话号（账号）
	 * @return 个人用户信息实体类
	 */
	PersonalUser getPersonalUser(long personalTel);

	/**
	 * 更改密码 实体类中只需有personalTel和password即可
	 * @param personalUser 个人用户对象
	 * @return 成功与否
	 */
	boolean changePassword(PersonalUser personalUser);

	/**
	 * 更新登录信息
	 * @param personalTel 个人用户电话号（账号）
	 * @return 成功与否
	 */
	boolean updateLoginInfo(long personalTel);

	/**
	 * 更新个人用户信息
	 * 登录次数、上次与本次登录时间在登录时更新，不在此处更新
	 */
	boolean updatePersonalUser(PersonalUser personalUser);

	/**
	 * 新增个人用户账号
	 */
	boolean insertPersonalUser(PersonalUser personalUser);

}

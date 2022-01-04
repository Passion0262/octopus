package com.example.octopus.service.personal;

import com.example.octopus.entity.personal.PersonalUser;
import com.example.octopus.entity.personal.PersonalUserManageVO;

import java.util.List;

/**
 * @author: Hao
 * @date: 2021/12/29 9:39
 */

public interface PersonalUserService {
	/**
	 * 用户管理界面，返回所有个人用户信息列表。包含手机号码、登录次数、购买套餐详情、最近登录时间
	 */
	List<PersonalUserManageVO> listAllPersonalUserInfo();

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

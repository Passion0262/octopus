package com.example.octopus.dao.personal;

import com.example.octopus.entity.personal.PersonalUser;
import com.example.octopus.entity.personal.PersonalUserManageVO;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Hao
 * @date: 2021/12/29 10:35
 */
@Component
@Mapper
public interface PersonalUserMapper {
	/**
	 * 用户管理界面，返回所有个人用户列表。包含手机号码、登录次数、购买套餐详情、最近登录时间
	 */
	@Select("SELECT pu.*, p.name AS purchased_plans FROM personal_user pu, plan p, personal_plan pp " +
			"WHERE pu.personal_tel=pp.personal_tel AND pp.plan_id=p.id")
	List<PersonalUserManageVO> listAllPersonUser();

	/**
	 * 根据个人用户电话号（账号）获取个人用户信息对象
	 */
	@Select("SELECT * FROM personal_user WHERE personal_tel=#{personalTel}")
	PersonalUser getPersonalUserByTel(long personalTel);

	/**
	 * 更新个人用户登录信息
	 */
	@Update("UPDATE personal_user SET login_number = login_number+1, last_login_time = this_login_time, this_login_time = CURRENT_TIMESTAMP " +
			"WHERE personal_tel = #{personalTel}")
	boolean updateLoginInfoByTel(long personalTel);

	//todo 无tourist列，如有新的个人用户信息内容，再做更改
	/**
	 * 更新除登录信息外的个人用户信息
	 */
	@Update("UPDATE personal_user SET tourist=#{tourist} WHERE personal_tel=#{personalTel}")
	boolean updatePersonalUser(PersonalUser personalUser);

	/**
	 * 新增个人用户信息
	 */
	@Insert("INSERT INTO personal_user (personal_tel, this_login_time) VALUES (#{personalTel}, CURRENT_TIMESTAMP)")
	boolean insertPersonalUser(PersonalUser personalUser);

	/**
	 * 按账号删除个人用户
	 */
	@Delete("DELETE personal_user WHERE personal_tel=#{personalTel}")
	boolean deletePersonalUser(long personalTel);

}

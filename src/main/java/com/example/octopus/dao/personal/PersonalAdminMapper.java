package com.example.octopus.dao.personal;

import com.example.octopus.entity.personal.PersonalAdmin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

/**
 * @author: Hao
 * @date: 2022/1/8 11:42
 */
@Component
@Mapper
public interface PersonalAdminMapper {
	/**
	 * 通过个人用户管理员账号获取个人用户管理员信息
	 */
	@Select("SELECT * FROM personal_admin WHERE admin_tel=#{personalAdminTel}")
	PersonalAdmin getPersonalAdminByTel(String personalAdminTel);

	/**
	 * 更新个人用户管理员登录信息
	 */
	@Update("UPDATE personal_admin SET login_number = login_number+1, last_login_time = this_login_time, this_login_time = CURRENT_TIMESTAMP " +
			"WHERE admin_tel = #{adminTel}")
	boolean updateLoginInfoByTel(String adminTel);
}

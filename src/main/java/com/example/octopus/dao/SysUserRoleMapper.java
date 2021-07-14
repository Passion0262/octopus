package com.example.octopus.dao;

import com.example.octopus.entity.user.SysUserRole;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/29 4:36 下午
 * @modified By：
 */
@Component
@Mapper
public interface SysUserRoleMapper {
	@Select("SELECT * FROM sys_user_role WHERE user_id = #{userId}")
	List<SysUserRole> listByUserId(long userId);

	@Select("SELECT role_id FROM sys_user_role WHERE user_id = #{userId}")
	int getRoleByUserId(long userId);

	@Select("SELECT * FROM sys_user_role WHERE user_id = #{userId}")
	SysUserRole getByUserId(long userId);

	@Insert("INSERT INTO sys_user_role (user_id,role_id,password) VALUES (#{userId},#{roleId},#{password})")
	boolean insert(SysUserRole sysUserRole);

	@Update("UPDATE sys_user_role SET password = #{password} WHERE user_id = #{userId}")
	boolean updatePassword(long userId, String password);

	@Update("UPDATE sys_user_role SET role_id = #{roleId} WHERE user_id = #{userId}")
	boolean updateRoleId(long userId, long roleId);

	@Delete("DELETE FROM sys_user_role WHERE user_id = #{userId}")
	boolean deleteUser(long userId);


	/////////////////批量操作
	@InsertProvider(type = Provider.class, method = "batchInsert")
	boolean batchInsert(List<SysUserRole> sysUserRoles);

	class Provider {
		/* 批量插入 */
		public String batchInsert(Map map) {
			List<SysUserRole> sysUserRoles = (List<SysUserRole>) map.get("list");
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO sys_user_role (user_id,role_id,password) VALUES ");
			MessageFormat mf = new MessageFormat(
					"(#'{'list[{0}].userId},#'{'list[{0}].roleId},#'{'list[{0}].password})"
			);

			for (int i = 0; i < sysUserRoles.size(); i++) {
				sb.append(mf.format(new Object[]{i}));
				if (i < sysUserRoles.size() - 1)
					sb.append(",");
			}
			return sb.toString();
		}

	}
}


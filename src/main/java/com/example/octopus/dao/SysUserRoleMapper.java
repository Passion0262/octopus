package com.example.octopus.dao;

import com.example.octopus.entity.user.SysUserRole;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/29 4:36 下午
 * @modified By：
 */
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

}


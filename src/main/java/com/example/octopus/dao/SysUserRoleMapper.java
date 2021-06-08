package com.example.octopus.dao;

import com.example.octopus.entity.user.SysUserRole;
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
    @Select("SELECT * FROM sys_user_role WHERE stu_number = #{stuNumber}")
    List<SysUserRole> listByUserId(long stuNumber);
}


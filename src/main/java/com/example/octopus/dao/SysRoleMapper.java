package com.example.octopus.dao;

import com.example.octopus.entity.user.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/29 3:43 下午
 * @modified By：
 */
@Mapper
public interface SysRoleMapper {
    @Select("SELECT * FROM sys_role WHERE id = #{id}")
    SysRole getById(long id);

    @Select("SELECT * FROM sys_role WHERE name = #{name}")
    SysRole getByName(String name);
}


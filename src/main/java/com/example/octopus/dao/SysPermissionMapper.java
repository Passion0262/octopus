package com.example.octopus.dao;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/1 4:07 下午
 */
import com.example.octopus.entity.user.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysPermissionMapper {
    @Select("SELECT * FROM sys_permission WHERE role_id=#{roleId}")
    List<SysPermission> listByRoleId(long roleId);
}


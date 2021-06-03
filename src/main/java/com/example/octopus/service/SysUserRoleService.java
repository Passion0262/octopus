package com.example.octopus.service;

import com.example.octopus.entity.user.SysUserRole;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/29 5:03 下午
 * @modified By：
 */
public interface SysUserRoleService {

    public List<SysUserRole> listByUserId(long stuNumber);

}

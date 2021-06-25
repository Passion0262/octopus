package com.example.octopus.service;

import com.example.octopus.entity.user.SysUserRole;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/29 5:03 下午
 * @modified By：
 */
public interface SysUserRoleService {

    /**
     *  查询角色
     */
    List<SysUserRole> listByUserId(long number);


    /**
     * 在sys_user_role表中，通过用户号（包括学生号和教师号）来查找对应的角色权限
     * @param stuNumber  用户号，包括学生号和教师号，非单一的学生号
     * @return 返回角色权限代码
     */
    int getRoleIdByUserId(long stuNumber);

    /**
     *  根据用户账号查询
     */
    SysUserRole getByUserId(long stuNumber);

    /**
     *  添加sysUserRole
     */
    boolean addUserRole(SysUserRole sysUserRole);



}

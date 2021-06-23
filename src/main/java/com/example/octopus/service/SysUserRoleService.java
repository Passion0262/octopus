package com.example.octopus.service;

import com.example.octopus.entity.user.SysUserRole;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/29 5:03 下午
 * @modified By：
 */
public interface SysUserRoleService {

    List<SysUserRole> listByUserId(long number);

    /**
     * 在teacher表中添加完管理员角色后，应同步将其添加到sys_user_role表中
     * 此接口应放在teacher添加接口中，不可单独使用
     * @param teaNumber 管理员号
     * @return 添加成功与否
     */
    boolean addAdmin2Role(long teaNumber);

    /**
     * 在teacher表中添加完教师角色后，应同步将其添加到sys_user_role表中
     * 此接口应放在teacher添加接口中，不可单独使用
     * @param teaNumber 教师号
     * @return 添加成功与否
     */
    boolean addTeacher2Role(long teaNumber);

    /**
     * 在student表中添加完管理员角色后，应同步将其添加到sys_user_role表中
     * 此接口应放在user添加接口中，不可单独使用
     * @param stuNumber 学生号
     * @return 添加成功与否
     */
    boolean addStudent2Role(long stuNumber);



    /**
     * 在sys_user_role表中，通过用户号（包括学生号和教师号）来查找对应的角色权限
     * @param stuNumber  用户号，包括学生号和教师号，非单一的学生号
     * @return 返回角色权限代码
     */
    int getRoleIdByUserId(long stuNumber);

}

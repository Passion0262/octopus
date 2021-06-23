package com.example.octopus.service.impl;

import com.example.octopus.dao.SysUserRoleMapper;
import com.example.octopus.entity.user.SysUserRole;
import com.example.octopus.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/29 5:03 下午
 * @modified By：
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SysUserRole> listByUserId(long stuNumber) {
        return sysUserRoleMapper.listByUserId(stuNumber);
    }

    @Override
    public int getRoleIdByUserId(long stuNumber){
        return sysUserRoleMapper.getRoleByUserId(stuNumber);
    }

    @Override
    public SysUserRole getByUserId(long stuNumber) {
        return sysUserRoleMapper.getByUserId(stuNumber);
    }

    @Override
    public boolean addUserRole(SysUserRole sysUserRole) {
        return sysUserRoleMapper.insert(sysUserRole);
    }
}

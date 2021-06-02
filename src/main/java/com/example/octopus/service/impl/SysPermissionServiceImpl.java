package com.example.octopus.service.impl;

import com.example.octopus.dao.SysPermissionMapper;
import com.example.octopus.entity.user.SysPermission;
import com.example.octopus.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/1 4:09 下午
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionMapper permissionMapper;

    @Override
    public List<SysPermission> listByRoleId(long roleId) {
        return permissionMapper.listByRoleId(roleId);
    }
}

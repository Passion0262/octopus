package com.example.octopus.service;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/1 4:08 下午
 */

import com.example.octopus.dao.SysPermissionMapper;
import com.example.octopus.entity.user.SysPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface SysPermissionService {

    /**
     * 获取指定角色所有权限
     */
    public List<SysPermission> listByRoleId(long roleId);
}


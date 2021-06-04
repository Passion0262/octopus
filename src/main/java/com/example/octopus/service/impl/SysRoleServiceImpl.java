package com.example.octopus.service.impl;

import com.example.octopus.dao.SysRoleMapper;
import com.example.octopus.entity.user.SysRole;
import com.example.octopus.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/29 4:59 下午
 * @modified By：
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Override
    public SysRole findById(long id) {
        return sysRoleMapper.getById(id);
    }

    @Override
    public SysRole findByName(String name) {
        return sysRoleMapper.getByName(name);
    }
}

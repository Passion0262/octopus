package com.example.octopus.dao;

import com.example.octopus.entity.user.SysRole;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/30 3:53 下午
 * @modified By：
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class SysRoleMapperTest {

    @Autowired
    SysRoleMapper sysRoleMapper;

    @Test
    void selectById() {
        SysRole sysRole = sysRoleMapper.selectById(1L);
        assertEquals(sysRole.getName(),"ROLE_ADMIN");
    }
}
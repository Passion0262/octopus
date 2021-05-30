package com.example.octopus.dao;

import com.example.octopus.entity.user.SysUserRole;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/30 3:57 下午
 * @modified By：
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class SysUserRoleMapperTest {

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Test
    void listByUserId() {
        List<SysUserRole> list = sysUserRoleMapper.listByUserId("1");
        assertEquals(list.get(0).getRoleId(),1);
    }
}
package com.example.octopus.service.impl;

import com.example.octopus.dao.SysUserRoleMapper;
import com.example.octopus.dao.TeacherMapper;
import com.example.octopus.dao.UserMapper;
import com.example.octopus.entity.user.SysUserRole;
import com.example.octopus.service.SysUserRoleService;
import org.checkerframework.checker.units.qual.A;
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
	@Autowired
	UserMapper userMapper;
	@Autowired
	TeacherMapper teacherMapper;

	@Override
	public List<SysUserRole> listByUserId(long stuNumber) {
		return sysUserRoleMapper.listByUserId(stuNumber);
	}

	@Override
	public int getRoleIdByUserId(long stuNumber) {
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

	@Override
	public boolean updatePasswordByIdAndPhone(long number, String phone, String password) {
		int role = getRoleIdByUserId(number);
		if (role == 2) {
			if (phone.equals(userMapper.getPhoneByStuNumber(number)))
				return sysUserRoleMapper.updatePassword(number, password);
			else return false;
		}
		else {
            if (phone.equals(teacherMapper.getPhoneByTeaNumber(number)))
                return sysUserRoleMapper.updatePassword(number, password);
            else return false;
        }
	}
}

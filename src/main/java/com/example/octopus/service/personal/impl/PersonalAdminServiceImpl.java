package com.example.octopus.service.personal.impl;

import com.example.octopus.dao.SysUserRoleMapper;
import com.example.octopus.dao.personal.PersonalAdminMapper;
import com.example.octopus.entity.personal.PersonalAdmin;
import com.example.octopus.service.personal.PersonalAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Hao
 * @date: 2022/1/8 11:42
 */
@Service
public class PersonalAdminServiceImpl implements PersonalAdminService {
	@Autowired
	PersonalAdminMapper personalAdminMapper;
	@Autowired
	SysUserRoleMapper sysUserRoleMapper;

	@Override
	public PersonalAdmin getPersonalAdmin(long adminTel){
		return personalAdminMapper.getPersonalAdminByTel(adminTel);
	}

	@Override
	public boolean changePassword(PersonalAdmin personalAdmin){
		return sysUserRoleMapper.updatePassword(personalAdmin.getAdminTel(), personalAdmin.getPassword());
	}

	@Override
	public boolean updateLoginInfo(long adminTel){
		return personalAdminMapper.updateLoginInfoByTel(adminTel);
	}

}

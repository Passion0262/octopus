package com.example.octopus.service.personal.impl;

import com.example.octopus.dao.personal.PersonalUserMapper;
import com.example.octopus.dao.SysUserRoleMapper;
import com.example.octopus.entity.personal.PersonalUser;
import com.example.octopus.entity.user.SysUserRole;
import com.example.octopus.service.personal.PersonalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Hao
 * @date: 2021/12/29 10:34
 */
@Service
public class PersonalUserServiceImpl implements PersonalUserService {

	@Autowired
	PersonalUserMapper personalUserMapper;

	@Autowired
	SysUserRoleMapper sysUserRoleMapper;

	@Override
	public List<PersonalUser> listAllPersonalUser(){
		return personalUserMapper.listAllPersonUser();
	}

	@Override
	public PersonalUser getPersonalUser(long personalTel){
		return personalUserMapper.getPersonalUserByTel(personalTel);
	}

	@Override
	public boolean changePassword(PersonalUser personalUser){
		return sysUserRoleMapper.updatePassword(personalUser.getPersonalTel(), personalUser.getPassword());
	}

	@Override
	public boolean updateLoginInfo(long personalTel){
		return personalUserMapper.updateLoginInfoByTel(personalTel);
	}

	@Override
	public boolean updatePersonalUser(PersonalUser personalUser){
		return personalUserMapper.updatePersonalUser(personalUser);
	}

	@Override
	public boolean insertPersonalUser(PersonalUser personalUser){
		if (personalUser.getPassword() == null)
			personalUser.setPassword("123");
		SysUserRole sysUserRole = new SysUserRole(personalUser.getPersonalTel(), 4, personalUser.getPassword());

		boolean add2PUTable = personalUserMapper.insertPersonalUser(personalUser);
		boolean add2SURTable = false;
		if (add2PUTable) {
			add2SURTable = sysUserRoleMapper.insert(sysUserRole);
			//如成功加入到了personal_user表但因主键问题等没有加入到SUR表，则删除先加在personal_user表中的数据
			if (!add2SURTable) personalUserMapper.deletePersonalUser(personalUser.getPersonalTel());
		}
		return add2PUTable && add2SURTable;
	}

}

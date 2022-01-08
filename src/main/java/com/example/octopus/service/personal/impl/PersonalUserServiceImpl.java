package com.example.octopus.service.personal.impl;

import com.example.octopus.dao.personal.PersonalUserMapper;
import com.example.octopus.dao.SysUserRoleMapper;
import com.example.octopus.entity.personal.PersonalUser;
import com.example.octopus.entity.personal.vo.PersonalUserManageVO;
import com.example.octopus.entity.user.SysUserRole;
import com.example.octopus.service.personal.PersonalUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
	public List<PersonalUserManageVO> listAllPersonalUserInfo() {

		List<PersonalUserManageVO> allPersonalUser = personalUserMapper.listAllPersonalUser();
		List<PersonalUserManageVO> purchasedPersonalUser = personalUserMapper.listAllPersonalUserPurchased();
		if (!allPersonalUser.isEmpty()) {
			for (int i = 0, j = 0; i < allPersonalUser.size(); j++) {
				if (j >= purchasedPersonalUser.size()) break;

				PersonalUserManageVO a = allPersonalUser.get(i);
				PersonalUserManageVO p = purchasedPersonalUser.get(j);
				if (a.getPersonalTel() == p.getPersonalTel()) {
					if (a.getPurchasedPlans() == null) a.setPurchasedPlans("");
					a.setPurchasedPlans(a.getPurchasedPlans().concat(";").concat(p.getPurchasedPlans()));
				} else {
					i++;
					j--;
				}
			}
		}
		return allPersonalUser;
	}

	@Override
	public PersonalUser getPersonalUser(long personalTel) {
		return personalUserMapper.getPersonalUserByTel(personalTel);
	}

	@Override
	public boolean changePassword(PersonalUser personalUser) {
		return sysUserRoleMapper.updatePassword(personalUser.getPersonalTel(), personalUser.getPassword());
	}

	@Override
	public boolean updateLoginInfo(long personalTel) {
		return personalUserMapper.updateLoginInfoByTel(personalTel);
	}

	@Override
	public boolean updatePersonalUser(PersonalUser personalUser) {
		return personalUserMapper.updatePersonalUser(personalUser);
	}

	@Override
	public boolean insertPersonalUser(PersonalUser personalUser) {
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

	@Override
	public int countActivatePersonalUser(){
		List<PersonalUserManageVO> allPersonalUser = personalUserMapper.listAllPersonalUser();
		Timestamp current = new Timestamp(System.currentTimeMillis());  //获取当前时间

		int activateNumber= 0 ;
		long between;
		for(int i=0;i<allPersonalUser.size();i++){
			between = current.getTime()-allPersonalUser.get(i).getThisLoginTime().getTime()/1000;
			if (between/(24*3600)<=7)
				activateNumber++;
		}
		return activateNumber;
	}

	@Override
	public int countAllPersonalUser(){
		return personalUserMapper.listAllPersonalUser().size();
	}
}

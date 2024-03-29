package com.example.octopus.service.personal.impl;

import com.example.octopus.dao.personal.PersonalPlanMapper;
import com.example.octopus.dao.personal.PersonalUserMapper;
import com.example.octopus.entity.personal.PersonalPlan;
import com.example.octopus.service.personal.PersonalPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: Hao
 * @date: 2021/12/29 14:45
 */
@Service
public class PersonalPlanServiceImpl implements PersonalPlanService {
	@Autowired
	PersonalPlanMapper personalPlanMapper;

	@Autowired
	PersonalUserMapper personalUserMapper;

	@Override
	public PersonalPlan getPersonalPlanById(long id){
		return personalPlanMapper.getPersonalPlanById(id);
	}

	@Override
	public List<PersonalPlan> listPersonalPlanByTel(long personalTel){
		Timestamp current = new Timestamp(System.currentTimeMillis());  //获取当前时间
		List<PersonalPlan> res = personalPlanMapper.listAllPersonalPlanByTel(personalTel);
		for (int i=0; i<res.size();i++)
			res.get(i).setUnexpired(res.get(i).getEndTime().getTime() > current.getTime());
		return res;
	}

	@Override
	public List<PersonalPlan> listUnexpiredPersonalPlanByTel(long personalTel){
		Timestamp current = new Timestamp(System.currentTimeMillis());  //获取当前时间
		List<PersonalPlan> tem = personalPlanMapper.listAllPersonalPlanByTel(personalTel);
		List<PersonalPlan> res = new ArrayList<>();;
		for (int i=0;i<tem.size();i++)
			if (tem.get(i).getEndTime().getTime()>current.getTime())
				res.add(tem.get(i));
		return res;
	}

	@Override
	public boolean createPersonalPlan(PersonalPlan personalPlan){
		return personalPlanMapper.insertPersonalPlan(personalPlan);
	}

}

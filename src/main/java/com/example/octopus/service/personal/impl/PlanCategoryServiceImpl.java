package com.example.octopus.service.personal.impl;

import com.example.octopus.dao.personal.PlanCategoryMapper;
import com.example.octopus.entity.personal.Category;
import com.example.octopus.entity.personal.PlanCategory;
import com.example.octopus.service.personal.PlanCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Hao
 * @date: 2022/1/2 17:00
 */

@Service
public class PlanCategoryServiceImpl implements PlanCategoryService {
	@Autowired
	PlanCategoryMapper planCategoryMapper;

	@Override
	public List<PlanCategory> listAllPlanCategory(){
		return planCategoryMapper.listAllPlanCategory();
	}

	@Override
	public List<Category> listPlanCategoryByPlanId(long planId){
		return planCategoryMapper.listCategoryByPlanId(planId);
	}
}

package com.example.octopus.service.personal.impl;

import com.example.octopus.dao.personal.PlanCategoryMapper;
import com.example.octopus.dao.personal.PlanMapper;
import com.example.octopus.entity.personal.Plan;
import com.example.octopus.entity.personal.PlanCategory;
import com.example.octopus.service.personal.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: Hao
 * @date: 2021/12/31 9:53
 */
@Service
public class PlanServiceImpl implements PlanService {
	@Autowired
	PlanMapper planMapper;

	@Autowired
	PlanCategoryMapper planCategoryMapper;

	@Override
	public List<Plan> listAllPlan() {
		List<Plan> tem = planMapper.listAllPlan();
		List<Plan> res = new LinkedList<>();
		if (!tem.isEmpty()) {
			res.add(tem.get(0));
			for (int i = 1, len_res = 0; i < tem.size(); i++) {
				Plan t = tem.get(i);
				Plan r = res.get(len_res);
				if (r.getId() == t.getId())
					r.setCategoryNames(r.getCategoryNames().concat(";").concat(t.getCategoryNames()));
				else {
					len_res++;
					res.add(t);
				}
			}
		}

		return res;
	}

	@Override
	public boolean updateDiscount(Plan plan) {
		return planMapper.updateDiscountById(plan);
	}

	@Override
	public boolean updateSelling(Plan plan){
		return planMapper.updateSellingById(plan);
	}

	@Override
	@Transactional
	public boolean insertPlan(Plan plan) {
		try{
			boolean planInserted = planMapper.insertPlan(plan);
			if (planInserted) {
				List<PlanCategory> planCategories = new ArrayList<>();
				String[] category_ids = plan.getCategoryIds().split(";");
				for (int i = 0; i < category_ids.length; i++) {
					PlanCategory p = new PlanCategory();
					p.setPlanId(planMapper.getPlanByName(plan.getName()).getId());
					p.setCategoryId(Long.parseLong(category_ids[i]));
					planCategories.add(p);
				}
				return planCategoryMapper.batchInsert(planCategories);
			}
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}
		return false;
	}

	@Override
	@Transactional
	public boolean deletePlan(Plan plan) {
		boolean planDeleted = planMapper.deletePlan(plan);
		if (planDeleted) {
			PlanCategory pc = new PlanCategory();
			pc.setPlanId(plan.getId());
			return planCategoryMapper.deletePlanCategoryByPlanId(pc);
		}
		return planDeleted;
	}

}

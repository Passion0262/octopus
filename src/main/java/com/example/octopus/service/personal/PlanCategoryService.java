package com.example.octopus.service.personal;

import com.example.octopus.entity.personal.Category;
import com.example.octopus.entity.personal.PlanCategory;

import java.util.List;

/**
 * 针对plan_category表的直接操作，应只有查询，不可直接对表进行增加、删除、更新操作
 * 要保证套餐下所属类别的不变性，如需改变，就做新套餐
 * @author: Hao
 * @date: 2022/1/2 16:47
 */

public interface PlanCategoryService {
	/**
	 * 显示所有套餐下对应的所有类别
	 */
	List<PlanCategory> listAllPlanCategory();

	/**
	 * 根据套餐id显示其下所拥有类别
	 */
	List<Category> listPlanCategoryByPlanId(long planId);
}

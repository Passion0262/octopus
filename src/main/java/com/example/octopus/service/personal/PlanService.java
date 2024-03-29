package com.example.octopus.service.personal;

import com.example.octopus.entity.personal.Plan;

import java.util.List;

/**
 * @author: Hao
 * @date: 2021/12/31 9:49
 */
public interface PlanService {

	/**
	 * 显示所有套餐，包含套餐下含有的类别名
	 */
	List<Plan> listAllPlan();

	/**
	 * 更新套餐折扣（定价一般不要修改，改折扣力度）
	 *   只需有id和discount属性即可
	 */
	boolean updateDiscount(Plan plan);

	/**
	 * 更新套餐上架/下架
	 *   只需有id和selling属性即可
	 */
	boolean updateSelling(Plan plan);

	/**
	 * 创建新套餐
	 *   categoryIds属性为本套餐所含类别id，之间用英文分号;隔开
	 *   categoryNames属性不需填写
	 */
	boolean insertPlan(Plan plan);

	/**
	 * 删除套餐  非必要切勿直接删除，将导致用户找不到已购但不卖的套餐
	 *   只需要有套餐id即可，同步删除plan_category中对应内容
	 */
	boolean deletePlan(Plan plan);
}

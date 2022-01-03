package com.example.octopus.service.personal;

import com.example.octopus.entity.personal.PersonalPlan;

import java.util.List;

/**
 * @author: Hao
 * @date: 2021/12/29 13:46
 */

public interface PersonalPlanService {
	/**
	 * 通过订单号获取个人所购套餐订单信息
	 * @param id 订单号
	 * @return 订单信息
	 */
	PersonalPlan getPersonalPlanById(long id);

	/**
	 * 列出该个人用户所购 所有 套餐订单
	 * @param personalTel 个人用户手机号（账号）
	 * @return 该用户 所有 套餐列表
	 */
	List<PersonalPlan> listPersonalPlan(long personalTel);

	/**
	 * 列出该个人用户所购 未过期 套餐订单
	 * @param personalTel 个人用户手机号（账号）
	 * @return 该用户 未过期 套餐列表
	 */
	List<PersonalPlan> listUnexpiredPersonalPlan(long personalTel);

	/**
	 * 创建新个人用户购买套餐订单
	 * startTime为当前时间，传入对象中不需要填写
	 * @param personalPlan 新套餐订单信息
	 * @return 成功与否
	 */
	boolean createPersonalPlan(PersonalPlan personalPlan);

}

package com.example.octopus.service.personal;

import com.example.octopus.entity.personal.PersonalPlan;
import com.example.octopus.entity.user.CourseStatic;

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
	List<PersonalPlan> listPersonalPlanByTel(String personalTel);

	/**
	 * 列出该个人用户所购 未过期 套餐订单
	 * @param personalTel 个人用户手机号（账号）
	 * @return 该用户 未过期 套餐列表
	 */
	List<PersonalPlan> listUnexpiredPersonalPlanByTel(String personalTel);

	/**
	 * 创建新个人用户购买套餐订单
	 * startTime为当前时间，传入对象中不需要填写
	 * @param personalPlan 新套餐订单信息
	 * @return 成功与否
	 */
	boolean createPersonalPlan(PersonalPlan personalPlan);

	/**
	 * 根据个人用户账号显示所有未过期课程
	 * @param personalTel 个人用户手机号（账号）
	 * @return 个人用户所有未过期课程
	 */
	List<CourseStatic> listUnexpiredPersonalCourseByTel(String personalTel);



}

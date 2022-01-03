package com.example.octopus.dao.personal;

import com.example.octopus.entity.personal.PersonalPlan;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Hao
 * @date: 2021/12/29 14:54
 */
@Component
@Mapper
public interface PersonalPlanMapper {

	/**
	 * 按订单号获取个人用户套餐订单信息
	 */
	@Select("SELECT * FROM personal_plan WHERE id=#{id}")
	PersonalPlan getPersonalPlanById(long id);

	/**
	 * 获取个人用户所有套餐订单信息，包括套餐名
	 */
	@Select("SELECT pp.*, plan.name AS plan_name FROM personal_plan pp, plan " +
			"WHERE pp.plan_id=plan.id AND personal_tel=#{personal_tel}")
	List<PersonalPlan> listAllPersonalPlanByTel(long personalTel);

	/**
	 * 创建新（完成购买）套餐订单
	 */
	@Insert("INSERT INTO personal_plan (personal_tel, price_id, start_time, end_time, cost) " +
			"VALUES (#{personalTel}, #{priceId}, CURRENT_TIMESTAMP, #{endTime}, #{cost})")
	boolean insertPersonalPlan(PersonalPlan personalPlan);

}

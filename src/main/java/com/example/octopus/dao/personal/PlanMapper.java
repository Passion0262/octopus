package com.example.octopus.dao.personal;

import com.example.octopus.entity.personal.Plan;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Hao
 * @date: 2021/12/30 19:16
 */
@Component
@Mapper
public interface PlanMapper {
	/**
	 * 显示所有套餐（只显示plan表中信息）
	 */
	@Select("SELECT * FROM plan")
	List<Plan> listAllPlan();

	/**
	 * 显示所有套餐，包含套餐所含类别名
	 */
	@Select("SELECT p.*, c.name AS category_names FROM plan p, category c, plan_category pc " +
			"WHERE p.id=pc.plan_id AND pc.category_id=c.id")
	List<Plan> listAllPlanWithCategoryName();

	/**
	 * 显示销售中（未下架）套餐，包含套餐所含类别名
	 */
	@Select("SELECT p.*, c.name AS category_names FROM plan p, category c, plan_category pc " +
			"WHERE p.selling=true AND p.id=pc.plan_id AND pc.category_id=c.id")
	List<Plan> listSellingPlan();

	/**
	 * 通过套餐id查找套餐
	 */
	@Select("SELECT * FROM plan WHERE id=#{id}")
	Plan getPlanById(long planId);

	/**
	 * 通过套餐名查找套餐，套餐名是唯一的
	 *   用于在创建套餐时，将套餐所含类别添加到plan_category中
	 *   因为plan表中id自动生成，所以在plan表完成insert后，需要通过name查找新创建套餐的id
	 */
	@Select("SELECT * FROM plan WHERE name=#{name}")
	Plan getPlanByName(String name);

	/**
	 * 通过套餐id更改折扣
	 */
	@Update("UPDATE plan SET discount=#{discount} WHERE id=#{id}")
	boolean updateDiscountById(Plan plan);

	/**
	 * 通过套餐id更改上架/下架
	 */
	@Update("UPDATE plan SET selling=#{selling} WHERE id=#{id}")
	boolean updateSellingById(Plan plan);

	/**
	 * 创建新套餐
	 *   需注意同步在plan_category表中进行添加
	 */
	@Insert("INSERT INTO plan (name, price, discount, valid_period_month) " +
			"VALUES (#{name}, #{price}, #{discount}, #{validPeriodMonth})")
	boolean insertPlan(Plan plan);

	/**
	 * 非必要切勿直接删除，将导致用户找不到已购但不卖的套餐，建议使用updateSellingById(Plan plan)
	 *   删除套餐 需要注意同步在plan_category表中进行删除
	 */
	@Delete("DELETE FROM plan WHERE id=#{id}")
	boolean deletePlan(Plan plan);
}

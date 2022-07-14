package com.example.octopus.dao.personal;

import com.example.octopus.dao.UserMapper;
import com.example.octopus.entity.personal.Category;
import com.example.octopus.entity.personal.Plan;
import com.example.octopus.entity.personal.PlanCategory;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @author: Hao
 * @date: 2021/12/31 11:03
 */
@Component
@Mapper
public interface PlanCategoryMapper {
	/**
	 * 显示所有套餐-类别条目
	 */
	@Select("SELECT * FROM plan_category")
	List<PlanCategory> listAllPlanCategory();

	/**
	 * 通过套餐id，显示其下类别信息
	 * 不显示类别下拥有的静态课程信息
	 */
	@Select("SELECT c.* FROM plan_category pc, category c " +
			"WHERE pc.plan_id=#{planId} AND pc.category_id=c.id")
	List<Category> listCategoryByPlanId(long planId);

	@Insert("INSERT INTO plan_category (plan_id, category_id) " +
			"VALUES (#{planId}, #{categoryId})")
	boolean insertPlanCategory(PlanCategory planCategory);

	@Delete("DELETE FROM plan_category WHERE plan_id=#{planId}")
	boolean deletePlanCategoryByPlanId(long planId);

	@Delete("DELETE FROM plan_category WHERE category_id=#{categoryId}")
	boolean deletePlanCategoryByCategoryId(long categoryId);


	///////////////////
	//批量插入
	@InsertProvider(type = PlanCategoryMapper.Provider.class, method = "batchInsert")
	boolean batchInsert(List<PlanCategory> planCategories);

	class Provider {
		/* 批量插入 */
		public String batchInsert(Map map) {
			List<PlanCategory> planCategories = (List<PlanCategory>) map.get("list");
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO plan_category (plan_id, category_id) VALUES ");
			MessageFormat mf = new MessageFormat(
					"(#'{'list[{0}].planId},#'{'list[{0}].categoryId})"
			);

			for (int i = 0; i < planCategories.size(); i++) {
				sb.append(mf.format(new Object[] {i}));
				if (i < planCategories.size() - 1)
					sb.append(",");
			}
			return sb.toString();
		}
	}

}

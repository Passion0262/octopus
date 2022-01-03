package com.example.octopus.dao.personal;

import com.example.octopus.entity.personal.Category;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: Hao
 * @date: 2021/12/31 16:38
 */
@Component
@Mapper
public interface CategoryMapper {
	/**
	 * 显示所有类别，及其包含课程名（之间用英文分号;隔开）
	 */
	@Select("SELECT c.*, cs.course_name AS static_course_names FROM category c, category_course cc, course_static cs " +
			"WHERE c.id=cc.category_id AND cc.static_course_id=cs.course_static_id")
	List<Category> listAllCategory();

	/**
	 * 更新套餐信息
	 */
	@Update("UPDATE category SET name=#{name}, brief=#{brief} WHERE id=#{id}")
	boolean updateCategory(Category category);

	/**
	 * 通过类别名查找类别，类别名是唯一的
	 * 用于在创建类别时，将类别所含静态课程添加到category_course中
	 * 因为category表中id自动生成，所以在category表完成insert后，需要通过name查找新创建类别的id
	 */
	@Select("SELECT * FROM category WHERE name=#{name}")
	Category getCategoryByName(String name);

	/**
	 * 创建新类别
	 * 需注意同步在category_course表中进行添加
	 */
	@Insert("INSERT INTO category (name, brief) VALUES (#{name}, #{brief})")
	boolean insertCategory(Category category);

	/**
	 * 创建类别
	 * 需注意同步在category_course表中进行删除
	 */
	@Delete("DELETE FROM category WHERE id=#{id}")
	boolean deleteCategory(Category category);

}

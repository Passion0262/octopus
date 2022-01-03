package com.example.octopus.service.personal;

import com.example.octopus.entity.personal.Category;

import java.util.List;

/**
 * @author: Hao
 * @date: 2021/12/31 16:38
 */

public interface CategoryService {
	/**
	 * 显示所有类别信息，其中类别所包含的课程名之间用英文分号;隔开
	 * @return
	 */
	List<Category> listAllCategory();

	/**
	 * 更新类别信息
	 * 不能更改（改了也没用）类别所含课程！！会造成混乱
	 */
	boolean updateCategory(Category category);

	/**
	 * 创建新类别
	 * staticCourseIds属性为本类别所含静态课程id，之间用英文分号;隔开
	 * staticCourseNames属性不需填写
	 */
	boolean insertCategory(Category category);

	/**
	 * 删除类别
	 * 只要有类别id即可，同步删除category_course中对应条目
	 */
	boolean deleteCategory(Category category);


}

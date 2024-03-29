package com.example.octopus.service.personal.impl;

import com.example.octopus.dao.personal.CategoryCourseMapper;
import com.example.octopus.dao.personal.CategoryMapper;
import com.example.octopus.entity.personal.Category;
import com.example.octopus.entity.personal.CategoryCourse;
import com.example.octopus.service.personal.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: Hao
 * @date: 2021/12/31 16:39
 */
@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryMapper categoryMapper;
	@Autowired
	CategoryCourseMapper categoryCourseMapper;

	@Override
	public List<Category> listAllCategory() {
		List<Category> tem = categoryMapper.listAllCategory();
		List<Category> res = new LinkedList<>();
		if (!tem.isEmpty()) {
			res.add(tem.get(0));
			for (int i = 1, len_res = 0; i < tem.size(); i++) {
				Category t = tem.get(i);
				Category r = res.get(len_res);
				if (r.getId() == t.getId())
					r.setStaticCourseNames(r.getStaticCourseNames().concat(";").concat(t.getStaticCourseNames()));
				else {
					len_res++;
					res.add(t);
				}
			}
		}
		return res;
	}

	@Override
	public boolean updateCategory(Category category) {
		return categoryMapper.updateCategory(category);
	}

	@Override
	@Transactional
	public boolean insertCategory(Category category){
		try{
			boolean categoryInserted = categoryMapper.insertCategory(category);
			if (categoryInserted) {
				List<CategoryCourse> categoryCourses = new ArrayList<>();
				String[] static_course_ids = category.getStaticCourseIds().split(";");
				for (int i = 0; i < static_course_ids.length; i++) {
					CategoryCourse cc = new CategoryCourse();
					cc.setCategoryId(categoryMapper.getCategoryByName(category.getName()).getId());
					cc.setStaticCourseId(Long.parseLong(static_course_ids[i]));
					categoryCourses.add(cc);
				}
				return categoryCourseMapper.batchInsert(categoryCourses);
			}
		}
		catch (Exception e){
			throw new RuntimeException(e);
		}
		return false;
	}

	@Override
	@Transactional
	public boolean deleteCategory(Category category){
		boolean categoryDeleted = categoryMapper.deleteCategory(category);
		if (categoryDeleted) {
			CategoryCourse cc = new CategoryCourse();
			cc.setCategoryId(category.getId());
			return categoryCourseMapper.deleteCategoryCourseByCategoryId(cc);
		}
		return categoryDeleted;
	}
}

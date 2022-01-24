package com.example.octopus.dao.personal;

import com.example.octopus.entity.personal.CategoryCourse;
import com.example.octopus.entity.personal.PlanCategory;
import com.example.octopus.entity.user.CourseStatic;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @author: Hao
 * @date: 2021/12/30 15:08
 */
@Component
@Mapper
public interface CategoryCourseMapper {
	/**
	 * 显示所有课程分类
	 */
	@Select("SELECT * FROM category_course")
	List<CategoryCourse> listAllCategoryCourse();

	/**
	 * 根据课程分类id获取课程分类信息
	 */
	@Select("SELECT * FROM category_course WHERE id=#{id}")
	CategoryCourse getCategoryCourseById(long id);

	/**
	 * 通过类别id获取类别所包含的所有静态课程信息
	 */
	@Select("SELECT cs.* FROM course_static cs, category_course cc " +
			"WHERE cs.course_static_id=cc.static_course_id AND cc.category_id=#{categoryId}")
	List<CourseStatic> listStaticCourseByCategoryId(long categoryId);

	/**
	 * 删除类目时，删除其下的包含课程的所有记录
	 */
	@Delete("DELETE FROM category_course WHERE category_id=#{categoryId}")
	boolean deleteCategoryCourseByCategoryId(long categoryId);

	///////////////////
	//批量插入
	@InsertProvider(type = CategoryCourseMapper.Provider.class, method = "batchInsert")
	boolean batchInsert(List<CategoryCourse> categoryCourses);

	class Provider {
		/* 批量插入 */
		public String batchInsert(Map map) {
			List<CategoryCourse> categoryCourses = (List<CategoryCourse>) map.get("list");
			StringBuilder sb = new StringBuilder();
			sb.append("INSERT INTO category_course (category_id, static_course_id) VALUES ");
			MessageFormat mf = new MessageFormat(
					"(#'{'list[{0}].categoryId},#'{'list[{0}].staticCourseId})"
			);

			for (int i = 0; i < categoryCourses.size(); i++) {
				sb.append(mf.format(new Object[] {i}));
				if (i < categoryCourses.size() - 1)
					sb.append(",");
			}
			return sb.toString();
		}
	}

}

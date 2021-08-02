package com.example.octopus.dao;

import com.example.octopus.entity.user.CourseStatic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: Hao
 * @date: 2021/8/2 9:47
 */
@Mapper
public interface CourseStaticMapper {
	@Select("SELECT cs.*, e.name AS experiment_name FROM course_static cs, experiment e " +
			"WHERE cs.experiment_id=e.id")
	List<CourseStatic> listAllCourseStatic();

	@Select("SELECT cs.*, e.name AS experiment_name FROM course_static cs, experiment e " +
			"WHERE cs.experiment_id=e.id AND cs.course_static_id=#{courseStaticId}")
	CourseStatic getCourseStaticById(long courseStaticId);
}

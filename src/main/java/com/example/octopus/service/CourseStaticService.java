package com.example.octopus.service;

import com.example.octopus.entity.user.CourseStatic;

import java.util.List;

/**
 * @author: Hao
 * @date: 2021/8/2 9:41
 */

public interface CourseStaticService {
	/**
	 * 显示所有静态课程相关信息
	 */
	List<CourseStatic> listAllCourseStatic();

	/**
	 * 通过静态课程号获取静态课程相关信息
	 */
	CourseStatic getCourseStaticById(long courseStaticId);

}

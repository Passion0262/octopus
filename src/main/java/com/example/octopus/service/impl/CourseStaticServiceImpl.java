package com.example.octopus.service.impl;

import com.example.octopus.dao.CourseStaticMapper;
import com.example.octopus.entity.user.CourseStatic;
import com.example.octopus.service.CourseService;
import com.example.octopus.service.CourseStaticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Hao
 * @date: 2021/8/2 9:45
 */

@Service
public class CourseStaticServiceImpl implements CourseStaticService {

	@Autowired
	CourseStaticMapper courseStaticMapper;

	@Override
	public List<CourseStatic> listAllCourseStatic(){
		return courseStaticMapper.listAllCourseStatic();
	}

	public CourseStatic getCourseStaticById(long courseStaticId){
		return courseStaticMapper.getCourseStaticById(courseStaticId);
	}
}

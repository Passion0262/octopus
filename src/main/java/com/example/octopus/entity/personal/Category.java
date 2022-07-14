package com.example.octopus.entity.personal;

import com.example.octopus.entity.user.CourseStatic;
import lombok.Data;

import java.util.List;

/**
 * 课程类别信息实体类（不包含下属课程）
 * @author: Hao
 * @date: 2021/12/30 18:25
 */
@Data
public class Category {
	private long id;  //类别id，主键

	private String name;  //类别名称

	private String brief;  //类别简介

	private String staticCourseIds;  //不在category表中，在创建新类别中使用，静态课程id之间用英文分号;隔开

	private String staticCourseNames;  //不在category表中，在展示类别中使用，名称之间用英文分号;隔开

	private List<CourseStatic> courseStatics;  //不在category表中
}

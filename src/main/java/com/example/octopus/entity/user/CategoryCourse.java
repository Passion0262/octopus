package com.example.octopus.entity.user;

import lombok.Data;

/**
 * 课程类别实体类
 * @author: Hao
 * @date: 2021/12/28 19:48
 */
@Data
public class CategoryCourse {
	private String categoryName;  //类别名称 主键
	private String brief;  //类别简介
	private String staticCourseIds;  //类别所含的静态课程id，之间用英文分号;隔开
}

package com.example.octopus.entity.user;

import lombok.Data;

/**
 * @author: Hao
 * @date: 2021/8/2 9:38
 * 课程静态表实体类
 */

@Data
public class CourseStatic {
	private long courseStaticId;

	private String courseName;

	private String description;

	private String imagePath;

	private long experimentId;

	private String experimentName;  // 不在course_static表中，课程对应的实验名

}

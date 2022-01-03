package com.example.octopus.entity.personal;

import lombok.Data;

/**
 * 类别所含课程实体类  类别:课程 = m:n
 * @author: Hao
 * @date: 2021/12/28 19:48
 */
@Data
public class CategoryCourse {
	private long id;

	private long categoryId;  //类别id

	private long staticCourseId;  //所包含的一项静态课程的id
}

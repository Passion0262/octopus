package com.example.octopus.entity.user;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 个人所购课程实体类
 * @author: Hao
 * @date: 2021/12/28 20:59
 */
@Data
public class PersonalCategory {
	private long id;  //订单号

	private String personalTel;  //个人账号

	private String categoryName;  //类别名

	private Timestamp startTime;  //开始时间

	private Timestamp endTime;  //结束时间
}

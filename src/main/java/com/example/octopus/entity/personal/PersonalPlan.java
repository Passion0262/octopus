package com.example.octopus.entity.personal;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 个人所购套餐实体类
 * @author: Hao
 * @date: 2021/12/28 20:59
 */
@Data
public class PersonalPlan {
	private long id;  //订单号

	private long personalTel;  //个人账号

	private long planId;  //套餐id

	private String planName;  //套餐名，不在personal_plan表内

	private float cost;  //订单实付价格

	private Timestamp startTime;  //有效期开始时间

	private Timestamp endTime;  //有效期结束时间
}

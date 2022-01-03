package com.example.octopus.entity.personal;

import lombok.Data;

/**
 * 套餐所含类别表 套餐:类别=m:n
 * @author: Hao
 * @date: 2021/12/30 18:57
 */
@Data
public class PlanCategory {
	private long id;

	private long planId;  //套餐id

	private long categoryId;  //类别id
}

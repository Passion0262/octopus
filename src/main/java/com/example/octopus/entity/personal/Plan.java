package com.example.octopus.entity.personal;

import lombok.Data;

/**
 * 套餐（价格）实体类
 * @author: Hao
 * @date: 2021/12/31 12:33
 */
@Data
public class Plan {
	private long id;  //套餐id

	private String name;  //套餐名称，在创建套餐时一定要有且唯一，不然因plan表中id自动生成，将无法把所含类别添加到plan_category表中

	private String categoryIds;  //不在plan表内，套餐所含类别id，用于接收新创建套餐所含类别

	private String categoryNames;  //不在plan表内，套餐所含类别名，用于展示套餐中所含类别名

	private boolean selling;  //上架中。尽量不要删除套餐表中条目，否则用户会找不到之前买过的套餐

	private float price;  //价格，两位小数，最大值999999.99

	private float discount;  //折扣，三位小数，0.001-1.000，数据库内默认为1.000

	private int validPeriodYear;  //有效期（年）

	private int validPeriodMonth;  //有效期（月）
}

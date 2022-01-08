package com.example.octopus.entity.personal.vo;

import com.example.octopus.entity.personal.PersonalUser;
import lombok.Data;

/**
 * 用户管理视图类  包含手机号码、登录次数、购买套餐详情、最近登录时间
 * @author: Hao
 * @date: 2022/1/2 16:18
 */
@Data
public class PersonalUserManageVO extends PersonalUser {

	private String purchasedPlans;  //已购买套餐，中间用英文分号;隔开
}

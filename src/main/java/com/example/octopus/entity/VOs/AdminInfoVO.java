package com.example.octopus.entity.VOs;

import lombok.Data;

import java.sql.Date;

/**
 * @author: Hao
 * @date: 2021/7/10 11:22
 */

@Data
public class AdminInfoVO {
	private int sumStudyTime;
	private Date studyDate;
	private Date today;
}

package com.example.octopus.entity.experiment;

import lombok.Data;

import java.sql.Date;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/22 12:40 下午
 * @modified By：
 * 实验任务 experiment表
 */
@Data
public class Experiment {

    private long id;

    private String name;        //实验课程名字

    private String description;     //实验课程描述

    private String imagePath;      //图片url地址

    private Date createTime;        //创建时间


}

package com.example.octopus.entity.project;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/22 1:16 下午
 * @modified By：Hao
 * 项目表
 */
@Data
public class Project {
    private long id;    //项目编号

    private String name;    //项目名称

    private String description;     //项目简介

    private String imagePath;  //项目简图地址

    private String status;  //项目状态

    private String creator; //创建人员

    private Timestamp createTime;  //创建时间

}

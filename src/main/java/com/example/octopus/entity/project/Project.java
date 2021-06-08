package com.example.octopus.entity.project;

import lombok.Data;

import java.sql.Date;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/22 1:16 下午
 * @modified By：Hao
 * 项目表
 */
@Data
public class Project {
    private long id;    //项目编号

//    @Column(nullable = false)
    private String name;    //项目名称

//    @Column(nullable = false)
    private String description;     //项目简介

//    @Column(nullable = false)
    private String imagePath;  //项目简图地址

//    @Column(nullable = false)
    private String status;  //项目状态

//    @Column(nullable = false)
//    private String sortValue; //排序值

    private String creator; //创建人员

    private Date createTime;  //创建时间

}

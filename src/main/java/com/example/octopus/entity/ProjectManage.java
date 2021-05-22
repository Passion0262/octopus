package com.example.octopus.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/22 1:16 下午
 * @modified By：
 */
@Table
@Entity
@Data
public class ProjectManage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;    //项目编号

    private String name;    //项目名称

    private String description;     //项目简介

    private String status;  //项目状态

    private String sortValue; //排序值

    private String creator; //创建人员

    private Date createTime;  //创建时间

    private String pictureUrl; //缩略图


}

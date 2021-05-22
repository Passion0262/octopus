package com.example.octopus.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/22 1:11 下午
 * @modified By：
 */
@Entity
@Table
@Data
public class ExperimentMissionClassify {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;    //编号

    private String title;   //标题

    private String type;    //类型

    private String description;     //介绍

    private int experimentAmount;   //实验数量
}

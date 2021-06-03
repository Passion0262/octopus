package com.example.octopus.entity.experiment;

import lombok.Data;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/22 1:11 下午
 * @modified By：
 * 实验任务分类
 */
@Data
public class ExperimentMissionClassify {
    private long id;    //编号

    private String title;   //标题

    private String type;    //类型

    private String description;     //介绍

    private int experimentAmount;   //实验数量
}

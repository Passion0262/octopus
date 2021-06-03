package com.example.octopus.entity.experiment;

import lombok.Data;

import java.sql.Date;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/22 12:40 下午
 * @modified By：
 * 实验任务
 */
@Data
public class ExperimentMission {
    private long id;

//    @Column(columnDefinition = "VARCHAR(50) default '' COMMENT '实验课程标题'")
    private String title;

//    @Column(columnDefinition = "comment '实验课程描述'")
    private String description;

//    @Column(columnDefinition = "comment '课程章节'")
    private String missionNums;

//    @Column(columnDefinition = "comment '图片url地址'")
    private String pictureUrl;

//    @Column(columnDefinition = "comment '创建时间'")
    private Date createTime;

//    @Column(columnDefinition = "comment '排序值'")
    private int sortValue;

}

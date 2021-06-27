package com.example.octopus.entity;

import lombok.Data;

import java.sql.Time;

/**
 * @author: Hao
 * @date: 2021/6/5 20:51
 * 容器相关内容表
 */
@Data
public class Docker {
    private long dockerId;  //容器id
    private String dockerName;  //容器名
    private Time runningTime;  //容器已开启时间
    private int dockerPort;  //容器端口
    private String dockerStatus;  //容器状态
    private long stuNumber;  //学生号
    private String stuName;  //学生姓名
    private String dockerAddress;  //docker完整ip访问地址

}

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
    private long id;  //容器id
    private String name;  //容器名
    private Time runningTime;  //容器已开启时间
    private int port;  //容器端口
    private String status;  //容器状态
    private long userNumber;  //使用者id
    private String userName;  //使用者名
    private String address;  //docker完整ip访问地址

}

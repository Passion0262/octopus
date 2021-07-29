package com.example.octopus.entity.user;

import lombok.Data;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * @author: Hao
 * @date: 2021/6/5 20:51
 * 容器相关内容表
 */
@Data
public class Docker {
    /**
     * 虚拟机/pod/docker流程：
     *
     *   在刚开始时，数据库中将存放有n个pod的相关信息，它们都是新的且无人使用的
     *
     *   学生进入后，从数据库中挑选可用的（status=true）的pod来分配给该学生，同时在数据库中对这条数据进行update，更新学生号、start_time、docker_status等信息
     *
     *   如果学生进入时，没有空余pod可以提供，则前端需要返回让其等待稍后再重新进入申请pod使用，之后重复此步骤（这里可能需要商榷）
     *
     *   学生退出时，需要将其所使用的的pod重置，即先释放/删除，后新建。在数据库中同样做重置，进行update操作
     */


    private int id;  //此项不要删除，用于填写pod name很方便

    private String name;  //pod名:exp-machine-id

    private String version;  //pod版本名

    private int port;  //pod外部用于访问虚拟机界面的端口，在32000-32999之间，端口号不可重复

    private Timestamp createTime;  //创建时间，应当是创建完成后才对数据库进行插入

    private boolean available;  //pod使用状态，可用与不可用

    private String address;  //完整地址

    private long stuNumber;  //正在使用该pod的学生号

    private String stuName;  //正在使用该pod的学生姓名

    private Timestamp startTime;  //学生开始使用pod的时间

    private String status;  //学生正在此pod/docker进行状态（project，experiment，sleeping）

    private long processingId;  //正在进行的项目或实验id


    public void upgrade(String version){
        this.version=version;
    }

    public void generate(String ip){
        this.name="exp-machine-"+id;
        this.port=32003+id;
        this.address=ip+":"+port+"/vnc.html?password=123456";
    }
}

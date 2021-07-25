package com.example.octopus.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * 课程
 * @author ：shadow
 * @date ：Created in 2021/5/24 2:37 下午
 * @modified By：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    //已设置触发器，在创建对象时不需要设置course_name和tea_name

    private long id;

    private long courseStaticId;  //课程静态ID，用于连接course_static表

    private String courseName;

    private long teaNumber;

    private String teaName;

    private String description;  //不在开课表course中，需要到课程静态表course_static中查

    private String imagePath;  //不在开课表course中，需要到课程静态表course_static中查

    private Date startTime;

    private Date endTime;

    private Date applyTime;

    private int numAllowed;

    private int numParticipated;

    private String status;

}

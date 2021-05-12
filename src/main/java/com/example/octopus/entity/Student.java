package com.example.octopus.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/12 2:26 下午
 * @modified By：
 */
@Entity
@Data
@Table
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long stuNumber;

    private String name;

    private String major;

    private String class_;

    private String phoneNumber;

    private Date regTime;

    private int loginNumber;

    private Date lastLoginTime;

    private Time studyTime;
}

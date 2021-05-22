package com.example.octopus.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/22 11:53 上午
 * @modified By：
 */
@Entity
@Table
@Data
public class Student_Class_ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String className;

    private String stuNumber;

    private String stuName;

    private String stuMajor;

    private String stuClass;

    private String applyTime;

    private String applyStatus;

    private String auditor;

    private String auditTime;
}

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

    private String ClassName;

    private String StuNumber;

    private String StuName;

    private String StuMajor;

    private String StuClass;

    private String ApplyTime;

    private String ApplyStatus;

    private String Auditor;

    private String AuditTime;
}

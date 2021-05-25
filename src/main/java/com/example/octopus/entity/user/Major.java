package com.example.octopus.entity.user;

import lombok.Data;

import javax.persistence.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/22 10:27 上午
 * @modified By：Hao
 * 专业表
 */
@Entity
@Data
@Table
public class Major {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private long id;
    @Column(name = "majorCode")
    private String majorCode;

    @Column(nullable = false, name = "majorName")
    private String majorName;

    @Column(nullable = false)
    private String creator;

    @Column(nullable = false, name = "createTime")
    private String createTime;
}

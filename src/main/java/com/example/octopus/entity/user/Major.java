package com.example.octopus.entity.user;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/22 10:27 上午
 * @modified By：
 */
@Entity
@Data
@Table
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "varchar(20) comment '专业代码'")
    private String majorCode;

    @Column(columnDefinition = "varchar(20) comment '专业名称'")
    private String majorName;

    @Column(columnDefinition = "varchar(20) comment '创建人员'")
    private String creator;

    @Column(columnDefinition = "date comment '创建时间'")
    private Date createTime;
}

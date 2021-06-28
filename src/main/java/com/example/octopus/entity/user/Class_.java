package com.example.octopus.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Set;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/18 3:20 下午
 * @modified By：
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Class_ {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
//    @Column(columnDefinition = "varchar(50) comment '班级名称'")
    private String className;

//    @Column(nullable = false,columnDefinition = "varchar(20) comment '所属专业'")
    private long majorId;

    private String majorName;  //专业名，不在class_表中

    private String school;

//    @Column(nullable = false,columnDefinition = "varchar(20) comment '创建人员'")
    private String creator;

//    @Column(nullable = false,columnDefinition = "date comment '创建时间'")
    private Date createTime;

}

package com.example.octopus.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/18 3:20 下午
 * @modified By：
 */
@Entity
@Data
@Table
public class Class_ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String major;

    private String className;

    private String creator;

    private String createTime;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(name="teacher_student", joinColumns={@JoinColumn(name="s_id")}, inverseJoinColumns={@JoinColumn(name="t_id")})
//    private Set<Student> students;
}

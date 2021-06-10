package com.example.octopus.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/5 12:07 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourse {

    private long id;

    private long stuNumber;

    private long courseId;

    private String courseName;

    private String stuName;

    private String stuMajor;

    private String stuClass;

    private Time applyTime;

}

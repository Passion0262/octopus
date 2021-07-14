package com.example.octopus.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Timestamp;

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

    private long teaNumber;  //教师号，不在student_course表中

    private String teaName;  //教师名，不在student_course表中

    private String stuName;

    private String stuMajor;

    private String stuClass;

    private Timestamp applyTime;

    private boolean completed;  //这门课程是否完成

    public StudentCourse(long id, long stuNumber, long courseId, String courseName, String stuName, String stuMajor, String stuClass, Timestamp applyTime) {
        this.id = id;
        this.stuNumber = stuNumber;
        this.courseId = courseId;
        this.courseName = courseName;
        this.stuName = stuName;
        this.stuMajor = stuMajor;
        this.stuClass = stuClass;
        this.applyTime = applyTime;
    }
}

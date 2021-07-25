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

    private long courseId;  //tea_course_id，是开课表course的id，不是course_static_id

    private String courseName;

    private long teaNumber;  //教师号，不在student_course表中

    private String teaName;  //教师名，不在student_course表中

    private String stuName;

    private String stuMajor;

    private String stuClass;

    private Timestamp applyTime;

    private boolean completed;  //这门课程是否完成

    public StudentCourse(long stuNumber, long courseId) {
        this.stuNumber = stuNumber;
        this.courseId = courseId;
    }
}

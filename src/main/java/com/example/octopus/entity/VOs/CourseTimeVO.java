package com.example.octopus.entity.VOs;

import lombok.Data;

/**
 * @author ：shadow
 * @date ：Created in 2021/7/12 3:47 下午
 */
@Data
public class CourseTimeVO {

    private long teaCourseId;

    private int time;   //单位：秒

    public int getTime() {
        return time;
    }

    public long getTeaCourseId() {
        return teaCourseId;
    }
}

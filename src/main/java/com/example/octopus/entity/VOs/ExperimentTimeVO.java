package com.example.octopus.entity.VOs;

import lombok.Data;

/**
 * @author ：shadow
 * @date ：Created in 2021/7/12 6:17 下午
 */
@Data
public class ExperimentTimeVO {

    private long courseId;

    private int time;   //单位：秒

    public long getCourseId() {
        return courseId;
    }

    public int getTime() {
        return time;
    }
}

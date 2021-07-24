package com.example.octopus.entity.VOs;

import lombok.Data;

/**
 * @author ：shadow
 * @date ：Created in 2021/7/12 6:17 下午
 */
@Data
public class ExperimentTimeVO {

    private long courseId;  //tea_course_id，即开课表course的id，非course_static_id

    private int time;   //单位：秒

}

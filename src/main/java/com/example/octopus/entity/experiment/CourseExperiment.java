package com.example.octopus.entity.experiment;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@Data
public class CourseExperiment {
    private long id;

    private long courseId;

    private long experimentId;

    private Time createTime;

}

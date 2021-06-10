package com.example.octopus.entity.experiment;
import lombok.Data;

import java.sql.Date;

@Data
public class Course_Experiment {
    private long id;

    private long courseId;

    private long experimentId;

    private Date createTime;

}

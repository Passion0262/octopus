package com.example.octopus.entity.experiment;

import lombok.Data;

import java.sql.Date;

@Data
public class SubExperiment {

    private long id;

    private long experimentId;

    private long moduleId;

    private String name;

    private String imagePath;

    private Date expectTime;

    private String requirePath;

}

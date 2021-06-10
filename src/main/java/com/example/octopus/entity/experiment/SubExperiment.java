package com.example.octopus.entity.experiment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubExperiment {

    private long id;

    private long experimentId;

    private long moduleId;

    private String name;

    private int number;

    private String imagePath;

    private Date expectTime;

    private String requirePath;

}

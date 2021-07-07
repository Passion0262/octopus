package com.example.octopus.entity.experiment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubExperiment {

    private long id;

    private long experimentId;

    private long moduleId;

    private String subExperimentName;

    private int number;

    private String imagePath;

    private int expectTime;

    private String requirementPath;

    private String knowledgePath;

    private String templatePath;

    private boolean copyable;

    private Timestamp lastUpdateTime;

}

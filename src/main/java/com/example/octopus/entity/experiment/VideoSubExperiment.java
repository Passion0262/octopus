package com.example.octopus.entity.experiment;
import lombok.Data;

import java.sql.Date;

@Data
public class VideoSubExperiment {
    private long id;

    private long videoId;

    private long subExperimentId;
}

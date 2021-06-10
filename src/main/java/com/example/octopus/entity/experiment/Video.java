package com.example.octopus.entity.experiment;
import lombok.Data;

import java.sql.Date;

@Data
public class Video {

    private long id;

    private String name;

    private int number;

    private String path;

    private long chapterId;

    private long courseId;

}

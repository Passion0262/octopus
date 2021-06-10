package com.example.octopus.entity.experiment;
import lombok.Data;

import java.sql.Date;

@Data
public class Chapter {
    private long id;

    private int number;

    private String name;

    private long courseId;

}

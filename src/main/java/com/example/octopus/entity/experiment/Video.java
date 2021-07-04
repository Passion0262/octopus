package com.example.octopus.entity.experiment;
import lombok.Data;

import java.sql.Date;

@Data
public class Video {
    //course-->chapter-->number

    private long id;

    private String name;

    private int number;

    private String path;

    private long chapterId;

    private long courseId;

    public Video() {
    }



    public Video(long id, String name, int number, String path, long chapterId, long courseId) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.path = path;
        this.chapterId = chapterId;
        this.courseId = courseId;
    }
}

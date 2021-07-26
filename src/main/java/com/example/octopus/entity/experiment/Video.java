package com.example.octopus.entity.experiment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
public class Video {
    //course-->chapter-->number

    public long id;

    public String name;

    public int number;

    public String path;

    public long chapterId;

    public long courseId;

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

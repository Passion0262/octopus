package com.example.octopus.entity.experiment;
import lombok.Data;

import java.sql.Date;

@Data
public class Chapter {
    private long chapterId;

    private int chapterNumber;

    private String chapterName;

    private long courseId;

}

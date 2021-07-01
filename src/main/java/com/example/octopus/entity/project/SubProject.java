package com.example.octopus.entity.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @author ：shadow
 * @date ：Created in 2021/7/1 5:12 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubProject {

    private long id;

    private long projectId;

    private long moduleId;

    private String subProjectName;

    private int number;

    private String imagePath;

    private String requirePath;

}

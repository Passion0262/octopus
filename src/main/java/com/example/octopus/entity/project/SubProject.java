package com.example.octopus.entity.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

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

    private long projectModuleId;

    private String subProjectName;

    private int number;

    private String imagePath;

    private String requirementPath;

    private String knowledgePath;

    private boolean copyable;

    private Timestamp lastUpdateTime;

}

package com.example.octopus.entity.experiment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 子实验文件
 * @author ：shadow
 * @date ：Created in 2021/6/23 8:53 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubExperimentFile {

    private long id;

    private long subExperimentId;

    private String requirementPath;

    private String knowledgePath;

    private String templatePath;

    private Timestamp lastUpdateTime;

    private String creator;

}

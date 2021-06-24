package com.example.octopus.entity.experiment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 子实验报告，用于保存，可多次保存
 * @author ：shadow
 * @date ：Created in 2021/6/24 1:45 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubExperimentReportSave {

    private long id;

    private long subExperimentId;

    private long stuNumber;

    private String content;     //报告内容,最大64kb

    private Timestamp lastUpdateTime;

}

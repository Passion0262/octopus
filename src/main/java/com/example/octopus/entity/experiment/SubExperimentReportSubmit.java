package com.example.octopus.entity.experiment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 子实验报告，用于提交，只可以提交一次
 * @author ：shadow
 * @date ：Created in 2021/6/24 1:54 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubExperimentReportSubmit {

    private long id;

    private long subExperimentId;

    private long stuNumber;

    private String reportPath;  //报告pdf的存储路径

    private Timestamp lastUpdateTime;

}

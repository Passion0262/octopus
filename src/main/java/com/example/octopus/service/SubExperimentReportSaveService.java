package com.example.octopus.service;

import com.example.octopus.entity.experiment.SubExperimentReportSave;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/24 8:24 下午
 */
public interface SubExperimentReportSaveService {

    /**
     * 查询某学生在某子实验上保存的所有报告记录
     */
    List<SubExperimentReportSave> listByStuNumberAndSubExperimentId(long subExperimentId, long stuNumber);

    /**
     * 查询最新保存记录
     */
    SubExperimentReportSave getLatest(long subExperimentId, long stuNumber);

    /**
     *  根据id查询
     */
    SubExperimentReportSave getById(long id);

    /**
     *  新增subExperimentReportSave
     */
    boolean insert(SubExperimentReportSave subExperimentReportSave);

    /**
     *  更新subExperimentReportSave
     */
    boolean update(SubExperimentReportSave subExperimentReportSave);

    /**
     *  删除subExperimentReportSave
     */
    boolean delete(long id);

}

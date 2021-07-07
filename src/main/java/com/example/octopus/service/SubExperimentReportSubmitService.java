package com.example.octopus.service;

import com.example.octopus.entity.experiment.SubExperimentReportSubmit;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/24 8:33 下午
 */
public interface SubExperimentReportSubmitService {

    /**
     * 查询某学生在某子实验上提交的记录
     */
    SubExperimentReportSubmit getByStuNumberAndSubExperimentId(long subExperimentId, long stuNumber);

    /**
     *  根据id查询
     */
    SubExperimentReportSubmit getById(long id);

    /**
     *  新增subExperimentReportSave
     */
    boolean insert(SubExperimentReportSubmit subExperimentReportSubmit);

    /**
     *  学生提交报告 更新
     */
    boolean updateBySubmit(long subExperimentId, long stuNumber, String content);

    /**
     *  教师审核报告 更新
     */
    boolean updateByExamine(long subExperimentId, long stuNumber, long teaNumber, int score);

    /**
     *  删除subExperimentReportSave
     */
    boolean deleteById(long id);

}

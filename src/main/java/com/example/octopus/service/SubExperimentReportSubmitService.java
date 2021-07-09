package com.example.octopus.service;

import com.example.octopus.entity.experiment.SubExperimentReportSubmit;

import java.util.List;

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
     *  返回所有的提交记录
     */
    List<SubExperimentReportSubmit> listAll();

    /**
     *  查询教师教的课的所有提交记录(不是所有已审核的)
     */
    List<SubExperimentReportSubmit> listByTeaNumber(long teaNumber);

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

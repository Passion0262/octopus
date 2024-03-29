package com.example.octopus.service;

import com.example.octopus.entity.vo.experiment.ReportAnalysisVO;
import com.example.octopus.entity.vo.experiment.SubExperimentReportSummaryVO;
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
    SubExperimentReportSubmit getByStuNumberAndSubExperimentId(long subExpId, long stuNumber);

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

//    /**
//     *  学生提交报告 更新
//     */
//    boolean updateBySubmit(long subExperimentId, long stuNumber, String content);

    /**
     *  教师审核报告 更新
     */
    boolean updateByExamine(long subExpId, long stuNumber, long teaNumber, int score);

    /**
     *  删除subExperimentReportSave
     */
    boolean deleteById(long id);

    /**
     * 实验报告管理，已批、未批、未交  通过身份获取
     */
    List<SubExperimentReportSummaryVO> listReportSummaryByRole(long teaNumber);


    /**
     * 实验报告管理-->报告审阅（承接上一接口，在确定子实验后，按班级显示）
     */
    List<SubExperimentReportSummaryVO> listClassReportSummaryByRoleAndSubExpId(long teaNumber, long subExpId);


    /**
     * 实验报告管理-->获取报告分析，按照班级，获取0-60，60-70，70-80，80-90，90-95，95-100各段人数
     */
    List<ReportAnalysisVO> listReportAnalysisByRoleAndSubExpId(long teaNumber, long subExpId);

    /**
     * 实验报告管理-->报告审阅-->获取报告分析，该班级的0-60，60-70，70-80，80-90，90-95，95-100各分数段人数
     */
    ReportAnalysisVO getReportAnalysisByRoleAndSubExpIdAndClassId(long teaNumber, long subExpId, long classId);

//    /**
//     * 获取实验报告成绩（所有已提交的，不论是否经过审核）
//     * 可删
//     */
//    List<SubExperimentReportSubmit> listReportScoreByRoleAndSubExpId(long teaNumber, long subExpId);

    /**
     * 实验报告管理-->报告审阅-->班级内学生报告列表，获取报告的相关信息
     * todo 需要注意管理员只能查看，只有对应的老师才能进行批改
     */
    List<SubExperimentReportSubmit> listClassReportByRoleAndSubExpIdAndClassId(long teaNumber, long subExpId, long classId);

    /**
     * 实验报告管理-->报告审阅-->班级内学生报告列表-->审阅学生报告，获取该子实验该班级的下一个学生的报告
     * 注意这里的teaNumber是授课教师号，而非后台的登录账号，管理员无法批阅非自己教的学生
     */
    SubExperimentReportSubmit getNextReportByTeaIdAndSubExpIdAndClassId(long reportId, long teaNumber, long subExpId, long classId);

}

package com.example.octopus.service;

import com.example.octopus.entity.experiment.SubExperimentReportSubmit;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
    @Update("UPDATE sub_experiment_report_submit SET content=#{content},submit_time=CURRENT_TIMESTAMP WHERE sub_experiment_id = #{subExperimentId} AND stu_number = #{stuNumber}")
    boolean updateBySubmit(long subExperimentId, long stuNumber, String content);

    /**
     *  教师审核报告 更新
     */
    @Update("UPDATE sub_experiment_report_submit SET score=#{score}, tea_number=#{teaNumber}, examined=1, examined_time=CURRENT_TIMESTAMP WHERE sub_experiment_id=#{subExperimentId} AND stu_number=#{stuNumber}")
    boolean updateByExamine(long subExperimentId, long stuNumber, long teaNumber, int score);

    /**
     *  删除subExperimentReportSave
     */
    boolean deleteById(long id);

}

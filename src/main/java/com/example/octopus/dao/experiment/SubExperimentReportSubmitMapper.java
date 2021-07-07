package com.example.octopus.dao.experiment;

import com.example.octopus.entity.experiment.SubExperimentReportSubmit;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/24 8:27 下午
 */
@Mapper
public interface SubExperimentReportSubmitMapper {

    /**
     * 查询某学生在某子实验上提交的记录
     */
    @Select("SELECT * FROM sub_experiment_report_submit WHERE sub_experiment_id = #{subExperimentId} AND stu_number = #{stuNumber}")
    SubExperimentReportSubmit getByStuNumberAndSubExperimentId(long subExperimentId, long stuNumber);

    /**
     *  根据id查询
     */
    @Select("SELECT * FROM sub_experiment_report_submit WHERE id = #{id}")
    SubExperimentReportSubmit getById(long id);

    /**
     *  新增subExperimentReportSave
     */
    @Insert("INSERT INTO sub_experiment_report_submit (sub_experiment_id, stu_number, content, report_path, submit_time) VALUES (#{subExperimentId},#{stuNumber},#{content},#{reportPath},CURRENT_TIMESTAMP)")
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
    @Delete("DELETE FROM sub_experiment_report_submit WHERE id = #{id}")
    boolean deleteById(long id);

}

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
    @Insert("INSERT INTO sub_experiment_report_submit (sub_experiment_id, stu_number, report_path, last_update_time) VALUES (#{subExperimentId},#{stuNumber},#{reportPath},CURRENT_TIMESTAMP)")
    boolean insert(SubExperimentReportSubmit subExperimentReportSubmit);

    /**
     *  更新subExperimentReportSave
     */
    @Update("UPDATE sub_experiment_report_save SET sub_experiment_id=#{subExperimentId},stu_number=#{stuNumber},report_path=#{reportPath},last_update_time=CURRENT_TIMESTAMP WHERE id = #{id}")
    boolean update(SubExperimentReportSubmit subExperimentReportSubmit);

    /**
     *  删除subExperimentReportSave
     */
    @Delete("DELETE FROM sub_experiment_report_submit WHERE id = #{id}")
    boolean deleteById(long id);

}

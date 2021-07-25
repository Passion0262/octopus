package com.example.octopus.dao.experiment;

import com.example.octopus.entity.experiment.SubExperimentReportSave;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/24 3:30 下午
 */
@Mapper
public interface SubExperimentReportSaveMapper {

    /**
     * 查询某学生在某子实验上保存的所有报告记录
     */
    @Select("SELECT * FROM sub_experiment_report_save WHERE sub_experiment_id = #{subExperimentId} AND stu_number = #{stuNumber}")
    List<SubExperimentReportSave> listByStuNumberAndSubExperimentId(long subExperimentId, long stuNumber);

    /**
     * 查询最新保存记录
     */
    @Select("SELECT * FROM sub_experiment_report_save WHERE sub_experiment_id = #{subExperimentId} AND stu_number = #{stuNumber} AND last_update_time =" +
            "(SELECT MAX(last_update_time) FROM sub_experiment_report_save WHERE sub_experiment_id = #{subExperimentId} AND stu_number = #{stuNumber})")
    SubExperimentReportSave getLatest(long subExperimentId, long stuNumber);

    /**
     *  根据id查询
     */
    @Select("SELECT * FROM sub_experiment_report_save WHERE id = #{id}")
    SubExperimentReportSave getById(long id);

    /**
     *  新增subExperimentReportSave
     *  reportSave表中的开课id即tea_course_id，使用before insert触发器自动查询并填写，不需要手动插入
     */
    @Insert("INSERT INTO sub_experiment_report_save (sub_experiment_id, stu_number, content, last_update_time) VALUES (#{subExperimentId},#{stuNumber},#{content},CURRENT_TIMESTAMP)")
    boolean insert(SubExperimentReportSave subExperimentReportSave);

    /**
     *  （不推荐使用）更新subExperimentReportSave
     */
    @Update("UPDATE sub_experiment_report_save SET sub_experiment_id=#{subExperimentId},stu_number=#{stuNumber},content=#{content},last_update_time=CURRENT_TIMESTAMP WHERE id = #{id}")
    boolean update(SubExperimentReportSave subExperimentReportSave);

    /**
     *  删除subExperimentReportSave
     */
    @Delete("DELETE FROM sub_experiment_report_save WHERE id = #{id}")
    boolean deleteById(long id);

}

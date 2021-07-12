package com.example.octopus.dao.experiment;

import com.example.octopus.entity.experiment.SubExperimentReportSubmit;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/24 8:27 下午
 */
@Mapper
public interface SubExperimentReportSubmitMapper{

    /**
     *  返回所有的提交记录
     */
    @Select("SELECT sers.*,se.sub_experiment_name,s.name as stu_name,t.tea_name FROM sub_experiment_report_submit sers, sub_experiment se, student s, teacher t " +
            "WHERE sers.sub_experiment_id = se.id AND sers.stu_number = s.stu_number AND sers.tea_number = t.tea_number")
    List<SubExperimentReportSubmit> listAll();

    /**
     *  查询某子实验下所有学生的提交记录
     */
    @Select("SELECT sers.*,se.sub_experiment_name,s.name as stu_name,t.tea_name FROM sub_experiment_report_submit sers, sub_experiment se, student s, teacher t " +
            "WHERE sers.sub_experiment_id = #{subExperimentId} AND sers.sub_experiment_id = se.id AND sers.stu_number = s.stu_number AND sers.tea_number = t.tea_number")
    List<SubExperimentReportSubmit> listBySubExperimentId(long subExperimentId);

    /**
     *  查询教师教的课的所有提交记录
     */
    @Select("SELECT sers.*,se.sub_experiment_name,s.name as stu_name,t.tea_name FROM sub_experiment_report_submit sers, sub_experiment se, student s, teacher t " +
            "WHERE sers.tea_number = #{teaNumber} AND sers.sub_experiment_id = se.id AND sers.stu_number = s.stu_number AND sers.tea_number = t.tea_number")
    List<SubExperimentReportSubmit> listByTeaNumber(long teaNumber);

    /**
     * 查询某学生在某子实验上提交的记录
     */
    @Select("SELECT sers.*,se.sub_experiment_name,s.name as stu_name,t.tea_name FROM sub_experiment_report_submit sers, sub_experiment se, student s, teacher t " +
            "WHERE sers.sub_experiment_id = #{subExperimentId} AND sers.stu_number = #{stuNumber} AND sers.sub_experiment_id = se.id AND sers.stu_number = s.stu_number AND sers.tea_number = t.tea_number")
    SubExperimentReportSubmit getByStuNumberAndSubExperimentId(long subExperimentId, long stuNumber);

    /**
     *  根据id查询
     */
    @Select("SELECT sers.*,se.sub_experiment_name,s.name as stu_name,t.tea_name FROM sub_experiment_report_submit sers, sub_experiment se, student s, teacher t " +
            "WHERE sers.id = #{id} AND sers.sub_experiment_id = se.id AND sers.stu_number = s.stu_number AND sers.tea_number = t.tea_number")
    SubExperimentReportSubmit getById(long id);

    /**
     *  新增subExperimentReportSave
     */
    @Insert("INSERT INTO sub_experiment_report_submit (sub_experiment_id, stu_number, content, submit_time,tea_number) VALUES (#{subExperimentId},#{stuNumber},#{content},CURRENT_TIMESTAMP," +
            "(SELECT c.tea_number FROM sub_experiment se, course_experiment ce, course c WHERE se.id=#{subExperimentId} AND se.experiment_id = ce.experiment_id AND ce.course_id = c.id ))")
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

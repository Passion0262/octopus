package com.example.octopus.dao.experiment;

import com.example.octopus.entity.VOs.ExperimentTimeHistoryVO;
import com.example.octopus.entity.VOs.ExperimentTimeVO;
import com.example.octopus.entity.experiment.Experiment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/2 4:13 下午
 */
@Mapper
public interface ExperimentMapper {

    /**
     * 查询所有实验任务并返回
     * @return 实验任务list
     */
    @Select("SELECT * FROM experiment")
    List<Experiment> listExperiments();

    /**
     * 根据实验任务id查找实验任务
     * @param id 实验任务id
     * @return 实验任务实体
     */
    @Select("SELECT * FROM experiment WHERE id = #{id}")
    Experiment getExperimentById(long id);

    /**
     *  根据学生id,返回每门课的实验学习时长
     * @return
     */
    @Select("SELECT course_id,count(sep.valid_study_time) as time FROM sub_experiment_progress sep,sub_experiment se,course_experiment ce WHERE stu_number=#{stuNumber} AND sep.sub_experiment_id=se.id AND se.experiment_id=ce.course_id")
    List<ExperimentTimeVO> countExperimentTimeByStuNumberGroupByCourseId(long stuNumber);

    /**
     * 获取学生过去7天的实验时间
     */
    @Select("SELECT DATE_FORMAT( end_time, '%Y-%m-%d' ) date, SUM(valid_study_time) time " +
            "FROM ( SELECT * FROM sub_experiment_progress WHERE DATE_SUB( CURDATE( ), INTERVAL 7 DAY ) <= date(end_time)) as day " +
            "GROUP BY date")
    List<ExperimentTimeHistoryVO> getHistory7DaysExperimentTime(long stuNumber);

}

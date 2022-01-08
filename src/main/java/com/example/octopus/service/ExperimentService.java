package com.example.octopus.service;

import com.example.octopus.entity.vo.experiment.ExperimentTimeHistoryVO;
import com.example.octopus.entity.vo.experiment.ExperimentTimeVO;
import com.example.octopus.entity.experiment.Experiment;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/2 4:11 下午
 */
public interface ExperimentService {

    /**
     * 查找所有的实验任务
     * @return 实验任务list
     */
    List<Experiment> listExperiments();

    /**
     * 根据实验任务id查找实验任务
     * @param id 实验任务id
     * @return experiment实体
     */
    Experiment getExperimentById(long id);

    /**
     * 根据课程id查找实验任务
     * @param courseId 课程id
     * @return experiment实体
     */
    Experiment getExperimentByCourseId(long courseId);

    /**
     * 根据学生学号查询所有该学生选的课对应的实验任务
     * @param stuNumber 学生学号
     * @return experiment list
     */
    List<Experiment> listExperimentsByStuNumber(long stuNumber);

    /**
     *  根据学生ID返回每门课的实验学习时长
     */
    List<ExperimentTimeVO> countExperimentTime(long stuNumber);

    /**
     * 获取学生过去7天的实验时间
     */
    List<ExperimentTimeHistoryVO> getHistory7DaysExperimentTime(long stuNumber);


}

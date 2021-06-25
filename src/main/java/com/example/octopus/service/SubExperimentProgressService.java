package com.example.octopus.service;

import com.example.octopus.entity.experiment.SubExperimentProgress;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/24 3:19 下午
 */
public interface SubExperimentProgressService {

    /**
     *  根据ID查询
     */
    SubExperimentProgress getById(long id);

    /**
     *  根据子实验id查询
     */
    List<SubExperimentProgress> listBySubExperimentId(long subExperimentId);

    /**
     *  根据学生id查询，返回这个学生所有的学习记录
     */
    List<SubExperimentProgress> listByStuNumber(long stuNumber);

    /**
     *  根据学生id和子实验id查询，返回这个学生有关这个子实验所有的学习记录
     */
    List<SubExperimentProgress> listByStuNumberAndSubExperimentId(long stuNumber, long subExperimentId);

    /**
     * 计算该学生总计有效学习时间
     * @param stuNumber 学生id
     */
    int countValidStudyTime(long stuNumber);

    /**
     * 计算该学生在某子实验上总计有效学习时间
     * @param stuNumber 学生id
     * @param subExperimentId 子实验id
     */
    int countValidStudyTime(long stuNumber, long subExperimentId);

    /**
     *  新增子实验学习时间记录
     */
    boolean insert(SubExperimentProgress subExperimentProgress);

    /**
     *  更新子实验学习时间记录
     */
    boolean update(SubExperimentProgress subExperimentProgress);

    /**
     *  删除子实验学习时间记录
     */
    boolean delete(long id);

}
package com.example.octopus.dao;

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

}

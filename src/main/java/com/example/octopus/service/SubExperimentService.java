package com.example.octopus.service;

import com.example.octopus.entity.experiment.SubExperiment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/10 4:15 下午
 */
public interface SubExperimentService {

    /**
     * 根据实验id获取所有的子实验id
     */
    List<Long> listIdsByExperimentId(long experimentId);

    /**
     * 根据实验id获取所有的子实验
     */
    List<SubExperiment> listByExperimentId(long experimentId);

    /**
     * 根据 moduleId 查询子实验
     * @param moduleId 模块id
     * @return subExperiment list
     */
    List<SubExperiment> listSubExperimentsByModuleId(long moduleId) ;

    /**
     * 根据子实验id查询子实验
     * @param id 子实验id
     * @return 子实验实体
     */
    SubExperiment getById(long id);

    /**
     * 根据实验id查询子实验数量
     * @param experimentId 实验id
     * @return 子实验数量
     */
    int getSubExperimentNumsByExperimentId(long experimentId);

    /**
     * 根据video查找对应的experimentId
     */
    Long getSubExperimentIdByVideoId(long videoId);

    /**
     * 根据video查找对应的experiment
     */
    SubExperiment getSubExperimentByVideoId(long videoId);

    /**
     *  新增sub_experiment
     */
    boolean insert(SubExperiment subExperiment);

    /**
     *  删除sub_experiment
     */
    boolean deleteById(long id);

}

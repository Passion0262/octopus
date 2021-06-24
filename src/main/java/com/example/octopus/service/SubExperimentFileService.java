package com.example.octopus.service;

import com.example.octopus.entity.experiment.SubExperimentFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/24 2:49 下午
 */
public interface SubExperimentFileService {

    /**
     *  根据id查询
     */
    SubExperimentFile getById(long id);

    /**
     *  根据子实验id查询
     */
    SubExperimentFile getBySubExperimentId(long subExperimentId);

    /**
     *  新增subExperimentFile
     */
    boolean insert(SubExperimentFile subExperimentFile);

    /**
     *  更新subExperimentFile
     */
    boolean updateById(SubExperimentFile subExperimentFile);

}

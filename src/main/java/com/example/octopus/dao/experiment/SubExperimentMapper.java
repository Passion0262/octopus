package com.example.octopus.dao.experiment;

import com.example.octopus.entity.experiment.SubExperiment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/10 3:17 下午
 */
@Mapper
public interface SubExperimentMapper {

    /**
     * 根据 moduleId 查询子实验
     * @param moduleId 模块id
     * @return subExperiment list
     */
    @Select(("SELECT * FROM sub_experiment WHERE module_id = #{moduleId} order by number"))
    List<SubExperiment> listSubExperimentsByModuleId(long moduleId) ;

    /**
     * 根据子实验id查询子实验
     * @param id 子实验id
     * @return 子实验实体
     */
    @Select("SELECT * FROM sub_experiment WHERE id = #{id}")
    SubExperiment getById(long id);
}

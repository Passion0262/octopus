package com.example.octopus.dao.experiment;

import com.example.octopus.entity.experiment.Module;
import com.example.octopus.entity.experiment.SubExperiment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/10 3:16 下午
 */
@Mapper
public interface ModuleMapper {

    /**
     * 根据experimentId查询模块
     * @param experimentId 实验id
     * @return module list
     */
    @Select(("SELECT * FROM module where experiment_id = #{experimentId} order by module_number" ))
    List<Module> listModulesByExperimentId(long experimentId) ;

}

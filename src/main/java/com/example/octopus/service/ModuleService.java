package com.example.octopus.service;

import com.example.octopus.entity.experiment.Module;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/10 4:18 下午
 */
public interface ModuleService {

    /**
     * 根据experimentId查询模块
     * @param experimentId 实验id
     * @return module list
     */
    List<Module> listModulesByExperimentId(long experimentId) ;

}

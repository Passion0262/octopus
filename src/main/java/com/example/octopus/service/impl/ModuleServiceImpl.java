package com.example.octopus.service.impl;

import com.example.octopus.dao.experiment.ModuleMapper;
import com.example.octopus.entity.experiment.Module;
import com.example.octopus.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/10 4:18 下午
 */
@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    ModuleMapper moduleMapper;

    @Override
    public List<Module> listModulesByExperimentId(long experimentId) {
        return moduleMapper.listModulesByExperimentId(experimentId);
    }
}

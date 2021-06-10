package com.example.octopus.service.impl;

import com.example.octopus.dao.experiment.SubExperimentMapper;
import com.example.octopus.entity.experiment.SubExperiment;
import com.example.octopus.service.SubExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/10 4:16 下午
 */
@Service
public class SubExperimentServiceImpl implements SubExperimentService {

    @Autowired
    SubExperimentMapper subExperimentMapper;

    @Override
    public List<SubExperiment> listSubExperimentsByModuleId(long moduleId) {
        return subExperimentMapper.listSubExperimentsByModuleId(moduleId);
    }

    @Override
    public SubExperiment getById(long id) {
        return subExperimentMapper.getById(id);
    }

    @Override
    public int getSubExperimentNumsByExperimentId(long experimentId) {
        return subExperimentMapper.getSubExperimentNumsByExperimentId(experimentId);
    }
}

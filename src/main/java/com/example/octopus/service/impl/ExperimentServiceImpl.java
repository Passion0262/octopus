package com.example.octopus.service.impl;

import com.example.octopus.dao.ExperimentMapper;
import com.example.octopus.entity.experiment.ExperimentMission;
import com.example.octopus.service.ExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/2 4:12 下午
 */
@Service
public class ExperimentServiceImpl implements ExperimentService {

    @Autowired
    ExperimentMapper experimentMapper;

    @Override
    public List<ExperimentMission> findAllExperimentMission() {
        return experimentMapper.queryAllExperimentMission();
    }
}

package com.example.octopus.service;

import com.example.octopus.entity.experiment.Experiment;
import com.example.octopus.entity.experiment.ExperimentMission;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/2 4:11 下午
 */
public interface ExperimentService {

    List<ExperimentMission> findAllExperimentMission();

}

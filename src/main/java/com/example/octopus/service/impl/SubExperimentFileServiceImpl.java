package com.example.octopus.service.impl;

import com.example.octopus.dao.experiment.SubExperimentFileMapper;
import com.example.octopus.entity.experiment.SubExperimentFile;
import com.example.octopus.service.SubExperimentFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/24 2:50 下午
 */
@Service
public class SubExperimentFileServiceImpl implements SubExperimentFileService {

    @Autowired
    SubExperimentFileMapper subExperimentFileMapper;

    @Override
    public SubExperimentFile getById(long id) {
        return subExperimentFileMapper.getById(id);
    }

    @Override
    public SubExperimentFile getBySubExperimentId(long subExperimentId) {
        return subExperimentFileMapper.getBySubExperimentId(subExperimentId);
    }

    @Override
    public boolean insert(SubExperimentFile subExperimentFile) {
        return subExperimentFileMapper.insert(subExperimentFile);
    }

    @Override
    public boolean updateById(SubExperimentFile subExperimentFile) {
        return subExperimentFileMapper.updateById(subExperimentFile);
    }
}

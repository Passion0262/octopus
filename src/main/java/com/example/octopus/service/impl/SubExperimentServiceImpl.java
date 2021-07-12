package com.example.octopus.service.impl;

import com.example.octopus.dao.SysUserRoleMapper;
import com.example.octopus.dao.experiment.SubExperimentMapper;
import com.example.octopus.dao.experiment.VideoSubExperimentMapper;
import com.example.octopus.entity.VOs.SubExperimentDetailVO;
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

    @Autowired
    VideoSubExperimentMapper videoSubExperimentMapper;

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Override
    public List<SubExperimentDetailVO> listSubExperimentByRole(long teaNumber){
        long role = sysUserRoleMapper.getRoleByUserId(teaNumber);
        if (role == 1) {
            //管理员获取全部
            return subExperimentMapper.listAllSubExperimentDetail();
        } else return subExperimentMapper.listSubExperimentDetailByTeaId(teaNumber);
    }

    @Override
    public List<Long> listIdsByExperimentId(long experimentId) {
        return subExperimentMapper.listIdsByExperimentId(experimentId);
    }

    @Override
    public List<SubExperiment> listByExperimentId(long experimentId) {
        return subExperimentMapper.listByExperimentId(experimentId);
    }

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

    @Override
    public Long getSubExperimentIdByVideoId(long videoId) {
        return videoSubExperimentMapper.getSubExperimentIdByVideoId(videoId);
    }

    @Override
    public SubExperiment getSubExperimentByVideoId(long videoId) {
//        long subExperimentId = videoSubExperimentMapper.getSubExperimentIdByVideoId(videoId);
        return getById(getSubExperimentIdByVideoId(videoId));
    }

    @Override
    public boolean insert(SubExperiment subExperiment) {
        return subExperimentMapper.insert(subExperiment);
    }

    @Override
    public boolean deleteById(long id) {
        return subExperimentMapper.deleteById(id);
    }
}

package com.example.octopus.service.impl;

import com.example.octopus.dao.SysUserRoleMapper;
import com.example.octopus.dao.experiment.SubExperimentProgressMapper;
import com.example.octopus.entity.VOs.SubExperimentOperateTimeVO;
import com.example.octopus.entity.experiment.SubExperimentProgress;
import com.example.octopus.service.SubExperimentProgressService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/24 3:21 下午
 */
@Service
public class SubExperimentProgressServiceImpl implements SubExperimentProgressService {

    @Autowired
    SubExperimentProgressMapper subExperimentProgressMapper;
    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Override
    public SubExperimentProgress getById(long id) {
        return subExperimentProgressMapper.getById(id);
    }

    @Override
    public List<SubExperimentProgress> listBySubExperimentId(long subExperimentId) {
        return subExperimentProgressMapper.listBySubExperimentId(subExperimentId);
    }

    @Override
    public List<SubExperimentProgress> listByStuNumber(long stuNumber) {
        return subExperimentProgressMapper.listByStuNumber(stuNumber);
    }

    @Override
    public List<SubExperimentProgress> listByStuNumberAndSubExperimentId(long stuNumber, long subExperimentId) {
        return subExperimentProgressMapper.listByStuNumberAndSubExperimentId(stuNumber, subExperimentId);
    }

    @Override
    public int countValidStudyTime(long stuNumber) {
        return subExperimentProgressMapper.countValidStudyTimeByStuNumber(stuNumber);
    }

    @Override
    public int countValidStudyTime(long stuNumber, long subExperimentId) {
        return subExperimentProgressMapper.countValidStudyTimeByStuNumberAndSubExpId(stuNumber, subExperimentId);
    }

    @Override
    public boolean insert(long stuNumber, long subExperimentId) {
        SubExperimentProgress subExperimentProgress = new SubExperimentProgress(1,subExperimentId,stuNumber,null,null,0);
        return subExperimentProgressMapper.insert(subExperimentProgress);
    }

    @Override
    public boolean insert(SubExperimentProgress subExperimentProgress) {
        return subExperimentProgressMapper.insert(subExperimentProgress);
    }

    @Override
    public boolean update(long stuNumber, long subExperimentId) {
        return subExperimentProgressMapper.updateByStuNumberAndId(stuNumber,subExperimentId);
    }

    @Override
    public boolean update(SubExperimentProgress subExperimentProgress) {
        return subExperimentProgressMapper.update(subExperimentProgress);
    }

    @Override
    public boolean delete(long id) {
        return subExperimentProgressMapper.delete(id);
    }

    @Override
    public List<SubExperimentOperateTimeVO> getOperateTimeByRole(long teaNumber){
        long role = sysUserRoleMapper.getRoleByUserId(teaNumber);
        if (role == 1) {
            return subExperimentProgressMapper.getAllOperateTime();
        } else return subExperimentProgressMapper.getOperateTimeByTeacherId(teaNumber);
    }
}

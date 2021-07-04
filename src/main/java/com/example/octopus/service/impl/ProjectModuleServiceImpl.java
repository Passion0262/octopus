package com.example.octopus.service.impl;

import com.example.octopus.dao.project.ProjectModuleMapper;
import com.example.octopus.entity.project.ProjectModule;
import com.example.octopus.service.ProjectModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/7/4 2:01 下午
 */
@Service
public class ProjectModuleServiceImpl implements ProjectModuleService {

    @Autowired
    ProjectModuleMapper projectModuleMapper;

    @Override
    public List<ProjectModule> listByProjectId(long projectId) {
        return projectModuleMapper.listByProjectId(projectId);
    }

    @Override
    public ProjectModule getById(long id) {
        return projectModuleMapper.getById(id);
    }

    @Override
    public ProjectModule getByNumberAndName(int moduleNumber, String moduleName) {
        return projectModuleMapper.getByNumberAndName(moduleNumber,moduleName);
    }

    @Override
    public boolean updateModuleNameById(String moduleName, long id) {
        return projectModuleMapper.updateModuleNameById(moduleName,id);
    }
}

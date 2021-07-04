package com.example.octopus.service.impl;

import com.example.octopus.dao.project.ProjectMapper;
import com.example.octopus.entity.project.Project;
import com.example.octopus.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/2 4:46 下午
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectMapper projectMapper;

    @Override
    public List<Project> listProjects() {
        return projectMapper.listProjects();
    }

    @Override
    public Project getProjectById(long id) {
        return projectMapper.getProject(id);
    }
}

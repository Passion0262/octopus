package com.example.octopus.service;

import com.example.octopus.entity.project.Project;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/2 4:45 下午
 */
public interface ProjectService {

    /**
     * 返回所有的项目实战
     * @return  项目list
     */
    List<Project> listProjects();

    /**
     * 根据项目id查找项目
     * @param id 项目id
     * @return 项目实体
     */
    Project getProjectById(long id);

}

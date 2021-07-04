package com.example.octopus.service;

import com.example.octopus.entity.project.SubProject;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/7/4 2:17 下午
 */
public interface SubProjectService {

    /**
     * 根据 projectModuleId 查询子项目
     */
    List<SubProject> listSubProjectsByModuleId(long projectModuleId) ;

    /**
     * 根据子项目id查询子项目
     */
    SubProject getById(long id);

    /**
     * 根据项目id查询子项目数量
     */
    int getSubProjectNumsByProjectId(long projectId);

    /**
     *  新增sub_project
     */
    boolean insert(SubProject subProject);

    /**
     *  删除sub_project
     */
    boolean deleteById(long id);

}

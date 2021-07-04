package com.example.octopus.service;

import com.example.octopus.entity.project.ProjectModule;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/7/4 2:00 下午
 */
public interface ProjectModuleService {


    /**
     * 根据projectId查询模块
     */
    List<ProjectModule> listByProjectId(long projectId);

    /**
     *  根据id获取projectModule实体
     */
    ProjectModule getById(long id);

    /**
     *  根据moduleNumber和moduleName获取projectModule实体
     */
    ProjectModule getByNumberAndName(int moduleNumber, String moduleName);

    /**
     *  根据id更改模块名
     */
    boolean updateModuleNameById(String moduleName,long id);


}

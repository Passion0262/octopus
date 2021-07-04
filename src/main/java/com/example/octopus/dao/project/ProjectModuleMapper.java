package com.example.octopus.dao.project;

import com.example.octopus.entity.project.ProjectModule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/7/4 1:45 下午
 */
@Mapper
public interface ProjectModuleMapper {

    /**
     * 根据projectId查询模块
     */
    @Select("SELECT * FROM project_module WHERE project_id = #{projectId} order by module_number")
    List<ProjectModule> listByProjectId(long projectId);

    /**
     *  根据id获取projectModule实体
     */
    @Select("SELECT * FROM project_module WHERE id =#{id}")
    ProjectModule getById(long id);

    /**
     *  根据moduleNumber和moduleName获取projectModule实体
     */
    @Select("SELECT * FROM project_module WHERE module_number=#{moduleNumber} AND module_name=#{moduleName}")
    ProjectModule getByNumberAndName(int moduleNumber, String moduleName);

    /**
     *  根据id更改模块名
     */
    @Update("UPDATE project_module SET module_name=#{moduleName} WHERE id = #{id}")
    boolean updateModuleNameById(String moduleName,long id);



}

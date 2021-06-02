package com.example.octopus.dao;

import com.example.octopus.entity.project.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/2 4:44 下午
 */
@Mapper
public interface ProjectMapper {

    @Select("SELECT * FROM project")
    List<Project> queryAllProject();

}

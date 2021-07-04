package com.example.octopus.dao.project;

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

    /**
     * 查找所有的项目实战
     * @return 项目实战list
     */
    @Select("SELECT * FROM project")
    List<Project> listProjects();

    /**
     * 根据项目实战id查找项目实战
     * @return 项目实战实体
     */
    @Select("SELECT * FROM project WHERE id = #{id}")
    Project getProject(long id);

}

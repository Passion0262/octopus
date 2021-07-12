package com.example.octopus.dao.project;

import com.example.octopus.entity.VOs.SubProjectDetailVO;
import com.example.octopus.entity.project.SubProject;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/7/4 2:11 下午
 */
@Mapper
public interface SubProjectMapper {

    @Select("SELECT sp.id AS sub_project_id, sp.sub_project_name, sp.number AS sub_project_number, pm.module_id, pm.module_name, pm.module_number, p.id AS project_id, p.name AS project_name " +
            "FROM sub_project sp, project_module pm, project p " +
            "WHERE sp.project_id=p.id AND sp.project_module_id=pm.module_id " +
            "ORDER BY sp.project_id, pm.module_number, sp.number")
    List<SubProjectDetailVO> listAllSubProjectDetail();

    /**
     * 根据 projectModuleId 查询子项目
     */
    @Select(("SELECT * FROM sub_project WHERE project_module_id = #{projectModuleId} order by number"))
    List<SubProject> listSubProjectsByModuleId(long projectModuleId) ;

    /**
     * 根据子项目id查询子项目
     */
    @Select("SELECT * FROM sub_project WHERE id = #{id}")
    SubProject getById(long id);

    /**
     * 根据项目id查询子项目数量
     */
    @Select("SELECT COUNT(*) FROM sub_project WHERE project_id = #{projectId}")
    int getSubProjectNumsByProjectId(long projectId);

    /**
     *  新增sub_project
     */
    @Insert("INSERT INTO sub_project (project_id, project_module_id, sub_project_name, number, image_path, requirement_path, knowledge_path, copyable, last_update_time) VALUES (#{projectId}, #{projectModuleId}, #{subProjectName}, #{number}, #{imagePath}, #{requirementPath}, #{knowledgePath}, #{copyable}, CURRENT_TIMESTAMP)")
    boolean insert(SubProject subProject);

    /**
     *  删除sub_project
     */
    @Delete("DELETE FROM sub_project WHERE id = #{id}")
    boolean deleteById(long id);

}

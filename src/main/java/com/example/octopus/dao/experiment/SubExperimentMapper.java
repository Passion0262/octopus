package com.example.octopus.dao.experiment;

import com.example.octopus.entity.experiment.SubExperiment;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/10 3:17 下午
 */
@Mapper
public interface SubExperimentMapper {

    /**
     * 根据实验id获取所有的子实验id
     */
    @Select("SELECT id FROM sub_experiment WHERE experiment_id = #{experimentId}")
    List<Long> listIdsByExperimentId(long experimentId);

    /**
     * 根据实验id获取所有的子实验
     */
    @Select("SELECT * FROM sub_experiment WHERE experiment_id = #{experimentId}")
    List<SubExperiment> listByExperimentId(long experimentId);

    /**
     * 根据 moduleId 查询子实验
     * @param moduleId 模块id
     * @return subExperiment list
     */
    @Select(("SELECT * FROM sub_experiment WHERE module_id = #{moduleId} order by number"))
    List<SubExperiment> listSubExperimentsByModuleId(long moduleId) ;

    /**
     * 根据子实验id查询子实验
     * @param id 子实验id
     * @return 子实验实体
     */
    @Select("SELECT * FROM sub_experiment WHERE id = #{id}")
    SubExperiment getById(long id);

    /**
     * 根据实验id查询子实验数量
     * @param experimentId 实验id
     * @return 子实验数量
     */
    @Select("SELECT COUNT(*) FROM sub_experiment WHERE experiment_id = #{experimentId}")
    int getSubExperimentNumsByExperimentId(long experimentId);

    /**
     *  新增sub_experiment
     */
    @Insert("INSERT INTO sub_experiment (experiment_id,module_id,sub_experiment_name,number,image_path,expect_time,requirement_path,knowledge_path,template_path,copyable,last_update_time) VALUES (#{experimentId},#{moduleId},#{subExperimentName},#{number},#{imagePath},#{expectTime},#{requirementPath},#{knowledgePath},#{templatePath},#{copyable},CURRENT_TIMESTAMP)")
    boolean insert(SubExperiment subExperiment);

    /**
     *  删除sub_experiment
     */
    @Delete("DELETE FROM sub_experiment WHERE id = #{id}")
    boolean deleteById(long id);
}

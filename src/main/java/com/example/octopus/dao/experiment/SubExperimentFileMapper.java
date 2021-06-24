package com.example.octopus.dao.experiment;

import com.example.octopus.entity.experiment.SubExperimentFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/24 1:56 下午
 */
@Mapper
public interface SubExperimentFileMapper {

    /**
     *  根据id查询
     */
    @Select("SELECT * FROM sub_experiment_file WHERE id = #{id}")
    SubExperimentFile getById(long id);

    /**
     *  根据子实验id查询
     */
    @Select("SELECT * FROM sub_experiment_file WHERE sub_experiment_id = #{subExperimentId}")
    SubExperimentFile getBySubExperimentId(long subExperimentId);

    /**
     *  新增
     */
    @Insert("INSERT INTO sub_experiment_file (sub_experiment_id, requirement_path, knowledge_path, template_path, last_update_time, creator) VALUES (#{subExperimentId},#{requirementPath},#{knowledgePath},#{templatePath},#{lastUpdateTime},#{creator})")
    boolean insert(SubExperimentFile subExperimentFile);

    /**
     *  更新
     */
    @Update("UPDATE sub_experiment_file SET sub_experiment_id=#{subExperimentId}, requirement_path=#{requirementPath}, knowledge_path=#{knowledgePath} ,template_path=#{template_Path}, last_update_time=CURRENT_TIMESTAMP, creator=#{creator} WHERE id = #{id}")
    boolean updateById(SubExperimentFile subExperimentFile);

}

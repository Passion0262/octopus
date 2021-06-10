package com.example.octopus.dao.experiment;

import com.example.octopus.entity.experiment.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/10 7:06 下午
 */
@Mapper
public interface VideoSubExperimentMapper {

    /**
     * 根据experimentId查找对应的videoId
     */
    @Select("SELECT video_id FROM video_sub_experiment WHERE sub_experiment_id = subExperimentId")
    long getVideoIdBySubExperimentId(long subExperimentId);

    /**
     * 根据videoId查找对应的experimentId
     */
    @Select("SELECT sub_experiment_id FROM video_sub_experiment WHERE video_id = videoId")
    long getSubExperimentIdByVideoId(long videoId);

}

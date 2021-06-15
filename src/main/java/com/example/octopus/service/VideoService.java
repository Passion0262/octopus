package com.example.octopus.service;

import com.example.octopus.entity.experiment.Video;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/10 7:10 下午
 */
public interface VideoService {

    /**
     * 返回所有视频的实体list
     */
    List<Video> listVideos();

    /**
     * 根据courseId返回对应的video list
     */
    List<Video> listVideosByCourseId(long courseId);

    /**
     * 根据chapterId返回对应的video list
     */
    List<Video> listVideosByChapterId(long chapterId);

    /**
     * 根据id查找video
     */
    Video getById(long id);

    /**
     * 根据experimentId查找对应的videoId
     */
    Long getVideoIdBySubExperimentId(long subExperimentId);

    /**
     * 根据experimentId查找对应的video
     */
    Video getVideoBySubExperimentId(long subExperimentId);

}

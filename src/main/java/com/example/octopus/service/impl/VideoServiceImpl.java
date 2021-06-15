package com.example.octopus.service.impl;

import com.example.octopus.dao.experiment.VideoMapper;
import com.example.octopus.dao.experiment.VideoSubExperimentMapper;
import com.example.octopus.entity.experiment.Video;
import com.example.octopus.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/10 7:11 下午
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    VideoMapper videoMapper;

    @Autowired
    VideoSubExperimentMapper videoSubExperimentMapper;

    @Override
    public List<Video> listVideosByCourseId(long courseId) {
        return videoMapper.listVideosByCourseId(courseId);
    }

    @Override
    public List<Video> listVideosByChapterId(long chapterId) {
        return videoMapper.listVideosByChapterId(chapterId);
    }

    @Override
    public Video getById(long id) {
        return videoMapper.getById(id);
    }

    @Override
    public Long getVideoIdBySubExperimentId(long subExperimentId) {
        return videoSubExperimentMapper.getVideoIdBySubExperimentId(subExperimentId);
    }

    @Override
    public Video getVideoBySubExperimentId(long subExperimentId) {
        Long videoId = getVideoIdBySubExperimentId(subExperimentId);
        if (videoId == null){
            return null;
        }else {
            return getById(videoId);
        }
    }
}

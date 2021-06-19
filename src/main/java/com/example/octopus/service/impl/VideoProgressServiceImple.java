package com.example.octopus.service.impl;

import com.example.octopus.dao.VideoProgressMapper;
import com.example.octopus.dao.experiment.VideoMapper;
import com.example.octopus.entity.experiment.Video;
import com.example.octopus.entity.experiment.VideoProgress;
import com.example.octopus.service.VideoProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/19 4:29 下午
 */
@Service
public class VideoProgressServiceImple implements VideoProgressService {

    @Autowired
    VideoProgressMapper videoProgressMapper;

    @Autowired
    VideoMapper videoMapper;

    @Override
    public List<VideoProgress> listByStuNumber(long stuNumber) {
        return videoProgressMapper.listByStuNumber(stuNumber);
    }

    @Override
    public List<VideoProgress> listByCourseIdAndStuNumber(long courseId, long stuNumber) {
        List<Long> videoIdList = videoMapper.listVideoIdsByCourseId(courseId);
        List<VideoProgress> videoProgressList = new ArrayList<>();
        for (long videoId:videoIdList) {
            videoProgressList.add(videoProgressMapper.getById(videoId));
        }
        return videoProgressList;
    }

    @Override
    public VideoProgress getById(long id) {
        return videoProgressMapper.getById(id);
    }

    @Override
    public VideoProgress getByVideoIdAndStuNumber(long videoId, long stuNumber) {
        return videoProgressMapper.getByVideoIdAndStuNumber(videoId,stuNumber);
    }

    @Override
    public boolean updateById(VideoProgress videoProgress) {
        return videoProgressMapper.updateById(videoProgress);
    }
}

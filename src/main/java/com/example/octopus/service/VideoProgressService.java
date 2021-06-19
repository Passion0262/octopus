package com.example.octopus.service;

import com.example.octopus.entity.experiment.VideoProgress;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/19 4:25 下午
 */
public interface VideoProgressService {

    /**
     *  根据学生学号查询所有的videoProgress
     */
    List<VideoProgress> listByStuNumber(long stuNumber);

    /**
     *  根据课程id和学生学号查询所有的videoProgress
     */
    List<VideoProgress> listByCourseIdAndStuNumber(long courseId, long stuNumber);

    /**
     *  根据id查找videoProgress
     */
    VideoProgress getById(long id);

    /**
     *  根据videoId和学生id查询videoProgress
     */
    VideoProgress getByVideoIdAndStuNumber(long videoId, long stuNumber);

    /**
     *  更新videoProgress
     */
    boolean updateById(VideoProgress videoProgress);

}

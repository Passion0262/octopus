package com.example.octopus.service;

import com.example.octopus.entity.VOs.VideoStudySummaryVO;
import com.example.octopus.entity.experiment.VideoProgress;

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

    /**
     *  插入videoProgress
     */
    boolean insertVideoProgress(VideoProgress videoProgress);

    /**
     * 获取视频学习汇总
     * @param teaNumber 教师号，用于自动分辨管理员or教师
     * @return 对管理员输出所有，对教师仅输出其所授课程的视频学习汇总情况
     */
    List<VideoStudySummaryVO> getVideoStudySummaryByRole(long teaNumber);
}

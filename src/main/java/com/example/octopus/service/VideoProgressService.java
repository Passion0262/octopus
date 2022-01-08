package com.example.octopus.service;

import com.example.octopus.entity.vo.CourseTimeVO;
import com.example.octopus.entity.vo.video.VideoProgressDetailVO;
import com.example.octopus.entity.vo.video.VideoProgressHistoryVO;
import com.example.octopus.entity.vo.video.VideoTimeHistoryVO;
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
     *  根据学生ID,返回每门课的视频学习时长
     *  @return 返回列表 单位秒
     */
    List<CourseTimeVO> countStudyTimeByStuNumberGroupByCourseId(long stuNumber);

    /**
     *  查询该学生在该课程所有学习记录
     */
    List<VideoProgress> listByCourseIdAndStuNumber(long courseId, long stuNumber);

    /**
     *  根据课程id和学生学号查询所有的最新的videoProgress
     */
    List<VideoProgress> listLatestByCourseIdAndStuNumber(long courseId, long stuNumber);

    /**
     *  获取学生过去7天的学习时间
     */
    List<VideoTimeHistoryVO> getHistory7DaysStudyTime(long stuNumber);

    /**
     * 计算学生该课程一共的视频学习时间
     *
     * @param courseId 课程id
     * @param stuNumber 学号
     * @return 学习时间（单位：秒）
     */
    int getStudyTimeByCourseIdAndStuNumber(long courseId, long stuNumber);

    /**
     *  根据id查找videoProgress
     */
    VideoProgress getById(long id);

    /**
     *  根据videoId和学生id查询最新的videoProgress
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
    List<VideoProgressHistoryVO> getVideoProgressHistoryByRole(long teaNumber);

    /**
     * 计算学生在课程的进度
     * 该课程下学生的进度总和除以总进度总和
     */
    double getCourseProgress(long courseStaticId, long stuNumber);

    /**
     * 视频学习详情，每个学生和每个视频对应一条数据。根据教师不同权x限
     * @param teaNumber 教师号
     * @return 获取所有学生在视频中的学习信息
     */
    List<VideoProgressDetailVO> getVideoProgressDetailByRole(long teaNumber);
}

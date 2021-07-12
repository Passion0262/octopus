package com.example.octopus.service;

import com.example.octopus.entity.VOs.VideoManageVO;
import com.example.octopus.entity.VOs.VideoStudySummaryVO;
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

    /**
     * 视频课程管理，根据角色，显示相应视频信息，包括对应课程和章节的id和name
     * @return 视频课程管理视图类列表
     */
    List<VideoManageVO> getVideoManageInfoByRole(long teaNumber);

    /**
     * 添加视频课程 或 在不知道视频id的情况下改视频名称和视频地址
     * @param video 视频课程实体
     * @return 成功与否
     */
    boolean addVideo(Video video);

    /**
     * 通过视频Id更新视频信息
     * @param video 视频课程实体
     * @return 成功与否
     */
    boolean updateVideo(Video video);

    /**
     * 通过视频课程Id删除视频
     * @param id 视频课程id
     * @return 成功与否
     */
    boolean deleteVideoById(long id);

    /**
     * 视频学习汇总，每个学生和每个课程对应一条数据，这个学生在这门课程里面的视频学习总时长、首次学习时间、末次学习时间
     * @param teaNumber 教师号
     * @return 获取所有学生在课程中的视频学习汇总信息
     */
    List<VideoStudySummaryVO> getVideoStudySummaryByRole(long teaNumber);

}

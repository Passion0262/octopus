package com.example.octopus.dao;

import com.example.octopus.entity.experiment.VideoProgress;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/19 4:01 下午
 */
@Mapper
public interface VideoProgressMapper {

    /**
     *  根据学生学号查询所有的videoProgress
     */
    @Select("SELECT * FROM video_progress WHERE stu_number = #{stuNumber}")
    List<VideoProgress> listByStuNumber(long stuNumber);

    /**
     *  根据id查找videoProgress
     */
    @Select("SELECT * FROM video_progress WHERE id = #{id}")
    VideoProgress getById(long id);

    /**
     *  根据videoId和学生id查询videoProgress
     */
    @Select("SELECT * FROM video_progress WHERE video_id = #{videoId} AND stu_number = #{stuNumber} ORDER BY end_time desc LIMIT 1")
    VideoProgress getByVideoIdAndStuNumber(long videoId, long stuNumber);

    /**
     *  更新videoProgress
     */
    @Update("UPDATE video_progress SET study_time = #{studyTime},progress = #{progress}, last_video_progress=#{lastVideoProgress} WHERE id = #{id}")
    boolean updateById(VideoProgress videoProgress);

    @Insert("INSERT INTO video_progress(video_id,stu_number,start_time,end_time,study_time,progress,last_video_progress) VALUES (#{videoId},#{stuNumber},#{startTime},#{endTime},#{studyTime},#{progress},#{lastVideoProgress})")
    boolean insertVideoProgress(VideoProgress videoProgress);

}

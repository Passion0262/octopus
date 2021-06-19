package com.example.octopus.dao.experiment;

import com.example.octopus.entity.experiment.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/10 7:00 下午
 */
@Mapper
public interface VideoMapper {

    /**
     * 返回所有video的list
     */
    @Select("SELECT * FROM video")
    List<Video> listVideos();

    /**
     * 根据courseId返回对应的video list
     */
    @Select("SELECT * FROM video WHERE course_id = #{courseId}")
    List<Video> listVideosByCourseId(long courseId);

    /**
     * 根据courseId返回对应的videoId list
     */
    @Select("SELECT id FROM video WHERE course_id = #{courseId}")
    List<Long> listVideoIdsByCourseId(long courseId);

    /**
     * 根据chapterId返回对应的video list
     */
    @Select("SELECT * FROM video WHERE chapter_id = #{chapterId}")
    List<Video> listVideosByChapterId(long chapterId);

    /**
     * 根据id查找video
     */
    @Select("SELECT * FROM video WHERE id = #{id}")
    Video getById(long id);



}

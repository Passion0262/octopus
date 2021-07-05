package com.example.octopus.dao.experiment;

import com.example.octopus.entity.VOs.VideoManageVO;
import com.example.octopus.entity.experiment.Video;
import lombok.Data;
import org.apache.ibatis.annotations.*;

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

    @Select("SELECT * FROM video WHERE number=#{number} AND chapter_id = #{chapterId} AND course_id = #{courseId}")
    List<Video> getVideoByCourseIdAndChapterIdAndNumber(int number, long chapterId, long courseId);

    /**
     * 根据id查找video
     */
    @Select("SELECT * FROM video WHERE id = #{id}")
    Video getById(long id);

    @Select("SELECT v.*, c.course_name, cha.chapter_name " +
            "FROM video v, course c, chapter cha " +
            "WHERE v.chapter_id=cha.chapter_id and v.course_id=c.id")
    List<VideoManageVO> getAllVideoManage();

    @Insert("INSERT INTO video (name, number, path, chapter_id, course_id) " +
            "VALUES (#{name}, #{number}, #{path}, #{chapterId}, #{courseId})")
    boolean addVideo(Video video);

    @Insert("INSERT INTO video (id, name, number, path, chapter_id, course_id) " +
            "VALUES (#{id}, #{name}, #{number}, #{path}, #{chapterId}, #{courseId})")
    boolean addVideoWithId(Video video);

    @Update("UPDATE video SET name=#{name}, path=#{path} WHERE id=#{id}")
    boolean updateNameAndPathById(long id, String name, String path);

    @Delete("DELETE * FROM video WHERE id=#{id}")
    boolean deleteVideoById(long id);
}

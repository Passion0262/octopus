package com.example.octopus.dao.experiment;

import com.example.octopus.entity.vo.video.VideoManageVO;
import com.example.octopus.entity.vo.video.VideoStudySummaryVO;
import com.example.octopus.entity.experiment.Video;
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
    @Select("SELECT id FROM video WHERE course_id = #{courseStaticId}")
    List<Long> listVideoIdsByCourseStaticId(long courseStaticId);

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

    /**
     *  这里是管理员查找所有已有的课程，所以查找course_static表
     */
    @Select("SELECT v.*, c.course_name, cha.chapter_name, cha.chapter_number " +
            "FROM video v, course_static c, chapter cha " +
            "WHERE v.chapter_id=cha.chapter_id and v.course_id=c.course_static_id " +
            "ORDER BY v.course_id, cha.chapter_number, v.number")
    List<VideoManageVO> getAllVideoManage();

    /**
     *  这里是查找teacher开的课，所以查找course表
     */
    @Select("SELECT v.*, c.course_name, cha.chapter_name, cha.chapter_number " +
            "FROM video v, course c, chapter cha " +
            "WHERE c.tea_number=#{teaNumber} and v.chapter_id=cha.chapter_id and v.course_id=c.course_static_id " +
            "ORDER BY v.course_id, cha.chapter_number, v.number")
    List<VideoManageVO> getVideoManageByTeaId(long teaNumber);

    @Insert("INSERT INTO video (name, number, path, chapter_id, course_id) " +
            "VALUES (#{name}, #{number}, #{path}, #{chapterId}, #{courseId})")
    boolean addVideo(Video video);

    @Insert("INSERT INTO video (id, name, number, path, chapter_id, course_id) " +
            "VALUES (#{id}, #{name}, #{number}, #{path}, #{chapterId}, #{courseId})")
    boolean addVideoWithId(Video video);

    @Update("UPDATE video SET name=#{name}, path=#{path} WHERE id=#{id}")
    boolean updateNameAndPathById(long id, String name, String path);

    @Delete("DELETE FROM video WHERE id=#{id}")
    boolean deleteVideoById(long id);

    /**
     * 视频学习汇总，每个学生和每个课程对应一条数据，这个学生在这门课程里面的视频学习总时长、首次学习时间、末次学习时间
     * @return 获取所有学生在课程中的视频学习汇总信息
     */
    @Select("select stu_number, name as stu_name, course_id, course_name, sum(study_time_sum)  as study_time_total, min(very_start_time) as first_start_time, max(very_last_time)  as last_end_time " +
            "from video_process_detail " +
            "group by stu_number and course_id")
    List<VideoStudySummaryVO> getAllVideoStudySummary();

    @Select("select stu_number, name as stu_name, course_id, vpd.course_name, sum(study_time_sum)  as study_time_total, min(very_start_time) as first_start_time, max(very_last_time)  as last_end_time " +
            "from video_process_detail vpd, course where course.course_static_id=vpd.course_id and course.tea_number=#{teaNumber} " +
            "group by stu_number and course_id")
    List<VideoStudySummaryVO> getVideoStudySummaryByTeacherId(long teaNumber);
}

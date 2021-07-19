package com.example.octopus.dao.experiment;

import com.example.octopus.entity.VOs.CourseTimeVO;
import com.example.octopus.entity.VOs.VideoProgressDetailVO;
import com.example.octopus.entity.VOs.VideoProgressHistoryVO;
import com.example.octopus.entity.VOs.VideoTimeHistoryVO;
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

    @Select("SELECT * FROM video_progress")
    List<VideoProgress> getAllVP();

    /**
     *  根据学生学号查询所有的videoProgress
     */
    @Select("SELECT * FROM video_progress WHERE stu_number = #{stuNumber} order by end_time desc")
    List<VideoProgress> listByStuNumber(long stuNumber);

    /**
     *  根据学生ID,返回每门课的视频学习时长
     *  @return 返回列表 单位秒
     */
    @Select("SELECT v.course_id,sum(vp.study_time) as time FROM video v,video_progress vp WHERE vp.stu_number=#{stuNumber} AND v.id = vp.video_id")
    List<CourseTimeVO> countStudyTimeByStuNumberGroupByCourseId(long stuNumber);

    /**
     *  根据学生学号和videoId查询所有的videoProgress
     */
    @Select("SELECT * FROM video_progress WHERE stu_number = #{stuNumber} AND video_id = #{videoId} order by end_time desc")
    List<VideoProgress> listByVideoIdAndStuNumber(long videoId, long stuNumber);

    /**
     *  根据id查找videoProgress
     */
    @Select("SELECT * FROM video_progress WHERE id = #{id}")
    VideoProgress getById(long id);

    /**
     *  根据videoId和学生id查询最新的videoProgress
     */
    @Select("SELECT * FROM video_progress WHERE video_id = #{videoId} AND stu_number = #{stuNumber} AND end_time=" +
            "(SELECT MAX(end_time) FROM video_progress WHERE video_id=#{videoId} AND stu_number = #{stuNumber}) limit 1")
    VideoProgress getLatestByVideoIdAndStuNumber(long videoId, long stuNumber);

    /**
     *  计算该学生在该视频学习时间总长度
     */
    @Select("SELECT SUM(study_time) FROM video_progress WHERE video_id=#{videoId} AND stu_number=#{stuNumber}")
    Integer countStudyTime(long videoId, long stuNumber);

    /**
     *  获取学生过去7天的学习时间
     */
    @Select("SELECT DATE_FORMAT( end_time, '%Y-%m-%d' ) date, SUM(study_time) time " +
            "FROM ( SELECT * FROM video_progress WHERE DATE_SUB( CURDATE( ), INTERVAL 7 DAY ) <= date(end_time)) as day " +
            "GROUP BY date")
    List<VideoTimeHistoryVO> getHistory7DaysStudyTime(long stuNumber);

    /**
     *  更新videoProgress
     */
    @Update("UPDATE video_progress SET study_time = #{studyTime},progress = #{progress}, last_video_progress=#{lastVideoProgress} WHERE id = #{id}")
    boolean updateById(VideoProgress videoProgress);

    @Insert("INSERT INTO video_progress(video_id,stu_number,start_time,end_time,study_time,progress,last_video_progress) VALUES (#{videoId},#{stuNumber},#{startTime},#{endTime},#{studyTime},#{progress},#{lastVideoProgress})")
    boolean insertVideoProgress(VideoProgress videoProgress);

    @Select("SELECT vp.*, s.name, class_.class_name, major.major_name, course.course_name  " +
            "FROM video_progress vp, student s, class_, major, course, video " +
            "WHERE vp.stu_number=s.stu_number and s.major_id=major.id and s.class_id=class_.id and vp.video_id=video.id and video.course_id=course.id")
    List<VideoProgressHistoryVO> getAllVideoStudyDetail();


    @Select("SELECT vp.*, s.name, class_.class_name, major.major_name, course.course_name " +
            "FROM video_progress vp, student s, class_, major, course, video " +
            "WHERE vp.stu_number=s.stu_number and s.major_id=major.id and s.class_id=class_.id and vp.video_id=video.id and video.course_id=course.id and course.tea_number=#{teaNumber}")
    List<VideoProgressHistoryVO> getVideoStudyDetailByTeacherId(long teaNumber);

    /**
     *  查询所有进度为100的videoId
     */
    @Select("SELECT video_id FROM video_progress WHERE progress=100 AND stu_number = #{stuNumber}")
    List<Long> getFinishedVideoIdsByStuNumber(long stuNumber);

    /**
     * 视频学习详情，每个学生和每个视频对应一条数据
     * @return 返回所有学生在视频中的学习信息
     */
    @Select("select vp.stu_number, s.name, vp.video_id, v.name as video_name, v.course_id, c.course_name, sum(vp.study_time) as study_time_sum, min(vp.start_time) as very_start_time, max(vp.end_time) as very_last_time " +
            "from video_progress vp, video v, course c, student s " +
            "where vp.stu_number = s.stu_number and vp.video_id = v.id and v.course_id = c.id " +
            "group by vp.stu_number, vp.video_id")
    List<VideoProgressDetailVO>  getAllVideoProgressDetail();

    @Select("select vp.stu_number, s.name, vp.video_id, v.name as video_name, v.course_id, c.course_name, sum(vp.study_time) as study_time_sum, min(vp.start_time) as very_start_time, max(vp.end_time) as very_last_time " +
            "from video_progress vp, video v, course c, student s " +
            "where vp.stu_number = s.stu_number and c.tea_number = #{teaNumber} and vp.video_id = v.id and v.course_id = c.id " +
            "group by vp.stu_number, vp.video_id")
    List<VideoProgressDetailVO> getVideoProgressDetailByTeacherId(long teaNumber);

}

package com.example.octopus.dao;

import com.example.octopus.entity.experiment.Chapter;
import com.example.octopus.entity.experiment.Video;
import com.example.octopus.entity.user.Course;
import com.example.octopus.entity.user.Student;
import com.example.octopus.entity.user.StudentCourse;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;


import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/28 5:04 下午
 * @modified By：
 */
@Mapper
public interface CourseMapper {

    /**
     * 查询所有课程并返回
     * @return 课程list
     */
    @Select("SELECT * FROM course")
    List<Course> listCourses();

    @Select("SELECT * FROM course WHERE tea_number=#{teaNumber}")
    List<Course> listCoursesByTeaId(long teaNumber);

    /**
     * 根据课程id返回课程
     * @param id 课程id
     * @return
     */
    @Select(("SELECT * FROM course where id = #{id}"))
    Course getCourseById(long id);

    /**
     *  根据学生id查询已选课程数
     */
    @Select("SELECT count(*) FROM student_course WHERE stu_number=#{stu_number}")
    int countCourseChosen(long stuNumber);

    /**
     *  获取学生完成的课程的名字
     */
    @Select("SELECT course_name FROM student_course WHERE stu_name=#{stuNumber} AND completed=1")
    List<String> listCompletedCourses(long stuNumber);

    /**
     * 增加课程实体  已设置触发器，不需要输入course_name和tea_name
     * @param course  课程实体
     * @return 成功为true，失败为false
     */
    @Insert("INSERT INTO course (course_static_id, tea_number,start_time,end_time,apply_time,num_allowed,num_participated,status) " +
            "VALUES (#{courseStaticId},#{teaNumber},#{startTime},#{endTime},#{applyTime},#{numAllowed},#{numParticipated},#{status})")
    boolean insertCourse(Course course);

    /**
     *  将课程状态更新为已完成
     */
    @Update("UPDATE course SET completed = 1 WHERE course_id=#{courseId} AND stu_number = #{stuNumber}")
    boolean updateCompleted(long courseId, long stuNumber);

    /**
     * 修改课程
     * @param course  课程实体
     * @return 成功为true，失败为false
     */
    @Update("Update course SET course_name = #{courseName},tea_number=#{teaNumber},tea_name=#{teaName},start_time=#{startTime},end_time=#{endTime},apply_time=#{applyTime},num_allowed=#{numAllowed},num_participated=#{numParticipated},status=#{status} WHERE id = #{id}")
    boolean updateCourse(Course course);

    /**
     * 根据courseid删除课程
     * @param courseId 课程id
     * @return 成功为true，失败为false
     */
    @Delete("DELETE FROM course WHERE id = #{courseId}")
    boolean deleteCourseById(long courseId);


    /**
     * 根据courseid查询章节
     * @param courseId 课程id
     * @return chapter list
     */
    @Select(("SELECT * FROM chapter where course_id = #{courseId} order by number" ))
    List<Chapter> getChapterByCourseId(long courseId) ;

    /**
     * 根据 chapterid 查询视频
     * @param chapterId 课程id
     * @return video list
     */
    @Select(("SELECT * FROM video where chapter_id = #{chapterId} order by number"))
    List<Video> getVideoByChapterId(long chapterId) ;


}

package com.example.octopus.dao;

import com.example.octopus.entity.VOs.AdminInfoVO;
import com.example.octopus.entity.user.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/5 8:12 下午
 */
@Mapper
public interface TeacherMapper {
    /**
     * 根据teaNumber查找教师
     * @param teaNumber 老师编号
     * @return teacher实体
     */
    @Select("SELECT t.*, major_name FROM teacher t, major m WHERE tea_number = #{teaNumber} and t.major_id=m.id")
    Teacher getByTeaNumber(long teaNumber);

    @Select("SELECT t.*, major_name FROM teacher t, major m WHERE t.major_id=m.id")
    List<Teacher> getAllTeachers();

    @Update("UPDATE teacher SET tea_name = #{teaName},major_id = #{majorId},admin_rights = #{adminRights},phone = #{phone},school = #{school}" +
            "WHERE tea_number = #{teaNumber}")
    boolean updateTeacher(Teacher teacher);

    @Update("UPDATE teacher SET login_number = login_number+1, last_login_time = this_login_time, this_login_time = CURRENT_TIMESTAMP WHERE tea_number = #{teaNumber}")
    boolean updateLoginInfoByTeaNumber(long teaNumber);

    @Insert("INSERT INTO teacher (tea_number,tea_name,major_id,admin_rights,phone,school,login_number,last_login_time) " +
            "VALUES (#{teaNumber},#{teaName},#{majorId},#{adminRights},#{phone},#{school},0,CURRENT_TIMESTAMP)")
    boolean addTeacher(Teacher teacher);

    @Delete("DELETE FROM teacher WHERE tea_number=#{teaNumber}")
    boolean deleteTeacher(long teaNumber);

    @Select("SELECT phone FROM teacher WHERE tea_number=#{teaNumber}")
    String getPhoneByTeaNumber(long teaNumber);

    @Select("SELECT school FROM teacher WHERE tea_number=#{teaNumber}")
    String getSchoolByTeaNumber(long teaNumber);

    ///////////获取首页展示信息：返回 最近七天 视频学习时间 和 实验学习时间 两个列表 单位为秒
    //视频学习时间
    @Select("SELECT SUM(study_time) AS sum_study_time, date(start_time) AS study_date, CURDATE() AS today FROM video_progress " +
            "WHERE DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(start_time) GROUP BY date(start_time)")
    List<AdminInfoVO> getAllVideoStudyTimeSum();

    @Select("SELECT SUM(vp.study_time) AS sum_study_time, date(vp.start_time) AS study_date, CURDATE() AS today FROM video_progress vp, video v, course c " +
            "WHERE DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(vp.start_time) " +
            "AND vp.video_id=v.id AND v.course_id=c.id AND c.tea_number=#{teaNumber} GROUP BY date(vp.start_time)")
    List<AdminInfoVO> getVideoStudyTimeSumByTeaNumber(long teaNumber);

    //实验学习时间
    @Select("SELECT SUM(valid_study_time) AS sum_study_time, date(start_time) AS study_date, CURDATE() AS today FROM sub_experiment_progress " +
            "WHERE DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(start_time) GROUP BY date(start_time)")
    List<AdminInfoVO> getAllExperimentTimeSum();

    @Select("SELECT SUM(sep.valid_study_time) AS sum_study_time, date(sep.start_time) AS study_date, CURDATE() AS today " +
            "FROM sub_experiment_progress sep, sub_experiment se, course_experiment ce, course c " +
            "WHERE DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(sep.start_time) AND sep.sub_experiment_id=se.id " +
            "AND se.experiment_id = ce.experiment_id AND ce.course_id=c.id AND c.tea_number=#{teaNumber} GROUP BY date(sep.start_time)")
    List<AdminInfoVO> getExperimentTimeSumByTeaNumber(long teaNumber);






}

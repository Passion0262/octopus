package com.example.octopus.dao.experiment;

import com.example.octopus.entity.vo.experiment.SubExperimentOperateTimeVO;
import com.example.octopus.entity.experiment.SubExperimentProgress;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/24 2:51 下午
 */
@Mapper
public interface SubExperimentProgressMapper {

    /**
     *  根据ID查询
     */
    @Select("SELECT * FROM sub_experiment_progress WHERE id = #{id}")
    SubExperimentProgress getById(long id);

    /**
     *  根据子实验id查询
     */
    @Select("SELECT * FROM sub_experiment_progress WHERE sub_experiment_id = #{subExperimentId}")
    List<SubExperimentProgress> listBySubExperimentId(long subExperimentId);

    /**
     *  根据学生id查询，返回这个学生所有的学习记录
     */
    @Select("SELECT * FROM sub_experiment_progress WHERE stu_number = #{stuNumber}")
    List<SubExperimentProgress> listByStuNumber(long stuNumber);

    /**
     *  根据学生id和实验id查询，返回这个学生有关这个实验所有的学习记录
     */
    @Select("SELECT * FROM sub_experiment s1, sub_experiment_progress s2 " +
            "WHERE s1.experiment_id=#{ExperimentId} AND s1.id=s2.sub_experiment_id AND s2.stu_number=#{stuNumber}")
    List<SubExperimentProgress> listByStuNumberAndExperimentId(long stuNumber, long ExperimentId);

    /**
     *  根据学生id和子实验id查询，返回这个学生有关这个子实验所有的学习记录
     */
    @Select("SELECT * FROM sub_experiment_progress WHERE stu_number = #{stuNumber} AND sub_experiment_id = #{subExperimentId}")
    List<SubExperimentProgress> listByStuNumberAndSubExperimentId(long stuNumber, long subExperimentId);

    /**
     *  计算该学生总计有效学习时间
     */
    @Select("SELECT SUM(valid_study_time) FROM sub_experiment_progress WHERE stu_number = #{stuNumber}")
    int countValidStudyTimeByStuNumber(long stuNumber);

    /**
     *  计算该学生在某子实验上总计有效学习时间
     */
    @Select("SELECT SUM(valid_study_time) FROM sub_experiment_progress WHERE stu_number = #{stuNumber} AND sub_experiment_id=#{subExperimentId}")
    int countValidStudyTimeByStuNumberAndSubExpId(long stuNumber, long subExperimentId);

    /**
     *  计算该学生在该实验任务的所有总计有效学习时间
     */
    @Select("SELECT SUM(s2.valid_study_time) FROM sub_experiment s1, sub_experiment_progress s2 " +
            "WHERE s1.experiment_id = #{experimentId} AND s1.id=s2.sub_experiment_id AND s2.stu_number=#{stuNumber}")
    int countValidStudyTimeOnExperiment(long stuNumber, long experimentId);

    /**
     *  新增子实验学习时间记录
     *  sep表中的开课id即tea_course_id，使用before insert触发器自动查询并填写，不需要手动插入
     */
    @Insert("INSERT INTO sub_experiment_progress (sub_experiment_id, stu_number, start_time, end_time, valid_study_time) " +
            "VALUES (#{subExperimentId},#{stuNumber},#{startTime},#{endTime},#{validStudyTime})")
    boolean insert(SubExperimentProgress subExperimentProgress);

    /**
     *  更新子实验学习时间记录，主要是在学生结束本次时进行“结束时间”和“有效学习时间”的更新
     */
    @Update("UPDATE sub_experiment_progress SET end_time=CURRENT_TIMESTAMP, valid_study_time=CURRENT_TIMESTAMP-start_time " +
            "WHERE stu_number=#{stuNumber} AND sub_experiment_id = #{subExperimentId}")
    boolean updateByStuNumberAndId(long stuNumber, long subExperimentId);

    /**
     *  （不建议使用，会出问题）更新子实验学习时间记录
     */
    @Update("UPDATE sub_experiment_progress SET sub_experiment_id=#{subExperimentId}, stu_number=#{stuNumber}, start_time=#{startTime}, end_time=#{endTime}, valid_study_time=#{validStudyTime} WHERE id =#{id}")
    boolean update(SubExperimentProgress subExperimentProgress);

    /**
     *  删除子实验学习时间记录
     */
    @Delete("DELETE FROM sub_experiment_progress WHERE id = #{id}")
    boolean delete(long id);

    //////////////////实验操作时长汇总
    /**
     * VO 联表查询 获取所有实验操作时长记录
     * 每个学生和每个实验任务对应一条数据
     * @return VO列表
     */
    @Select("SELECT sep.id, sep.sub_experiment_id, sep.stu_number, min(sep.start_time) AS start_time, max(sep.end_time) AS end_time, sum(sep.valid_study_time) AS valid_study_time, " +
            "       s.name, class_.class_name, major.major_name, cs.course_name, se.sub_experiment_name " +
            "FROM sub_experiment_progress sep, student s, class_, major, course_static cs, sub_experiment se " +
            "WHERE sep.stu_number=s.stu_number and s.major_id=major.id and s.class_id=class_.id and sep.sub_experiment_id=se.id and se.experiment_id=cs.experiment_id " +
            "GROUP BY sub_experiment_id, sep.stu_number")
    List<SubExperimentOperateTimeVO> getAllOperateTimeSummary();

    @Select("SELECT sep.id, sep.sub_experiment_id, sep.stu_number, min(sep.start_time) AS start_time, max(sep.end_time) AS end_time, sum(sep.valid_study_time) AS valid_study_time, " +
            "       s.name, class_.class_name, major.major_name, cs.course_name, se.sub_experiment_name " +
            "FROM sub_experiment_progress sep, student s, class_, major, course_static cs, sub_experiment se, course c " +
            "WHERE sep.tea_course_id=c.id AND c.tea_number=#{teaNumber} AND sep.stu_number=s.stu_number and s.major_id=major.id and s.class_id=class_.id and sep.sub_experiment_id=se.id and se.experiment_id=cs.experiment_id " +
            "GROUP BY sub_experiment_id, sep.stu_number")
    List<SubExperimentOperateTimeVO> getOperateTimeSummaryByTeacherId(long teaNumber);

    ///////////实验报告汇总下所属


}

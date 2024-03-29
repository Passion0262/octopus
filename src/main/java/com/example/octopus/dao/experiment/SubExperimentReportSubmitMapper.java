package com.example.octopus.dao.experiment;

import com.example.octopus.entity.vo.experiment.SubExperimentReportSummaryVO;
import com.example.octopus.entity.experiment.SubExperimentReportSubmit;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/24 8:27 下午
 */
@Mapper
public interface SubExperimentReportSubmitMapper{

    /**
     *  返回所有的提交记录
     */
    @Select("SELECT sers.*, se.sub_experiment_name, s.name as stu_name, s.class_id, c.class_name, t.tea_name " +
            "FROM sub_experiment_report_submit sers, sub_experiment se, student s, class_ c, teacher t " +
            "WHERE sers.sub_experiment_id = se.id AND sers.stu_number = s.stu_number AND s.class_id=c.id " +
            "       AND sers.tea_number = t.tea_number " +
            "ORDER BY sers.sub_experiment_id, s.class_id")
    List<SubExperimentReportSubmit> listAll();

    /**
     *  查询某子实验下所有学生的提交记录
     */
    @Select("SELECT sers.*, se.sub_experiment_name, s.name as stu_name, s.class_id, c.class_name, t.tea_name " +
            "FROM sub_experiment_report_submit sers, sub_experiment se, student s, class_ c, teacher t " +
            "WHERE sers.sub_experiment_id = #{subExpId} AND sers.sub_experiment_id = se.id AND sers.stu_number = s.stu_number " +
            "       AND s.class_id=c.id AND sers.tea_number = t.tea_number " +
            "ORDER BY s.class_id")
    List<SubExperimentReportSubmit> listBySubExperimentId(long subExpId);

    /**
     *  查询教师教的课的所有提交记录
     */
    @Select("SELECT sers.*, se.sub_experiment_name, s.name as stu_name, s.class_id, c.class_name, t.tea_name " +
            "FROM sub_experiment_report_submit sers, sub_experiment se, student s, class_ c, teacher t " +
            "WHERE sers.tea_number = #{teaNumber} AND sers.sub_experiment_id = se.id AND sers.stu_number = s.stu_number " +
            "       AND s.class_id=c.id AND sers.tea_number = t.tea_number " +
            "ORDER BY sers.sub_experiment_id, s.class_id")
    List<SubExperimentReportSubmit> listByTeaNumber(long teaNumber);

    /**
     * 查询某学生在某子实验上提交的记录
     */
    @Select("SELECT sers.*, se.sub_experiment_name, s.name as stu_name, s.class_id, c.class_name, t.tea_name " +
            "FROM sub_experiment_report_submit sers, sub_experiment se, student s, class_ c, teacher t " +
            "WHERE sers.sub_experiment_id = #{subExpId} AND sers.stu_number = #{stuNumber} AND sers.sub_experiment_id = se.id " +
            "       AND sers.stu_number = s.stu_number AND s.class_id=c.id AND sers.tea_number = t.tea_number")
    SubExperimentReportSubmit getByStuNumberAndSubExperimentId(long subExpId, long stuNumber);

    /**
     *  根据id查询
     */
    @Select("SELECT sers.*, se.sub_experiment_name, s.name as stu_name, s.class_id, c.class_name, t.tea_name " +
            "FROM sub_experiment_report_submit sers, sub_experiment se, student s, class_ c, teacher t " +
            "WHERE sers.id = #{id} AND sers.sub_experiment_id = se.id AND sers.stu_number = s.stu_number AND s.class_id=c.id " +
            "       AND sers.tea_number = t.tea_number")
    SubExperimentReportSubmit getById(long id);

    /**
     *  新增subExperimentReportSave
     *  不需要手动输入tea_number和tea_course_id，这两者使用触发器自动填写
     */
    @Insert("INSERT INTO sub_experiment_report_submit (sub_experiment_id, stu_number, content, submit_time) " +
            "VALUES (#{subExperimentId},#{stuNumber},#{content},CURRENT_TIMESTAMP)")
    boolean insert(SubExperimentReportSubmit subExperimentReportSubmit);

//    /**
//     *  学生提交报告 更新   学生一经提交就不能修改
//     */
//    @Update("UPDATE sub_experiment_report_submit SET content=#{content},submit_time=CURRENT_TIMESTAMP WHERE sub_experiment_id = #{subExperimentId} AND stu_number = #{stuNumber}")
//    boolean updateBySubmit(long subExperimentId, long stuNumber, String content);

    /**
     *  教师审核报告 更新
     *  这里其实不需要teaNumber，但为避免上层更多的改动就不管了
     */
    @Update("UPDATE sub_experiment_report_submit SET score=#{score}, examined=1, examined_time=CURRENT_TIMESTAMP " +
            "WHERE sub_experiment_id=#{subExpId} AND stu_number=#{stuNumber}")
    boolean updateByExamine(long subExpId, long stuNumber, long teaNumber, int score);

    /**
     *  删除subExperimentReportSave
     */
    @Delete("DELETE FROM sub_experiment_report_submit WHERE id = #{id}")
    boolean deleteById(long id);



    /////////////////////////////////       实验报告管理（汇总）
    /**
     * 联表查询count有问题，故分两步：1从sub_experiment_progress取未提交的数量；2从sub_experiment_report_submit取审核与未审核的数量
     * 注意1中未提交数量需要在implement中进行计算等
     */
    //管理员
    @Select("SELECT se.experiment_id, e.name AS experiment_name, se.id AS sub_experiment_id, se.sub_experiment_name, " +
            "       COUNT(DISTINCT sep.stu_number) AS not_submit_num " +
            "FROM sub_experiment_progress sep, sub_experiment se, experiment e " +
            "WHERE sep.sub_experiment_id=se.id and se.experiment_id=e.id " +
            "GROUP BY se.id")
    List<SubExperimentReportSummaryVO> listAllReportSummary1();

    @Select("SELECT sub_experiment_id, COUNT(examined = true OR NULL) AS examined_num, COUNT(examined = false OR NULL) AS unexamined_num " +
            "FROM sub_experiment_report_submit " +
            "GROUP BY sub_experiment_id")
    List<SubExperimentReportSummaryVO> listAllReportSummary2();

    //教师
    @Select("SELECT se.experiment_id, e.name AS experiment_name, se.id AS sub_experiment_id, se.sub_experiment_name, " +
            "       COUNT(DISTINCT sep.stu_number) AS not_submit_num " +
            "FROM sub_experiment_progress sep, sub_experiment se, experiment e, course c " +
            "WHERE sep.tea_course_id=c.id AND c.tea_number=#{teaNumber} AND sep.sub_experiment_id=se.id AND se.experiment_id=e.id " +
            "GROUP BY se.id")
    List<SubExperimentReportSummaryVO> listReportSummaryByTeaId1(long teaNumber);
    @Select("SELECT sub_experiment_id, COUNT(examined = true OR NULL) AS examined_num, COUNT(examined = false OR NULL) AS unexamined_num " +
            "FROM sub_experiment_report_submit " +
            "WHERE tea_number=#{teaNumber} " +
            "GROUP BY sub_experiment_id")
    List<SubExperimentReportSummaryVO> listReportSummaryByTeaId2(long teaNumber);


    /////////////////////////////////       实验报告管理-->报告审阅
    /**
     * 联表查询count有问题，故分两步：1从sub_experiment_progress取未提交的数量；2从sub_experiment_report_submit取审核与未审核的数量
     * 注意1中未提交数量需要在implement中进行计算等
     */
    //管理员
    @Select("SELECT se.experiment_id, e.name AS experiment_name, se.id AS sub_experiment_id, se.sub_experiment_name, " +
            "       s.class_id, c.class_name, COUNT(DISTINCT sep.stu_number) AS not_submit_num " +
            "FROM sub_experiment_progress sep, sub_experiment se, experiment e, student s, class_ c " +
            "WHERE sep.sub_experiment_id=#{subExpId} " +
            "       AND sep.sub_experiment_id=se.id AND se.experiment_id=e.id AND sep.stu_number=s.stu_number AND s.class_id=c.id " +
            "GROUP BY s.class_id")
    List<SubExperimentReportSummaryVO> listClassReportSummaryBySubExpId1(long subExpId);

    @Select("SELECT s.class_id, COUNT(examined = true OR NULL) AS examined_num, COUNT(examined = false OR NULL) AS unexamined_num " +
			"FROM sub_experiment_report_submit sers, student s " +
			"WHERE sers.sub_experiment_id=#{subExpId} AND sers.stu_number=s.stu_number " +
			"GROUP BY s.class_id")
    List<SubExperimentReportSummaryVO> listClassReportSummaryBySubExpId2(long subExpId);

    //教师
    @Select("SELECT se.experiment_id, e.name AS experiment_name, se.id AS sub_experiment_id, se.sub_experiment_name, " +
            "       s.class_id, class_.class_name, COUNT(DISTINCT sep.stu_number) AS not_submit_num " +
            "FROM sub_experiment_progress sep, sub_experiment se, experiment e, course c, student s, class_ " +
            "WHERE sub_experiment_id=#{subExpId} AND c.tea_number=#{teaNumber} " +
            "       AND sep.tea_course_id=c.id AND sep.sub_experiment_id=se.id AND se.experiment_id=e.id " +
            "       AND sep.stu_number=s.stu_number AND s.class_id=class_.id " +
            "GROUP BY s.class_id")
    List<SubExperimentReportSummaryVO> listClassReportSummaryByTeaIdAndSubExpId1(long teaNumber, long subExpId);
    @Select("SELECT s.class_id, COUNT(examined = true OR NULL) AS examined_num, COUNT(examined = false OR NULL) AS unexamined_num " +
			"FROM sub_experiment_report_submit sers, student s " +
			"WHERE tea_number=#{teaNumber} AND sers.sub_experiment_id=#{subExpId} AND sers.stu_number=s.stu_number " +
			"GROUP BY s.class_id")
    List<SubExperimentReportSummaryVO> listClassReportSummaryByTeaIdAndSubExpId2(long teaNumber, long subExpId);


//    //////////////////////////////////////////////    实验报告成绩（下载）
//    //管理员
//    @Select("SELECT sers.*,se.sub_experiment_name,s.name as stu_name, s.class_id, c.class_name, t.tea_name " +
//            "FROM sub_experiment_report_submit sers, sub_experiment se, student s, class_ c, teacher t " +
//            "WHERE sers.sub_experiment_id = se.id AND sers.stu_number = s.stu_number AND sers.tea_number = t.tea_number " +
//            "       AND sers.sub_experiment_id=#{subExpId} AND s.class_id=c.id " +
//            "ORDER BY s.class_id, sers.stu_number")
//    List<SubExperimentReportSubmit> listAllExaminedBySubExpId(long SubExpId);
//
//    //教师
//    @Select("SELECT sers.*,se.sub_experiment_name,s.name as stu_name,s.class_id, c.class_name, t.tea_name " +
//            "FROM sub_experiment_report_submit sers, sub_experiment se, student s, class_ c, teacher t " +
//            "WHERE sers.sub_experiment_id = se.id AND sers.stu_number = s.stu_number AND sers.tea_number = t.tea_number " +
//            "       AND sers.sub_experiment_id=#{subExpId} AND sers.tea_number=#{teaNumber} AND s.class_id=c.id " +
//            "ORDER BY s.class_id, sers.stu_number")
//    List<SubExperimentReportSubmit> listExaminedByTeaIdAndSubExpId(long teaNumber, long subExpId);


    //////////////////////////////////////////////    实验报告管理-->报告分析按钮，按子实验号获取分数
    //管理员
    @Select("SELECT sers.*, se.sub_experiment_name, s.name as stu_name, s.class_id, c.class_name, t.tea_name " +
            "FROM sub_experiment_report_submit sers, sub_experiment se, student s, class_ c, teacher t " +
            "WHERE sers.sub_experiment_id = #{subExpId} AND sers.sub_experiment_id = se.id AND sers.stu_number = s.stu_number " +
            "       AND s.class_id=c.id AND sers.tea_number = t.tea_number AND examined=true " +
            "ORDER BY s.class_id, sers.stu_number")
    List<SubExperimentReportSubmit> listAllExaminedBySubExpId(long subExpId);

    //教师
    @Select("SELECT sers.*, se.sub_experiment_name, s.name as stu_name, s.class_id, c.class_name, t.tea_name " +
            "FROM sub_experiment_report_submit sers, sub_experiment se, student s, class_ c, teacher t " +
            "WHERE sers.sub_experiment_id = #{subExpId} AND sers.sub_experiment_id = se.id AND sers.stu_number = s.stu_number " +
            "       AND s.class_id=c.id AND sers.tea_number = t.tea_number AND examined=true AND sers.tea_number=#{teaNumber} " +
            "ORDER BY s.class_id, sers.stu_number")
    List<SubExperimentReportSubmit> listExaminedByTeaIdAndSubExpId(long teaNumber, long subExpId);


    /////////////////////////////////////////////    实验报告管理-->报告审阅-->报告分析按钮(examined=true)  显示该实验下某个特定班级的分数
    //管理员
    @Select("SELECT sers.*, se.sub_experiment_name, s.name as stu_name, s.class_id, c.class_name, t.tea_name " +
            "FROM sub_experiment_report_submit sers, sub_experiment se, student s, class_ c, teacher t " +
            "WHERE sers.sub_experiment_id = #{subExpId} AND s.class_id=#{classId} " +
            "       AND sers.sub_experiment_id = se.id AND sers.stu_number = s.stu_number AND s.class_id=c.id " +
            "       AND sers.tea_number = t.tea_number AND examined=true " +
            "ORDER BY sers.stu_number")
    List<SubExperimentReportSubmit> listAllExaminedBySubExpIdAndClassId(long subExpId, long classId);

    //教师
    @Select("SELECT sers.*, se.sub_experiment_name, s.name as stu_name, s.class_id, c.class_name, t.tea_name " +
            "FROM sub_experiment_report_submit sers, sub_experiment se, student s, class_ c, teacher t " +
            "WHERE sers.tea_number=#{teaNumber} AND sers.sub_experiment_id = #{subExpId} AND s.class_id=#{classId} " +
            "       AND sers.sub_experiment_id = se.id AND sers.stu_number = s.stu_number AND s.class_id=c.id " +
            "       AND sers.tea_number = t.tea_number AND examined=true " +
            "ORDER BY sers.stu_number")
    List<SubExperimentReportSubmit> listExaminedByTeaIdAndSubExpIdAndClassId(long teaNumber, long subExpId, long classId);


    //////////////////////////////////////////         实验报告管理-->报告审阅-->班级内学生报告列表(所有的，不管examined true和false)
	//管理员
	@Select("SELECT sers.*, se.sub_experiment_name, s.name as stu_name, s.class_id, c.class_name, t.tea_name " +
			"FROM sub_experiment_report_submit sers, sub_experiment se, student s, class_ c, teacher t " +
			"WHERE sers.sub_experiment_id = #{subExpId} AND s.class_id=#{classId} " +
			"       AND sers.sub_experiment_id = se.id AND sers.stu_number = s.stu_number AND s.class_id=c.id " +
			"       AND sers.tea_number = t.tea_number " +
			"ORDER BY sers.stu_number")
	List<SubExperimentReportSubmit> listAllBySubExpIdAndClassId(long subExpId, long classId);

	//教师
	@Select("SELECT sers.*, se.sub_experiment_name, s.name as stu_name, s.class_id, c.class_name, t.tea_name " +
			"FROM sub_experiment_report_submit sers, sub_experiment se, student s, class_ c, teacher t " +
			"WHERE sers.tea_number=#{teaNumber} AND sers.sub_experiment_id = #{subExpId} AND s.class_id=#{classId} " +
			"       AND sers.sub_experiment_id = se.id AND sers.stu_number = s.stu_number AND s.class_id=c.id " +
			"       AND sers.tea_number = t.tea_number " +
			"ORDER BY sers.stu_number")
	List<SubExperimentReportSubmit> listByTeaIdAndSubExpIdAndClassId(long teaNumber, long subExpId, long classId);


    //////////////////////////////////////////     获取该子实验该班级的下一个学生的报告，只能批阅自己教授的学生的报告
	@Select("SELECT rs.*, se.sub_experiment_name, s.name AS stu_name, s.class_id, c.class_name, t.tea_name " +
			"FROM sub_experiment_report_submit rs, sub_experiment se, student s, class_ c, teacher t " +
			"WHERE rs.id>#{reportId} AND rs.tea_number=#{teaNumber} AND rs.sub_experiment_id=#{subExpId} AND s.class_id=#{classId} " +
			"		AND rs.examined=false " +
			"ORDER BY rs.id LIMIT 1")
	SubExperimentReportSubmit getNextReportByTeaIdAndSubExpIdAndClassId(long reportId, long teaNumber, long subExpId, long classId);



}

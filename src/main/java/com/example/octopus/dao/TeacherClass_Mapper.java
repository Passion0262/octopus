package com.example.octopus.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/8 3:00 下午
 */
@Mapper
public interface TeacherClass_Mapper {

    /**
     * 根据教师编号返回所有该教师教授的课程id list
     * @param teaNumber 老师编号
     * @return 课程id list
     */
    @Select("SELECT class_id FROM teacher_class_ WHERE tea_number = #{teaNumber}")
    List<Long> listClass_idsByTeaNumber(long teaNumber);

    /**
     * 根据课程编号返回教授该课程的教师id list
     * @param classId 课程id
     * @return 教师id list
     */
    @Select("SELECT tea_number FROM teacher_class_ WHERE class_id = #{classId}")
    List<Long> listTeaNumbersByClass_Id(long classId);

}

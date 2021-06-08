package com.example.octopus.dao;

import com.example.octopus.entity.user.Teacher;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/5 8:12 下午
 */
@Mapper
public interface TeacherMapper {

    /**
     * 查找所有的老师
     * @return teacher list
     */
    @Select("SELECT * FROM teacher")
    List<Teacher> listTeachers();

    /**
     * 根据teaNumber查找教师
     * @param teaNumber 老师编号
     * @return teacher实体
     */
    @Select("SELECT * FROM teacher WHERE tea_number = #{teaNumber}")
    Teacher getByTeaNumber(long teaNumber);

}

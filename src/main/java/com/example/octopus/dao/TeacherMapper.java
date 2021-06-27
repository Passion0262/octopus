package com.example.octopus.dao;

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

    @Select("SELECT * FROM teacher")
    List<Teacher> getAllTeachers();

    @Update("UPDATE teacher SET name = #{name},major_code = #{majorCode},admin_rights = #{adminRights},phone = #{phone},school = #{school},login_number = #{loginNumber}, last_login_time = #{lastLoginTime}" +
            "WHERE tea_number = #{teaNumber}")
    boolean updateTeacher(Teacher teacher);

    @Insert("INSERT INTO teacher (tea_number,name,major_code,admin_rights,phone,school,login_number,last_login_time) " +
            "VALUES (#{teaNumber},#{name},#{majorCode},#{adminRights},#{phone},#{school},#{loginNumber},#{lastLoginTime})")
    boolean addTeacher(Teacher teacher);

    @Delete("DELETE FROM teacher WHERE tea_number=#{teaNumber}")
    boolean deleteTeacher(long teaNumber);

    @Select("SELECT phone FROM teacher WHERE tea_number=#{teaNumber}")
    String getPhoneByTeaNumber(long teaNumber);
}

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
     * 根据teaNumber查找教师
     * @param teaNumber 老师编号
     * @return teacher实体
     */
    @Select("SELECT t.*, major_name FROM teacher t, major m WHERE tea_number = #{teaNumber} and t.major_id=m.id")
    Teacher getByTeaNumber(long teaNumber);

    @Select("SELECT t.*, major_name FROM teacher t, major m WHERE t.major_id=m.id")
    List<Teacher> getAllTeachers();

    @Update("UPDATE teacher SET name = #{name},major_id = #{majorId},admin_rights = #{adminRights},phone = #{phone},school = #{school},login_number = #{loginNumber}, last_login_time = #{lastLoginTime}" +
            "WHERE tea_number = #{teaNumber}")
    boolean updateTeacher(Teacher teacher);

    @Insert("INSERT INTO teacher (tea_number,name,major_id,admin_rights,phone,school,login_number,last_login_time) " +
            "VALUES (#{teaNumber},#{name},#{majorId},#{adminRights},#{phone},#{school},#{loginNumber},#{lastLoginTime})")
    boolean addTeacher(Teacher teacher);

    @Delete("DELETE FROM teacher WHERE tea_number=#{teaNumber}")
    boolean deleteTeacher(long teaNumber);

    @Select("SELECT phone FROM teacher WHERE tea_number=#{teaNumber}")
    String getPhoneByTeaNumber(long teaNumber);
}

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

    @Update("UPDATE teacher SET tea_name = #{teaName},major_id = #{majorId},admin_rights = #{adminRights},phone = #{phone},school = #{school},login_number = #{loginNumber}, last_login_time = #{lastLoginTime} " +
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
}

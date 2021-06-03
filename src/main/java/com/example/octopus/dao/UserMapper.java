package com.example.octopus.dao;

import com.example.octopus.entity.user.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/28 3:33 下午
 * @modified By：
 */
@Mapper
public interface UserMapper {
    @Select("SELECT * FROM student")
    List<Student> listAllStudents();

    @Select("SELECT * FROM student WHERE stuNumber = #{stuNumber}")
    Student getStudentByStuNumber(long stuNumber);

    @Select("SELECT * FROM student WHERE name = #{name}")
    Student getStudentByName(String name);

    @Select("SELECT * FROM student WHERE stuNumber = #{stuNumber} and password = #{password}")
    Student getStudentByStuNumberAndPassword(long stuNumber, String password);

    @Update("UPDATE student SET loginNumber = loginNumber+1, lastLoginTime = CURRENT_TIMESTAMP where stuNumber = #{stuNumber}")
    boolean updateLoginInfoByStuNumber(long stuNumber);

    @Update("UPDATE student SET stuNumber = '000000' WHERE stuNumber = #{stuNumber}}")
    boolean resetPassword(long stuNumber);

    @Update("UPDATE student SET phoneNumber = #{phoneNumber} WHERE stuNumber = #{stuNumber}")
    boolean changePhone(long stuNumber, String phoneNumber);

}

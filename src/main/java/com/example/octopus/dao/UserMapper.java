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
    List<Student> queryAllStudents();

    @Select("SELECT * FROM student WHERE stuNumber = #{stuNumber}")
    Student queryStudentByStuNumber(String stuNumber);

    @Select("SELECT * FROM student WHERE name = #{name}")
    Student queryStudentByName(String name);

    @Select("SELECT * FROM student WHERE stuNumber = #{stuNumber} and password = #{password}")
    Student queryStudentByStuNumberAndPassword(String stuNumber, String password);

    @Update("UPDATE student SET loginNumber = loginNumber+1, lastLoginTime = CURRENT_TIMESTAMP where stuNumber = #{stuNumber}")
    boolean updateLoginInfoByStuNumber(String stuNumber);

    @Update("UPDATE student SET stuNumber = '000000' WHERE stuNumber = #{stuNumber}}")
    boolean resetPassword(String stuNumber);

    @Update("UPDATE student SET phoneNumber = #{phoneNumber} WHERE stuNumber = #{stuNumber}")
    boolean changePhone(String stuNumber, String phoneNumber);

}

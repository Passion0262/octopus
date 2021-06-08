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

    @Select("SELECT * FROM student WHERE stu_number = #{stuNumber}")
    Student getStudentByStuNumber(long stuNumber);

    @Select("SELECT * FROM student WHERE name = #{name}")
    Student getStudentByName(String name);

    @Select("SELECT * FROM student WHERE stu_number = #{stuNumber} and password = #{password}")
    Student getStudentByStuNumberAndPassword(long stuNumber, String password);

    @Update("UPDATE student SET login_number = login_number+1, last_login_time = CURRENT_TIMESTAMP where stu_number = #{stuNumber}")
    boolean updateLoginInfoByStuNumber(long stuNumber);

    @Update("UPDATE student SET stu_number = '000000' WHERE stu_number = #{stuNumber}}")
    boolean resetPassword(long stuNumber);

    @Update("UPDATE student SET phone_number = #{phoneNumber} WHERE stu_number = #{stuNumber}")
    boolean updatePhoneByStuNumber(long stuNumber, String phoneNumber);

}

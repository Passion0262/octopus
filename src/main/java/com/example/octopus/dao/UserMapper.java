package com.example.octopus.dao;

import com.example.octopus.entity.user.Student;
import org.apache.ibatis.annotations.*;

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

    @Select("SELECT * FROM student WHERE class_name = #{className}")
    List<Student> listByClassName(String className);

    @Select("SELECT * FROM student WHERE stu_number = #{stuNumber}")
    Student getStudentByStuNumber(long stuNumber);

    @Select("SELECT * FROM student WHERE name = #{name}")
    Student getStudentByName(String name);

//    @Select("SELECT * FROM student WHERE stu_number = #{stuNumber} and password = #{password}")
//    Student getStudentByStuNumberAndPassword(long stuNumber, String password);

    //@Insert("INSERT INTO student (stu_number,name,password,major,class_name,phone_number,login_number,last_login_time,study_time) VALUES (#{stuNumber},#{name},#{password},#{major},#{className},#{phoneNumber},0,CURRENT_TIMESTAMP,0)")
    @Insert("INSERT INTO student (stu_number,name,major,class_name,phone_number,login_number,last_login_time,study_time) VALUES (#{stuNumber},#{name},#{major},#{className},#{phoneNumber},0,CURRENT_TIMESTAMP,0)")
    boolean insertStudent(Student student);

    //@Update("UPDATE student SET name=#{name},password=#{password},major_id=#{majorId},class_id=#{classId},phone_number=#{phoneNumber},login_number=#{loginNumber},last_login_time=#{lastLoginTime},study_time=#{studyTime} WHERE stu_number=#{stuNumber}")
    @Update("UPDATE student SET name=#{name},major_id=#{majorId},class_id=#{classId},phone_number=#{phoneNumber},login_number=#{loginNumber},last_login_time=#{lastLoginTime},study_time=#{studyTime} WHERE stu_number=#{stuNumber}")
    boolean updateStudent(Student student);

    @Update("UPDATE student SET login_number = login_number+1, last_login_time = CURRENT_TIMESTAMP where stu_number = #{stuNumber}")
    boolean updateLoginInfoByStuNumber(long stuNumber);

//    @Update("UPDATE student SET stu_number = '000000' WHERE stu_number = #{stuNumber}}")
//    boolean resetPassword(long stuNumber);

    @Update("UPDATE student SET phone_number = #{phoneNumber} WHERE stu_number = #{stuNumber}")
    boolean updatePhoneByStuNumber(long stuNumber, String phoneNumber);

    @Delete("DELETE FROM student WHERE stu_number=#{stuNumber}")
    boolean deleteByStuNumber(long stuNumber);

}

package com.example.octopus.dao;

import com.example.octopus.entity.user.Student;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/28 3:33 下午
 * @modified By：
 */

@Component
@Mapper
public interface UserMapper {
    @Select("SELECT s.*, major_name, class_name FROM student s, major, class_ WHERE s.major_id=major.id and s.class_id=class_.id")
    List<Student> listAllStudents();

    /**
     * 显示选了这位老师教的课的所有学生
     */
    @Select("SELECT DISTINCT s.*, major_name, class_name FROM student s, major, class_, course, student_course sc " +
            "WHERE s.major_id=major.id and s.class_id=class_.id " +
            "and course.tea_number=#{teaNumber} and course.id=sc.course_id and sc.stu_number=s.stu_number")
    List<Student> listStudentsByTeaNum(long teaNumber);

    @Select("SELECT s.*, major_name, class_name FROM student s, major, class_ WHERE s.class_name = #{className} and s.major_id=major.id and s.class_id=class_.id")
    List<Student> listByClassName(String className);

    @Select("SELECT  s.*, major_name, class_name FROM student s, major, class_ WHERE stu_number = #{stuNumber} and s.major_id=major.id and s.class_id=class_.id")
    Student getStudentByStuNumber(long stuNumber);

    @Select("SELECT s.*, major_name, class_name FROM student s, major, class_ WHERE name = #{name} and s.major_id=major.id and s.class_id=class_.id")
    Student getStudentByName(String name);

    @Insert("INSERT INTO student (stu_number,name,major_id,class_id,school,phone_number,login_number,last_login_time,study_time) VALUES (#{stuNumber},#{name},#{majorId},#{classId},#{school},#{phoneNumber},0,CURRENT_TIMESTAMP,0)")
    boolean insertStudent(Student student);

    @Update("UPDATE student SET name=#{name},major_id=#{majorId},class_id=#{classId},school=#{school}, phone_number=#{phoneNumber} WHERE stu_number=#{stuNumber}")
    boolean updateStudent(Student student);

//    @Update("UPDATE student SET login_number = login_number+1, last_login_time = CURRENT_TIMESTAMP WHERE stu_number = #{stuNumber}")
    @Update("UPDATE student SET login_number = login_number+1, last_login_time = this_login_time, this_login_time = CURRENT_TIMESTAMP WHERE stu_number = #{stuNumber}")
    boolean updateLoginInfoByStuNumber(long stuNumber);

    @Update("UPDATE student SET phone_number = #{phoneNumber} WHERE stu_number = #{stuNumber}")
    boolean updatePhoneByStuNumber(long stuNumber, String phoneNumber);

    @Delete("DELETE FROM student WHERE stu_number=#{stuNumber}")
    boolean deleteByStuNumber(long stuNumber);

    @Select("SELECT phone_number FROM student WHERE stu_number=#{stuNumber}")
    String getPhoneByStuNumber(long stuNumber);

    @Select("SELECT school FROM student WHERE stu_number=#{stuNumber}")
    String getSchoolById(long stuNumber);

    @Update("update student set major_id =100 where stu_number=1")
    boolean updateMajorId();


    //////////////////////批量操作

    @InsertProvider(type = Provider.class, method = "batchInsert")
    boolean batchInsert(List<Student> students);

    class Provider {
        /* 批量插入 */
        public String batchInsert(Map map) {
            List<Student> students = (List<Student>) map.get("list");
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO student (stu_number,name,major_id,class_id,school,phone_number,login_number,last_login_time,study_time) VALUES ");
            MessageFormat mf = new MessageFormat(
                    "(#'{'list[{0}].stuNumber},#'{'list[{0}].name},#'{'list[{0}].majorId},#'{'list[{0}].classId},#'{'list[{0}].school},#'{'list[{0}].phoneNumber},0,CURRENT_TIMESTAMP,0)"
            );

            for (int i = 0; i < students.size(); i++) {
                sb.append(mf.format(new Object[] {i}));
                if (i < students.size() - 1)
                    sb.append(",");
            }
            return sb.toString();
        }

    }

}

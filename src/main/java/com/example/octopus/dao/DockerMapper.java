package com.example.octopus.dao;

import com.example.octopus.entity.user.Docker;
import com.example.octopus.entity.user.Student;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * @author: Hao
 * @date: 2021/6/7 15:45
 */
@Mapper
public interface DockerMapper {
    ////////////
    @Select("SELECT * FROM docker")
    List<Docker> listAllDockers();

    @Select("SELECT d.* FROM docker d, student_course sc, course c " +
            "WHERE c.tea_number=#{teaNumber} and d.stu_number=sc.stu_number and sc.course_id=c.id")
    List<Docker> listDockersByTeaId(long teaNumber);


    ////////////////
    @Select("SELECT * FROM docker WHERE stu_number = #{stuNumber}")
    Docker getDockerByStuNum(long stuNumber);

    @Select("SELECT * FROM docker WHERE docker_id = #{id}")
    Docker getDockerById(int id);


    /////////////
    @Select("SELECT port FROM docker")
    List<Integer> getAllPorts();

    @Select("SELECT name FROM docker")
    List<String> getAllNames();


    ///////////////////
    @Select("SELECT * FROM docker WHERE available=true ORDER BY version DESC, create_time")
    List<Docker> listAvailableDocker();

    @Select("SELECT * FROM docker WHERE available=false AND TIMESTAMPDIFF(MINUTE, last_time, CURRENT_TIMESTAMP)>30")
    List<Docker> listResetNeedingDocker();


    /////////////
    @Update("INSERT INTO docker (id, name, version, port, address, create_time) " +
            "VALUES (#{id}, #{name}, #{version}, #{port}, #{address}, CURRENT_TIMESTAMP)")
    boolean createDocker(Docker docker);

    @Update("UPDATE docker SET available=false, stu_number=#{stuNumber}, start_time=CURRENT_TIMESTAMP, last_time=CURRENT_TIMESTAMP, status = #{status}, processing_id=#{processingId}, " +
            "       stu_name=(SELECT name FROM student WHERE stu_number=#{stuNumber}) " +
            "WHERE id=#{id}")
    boolean updateStatusByStuNum(Docker docker);

    @Update("UPDATE docker SET last_time=CURRENT_TIMESTAMP WHERE stu_number=#{stuNumber}")
    boolean updateLastTimeByStuNum(long stuNumber);

    /**
     * 此项用于检查当前数据库中的pod最新版本
     */
    @Select("SELECT version FROM docker ORDER BY version DESC LIMIT 1")
    String checkDBLatestVersion();

    @Update("DELETE FROM docker WHERE id=#{id}")
    boolean deleteDocker(int id);

    @Select("SELECT * FROM docker WHERE available=true")
    List<Docker> listDockerNeededUpgrade();

    //////////////////////////批量删除与增加
    @InsertProvider(type = Provider.class, method = "batchInsert")
    boolean batchInsert(List<Docker> dockers);

    @DeleteProvider(type = Provider.class, method = "batchDelete")
    boolean batchDelete(List<Docker> dockers);

    class Provider {
        /* 批量插入 */
        public String batchInsert(Map map) {
            List<Docker> dockers = (List<Docker>) map.get("list");
            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO docker (id, name, version, port, address, create_time) VALUES ");
            MessageFormat mf = new MessageFormat(
                    "(#'{'list[{0}].id},#'{'list[{0}].name},#'{'list[{0}].version},#'{'list[{0}].port},#'{'list[{0}].address}, CURRENT_TIMESTAMP)"
            );

            for (int i = 0; i < dockers.size(); i++) {
                sb.append(mf.format(new Object[] {i}));
                if (i < dockers.size() - 1)
                    sb.append(",");
            }
            return sb.toString();
        }

        /* 批量插入 */
        public String batchDelete(Map map) {
            List<Docker> dockers = (List<Docker>) map.get("list");
            StringBuilder sb = new StringBuilder();
            sb.append("DELETE FROM docker WHERE id= ");
            MessageFormat mf = new MessageFormat(
                    "(#'{'list[{0}].id})"
            );

            for (int i = 0; i < dockers.size(); i++) {
                sb.append(mf.format(new Object[] {i}));
                if (i < dockers.size() - 1)
                    sb.append(",");
            }
            return sb.toString();
        }

    }



    ////////// 按运行中与睡眠中查询
    @Select("SELECT * FROM docker WHERE status not like 'sleeping'")
    List<Docker> listAllAwakeDocker();

    @Select("SELECT d.* FROM docker d, student_course sc, course c " +
            "WHERE status not like 'sleeping' and c.tea_number=#{teaNumber} and d.stu_number=sc.stu_number and sc.course_id=c.id")
    List<Docker> listAwakeDockerByTeaID(long teaNumber);

    @Select("SELECT * FROM docker WHERE status='sleeping'")
    List<Docker> listAllSleepDocker();

    @Select("SELECT d.* FROM docker d, student_course sc, course c " +
            "WHERE status='sleeping' and c.tea_number=#{teaNumber} and d.stu_number=sc.stu_number and sc.course_id=c.id")
    List<Docker> listSleepDockerByTeaID(long teaNumber);

}

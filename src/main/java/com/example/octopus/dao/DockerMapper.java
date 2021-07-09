package com.example.octopus.dao;

import com.example.octopus.entity.Docker;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;
import java.util.List;

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
    Docker getDockerById(long id);


    /////////////
    @Select("SELECT docker_port FROM docker")
    List<String> getAllPorts();

    @Select("SELECT docker_name FROM docker")
    List<String> getAllNames();


    /////////////
    @Update("INSERT INTO docker (docker_name, docker_port, stu_number, stu_name, docker_address) " +
            "VALUES (#{name}, #{port}, #{stuNumber}, #{stuName}, #{address})")
    boolean createDocker(String name, int port, long stuNumber, String stuName, String address);

    @Update("UPDATE docker SET docker_status = #{status}, processing_id=#{processingId} WHERE stu_number = #{stuNumber}")
    boolean updateStatusByStuNum(long stuNumber, String status, long processingId);


    ////////// 按运行中与睡眠中查询
    @Select("SELECT * FROM docker WHERE docker_status not like 'sleeping'")
    List<Docker> listAllAwakeDocker();

    @Select("SELECT d.* FROM docker d, student_course sc, course c " +
            "WHERE docker_status not like 'sleeping' and c.tea_number=#{teaNumber} and d.stu_number=sc.stu_number and sc.course_id=c.id")
    List<Docker> listAwakeDockerByTeaID(long teaNumber);

    @Select("SELECT * FROM docker WHERE docker_status='sleeping'")
    List<Docker> listAllSleepDocker();

    @Select("SELECT d.* FROM docker d, student_course sc, course c " +
            "WHERE docker_status='sleeping' and c.tea_number=#{teaNumber} and d.stu_number=sc.stu_number and sc.course_id=c.id")
    List<Docker> listSleepDockerByTeaID(long teaNumber);

}

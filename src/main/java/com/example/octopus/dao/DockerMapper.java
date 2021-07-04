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



}

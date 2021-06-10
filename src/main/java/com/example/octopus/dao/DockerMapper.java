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

    @Select("SELECT * FROM docker WHERE userNumber = #{stuNumber}")
    Docker getDockerByStuNum(long stuNumber);

    @Select("SELECT * FROM docker WHERE id = #{id}")
    Docker getDockerById(long id);


    /////////////
    @Select("SELECT port FROM docker")
    List<String> getAllPorts();

    @Select("SELECT name FROM docker")
    List<String> getAllNames();


    /////////////
    @Update("INSERT INTO docker (name, port, userNumber, userName, address) " +
            "VALUES (#{name}, #{port}, #{stuNumber}, #{stuName}, #{address})")
    boolean createDocker(String name, int port, long stuNumber, String stuName, String address);

    @Update("UPDATE docker SET status = #{status} WHERE userNumber = #{stuNumber}")
    boolean updateStatusByStuNum(long stuNumber, String status);



}

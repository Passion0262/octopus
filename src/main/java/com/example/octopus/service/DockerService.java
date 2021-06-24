package com.example.octopus.service;

import com.example.octopus.entity.Docker;

import java.util.List;

/**
 * @author: Hao
 * @date: 2021/6/7 14:46
 * 容器表服务
 */

public interface DockerService {
    /**
     * 查库前必调，查看该学生是否有虚拟机
     *
     * @param stuNumber 学生号
     * @return 返回true则有，可以进行后续的查库操作。
     * 返回false则不能继续进行，否则查库会报错
     */
    public boolean existsStuNumDocker(long stuNumber);

    public boolean existsIdDocker(long id);


    /**
     * 获取所有docker信息
     *
     * @return docker信息列表
     */
    public List<Docker> getDockerList();


    /**
     * 通过docker id获取docker信息
     *
     * @param id docker的id
     * @return 该docker信息
     */
    public Docker findDockerById(long id);

    /**
     * 通过学生号检索docker信息
     *
     * @param stuNumber 学生id
     * @return 该docker信息
     */
    public Docker findDockerByStuNumber(long stuNumber);


    /**
     * 通过学生号获取docker的完整ip地址
     *
     * @param stuNumber 学生号
     * @return 完整ip地址，如 http://172.18.146.123:6081/#/
     */
    public String getAddressByStuNumber(long stuNumber);

    /**
     * 通过学生号获取其虚拟机状况（未在使用、在使用、无虚拟机）
     *
     * @param stuNumber 学生号
     * @return 返回虚拟机容器状况
     */
    public String getStatusByStuNumber(long stuNumber);

    /**
     * 创建新的docker
     *
     * @param stuNumber 学生号
     * @return 成功则返回true
     */
    public boolean createNewDocker(long stuNumber);


}

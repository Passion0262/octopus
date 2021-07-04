package com.example.octopus.service;

import com.example.octopus.entity.Docker;

import java.util.List;
import java.util.Map;

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
	 * @return 返回true则有，可以进行后续的查库操作；返回false则不能继续进行，否则查库会报错
	 */
	boolean existsStuNumDocker(long stuNumber);

	/**
	 * 通过学生号获取docker状态，包含sleeping、project、experiment三种情况及相对应的processingId
	 *
	 * @param stuNumber 学生号
	 * @return String[0]为状态status，String[1]为processingId，注意需要转换为long型
	 */
	String[] getStatusByStuNum(long stuNumber);

	/**
	 * 通过学生号更新docker状态，包含sleeping、project、experiment三种情况
	 *
	 * @param stuNumber    学生号
	 * @param statusCode   sleeping:0、project:1、experiment:2
	 * @param processingId 正在进行的project或experiment的id；如为sleeping，则设置为0
	 * @return 更新成功与否
	 */
	boolean updateStatusByStuNum(long stuNumber, int statusCode, long processingId);

	/**
	 * 获取所有docker信息
	 *
	 * @return docker信息列表
	 */
	List<Docker> getDockerList();


	/**
	 * 通过docker id获取docker信息
	 *
	 * @param id docker的id
	 * @return 该docker信息
	 */
	Docker findDockerById(long id);

	/**
	 * 通过学生号检索docker信息
	 *
	 * @param stuNumber 学生id
	 * @return 该docker信息
	 */
	Docker findDockerByStuNumber(long stuNumber);


	/**
	 * 通过学生号获取docker的完整ip地址
	 *
	 * @param stuNumber 学生号
	 * @return 完整ip地址，如 http://172.18.146.123:6081/#/
	 */
	String getAddressByStuNumber(long stuNumber);


	/**
	 * 创建新的docker
	 *
	 * @param stuNumber 学生号
	 * @return 成功则返回true
	 */
	boolean createNewDocker(long stuNumber);


}

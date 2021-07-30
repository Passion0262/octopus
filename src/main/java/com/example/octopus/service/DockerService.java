package com.example.octopus.service;

import com.example.octopus.entity.user.Docker;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author: Hao
 * @date: 2021/6/7 14:46
 * 容器表服务
 */

public interface DockerService {

	/**
	 * 根据角色获取全部或自己学生的docker信息
	 */
	List<Docker> listDockerByRole(long teaNumber);


	/**
	 * 通过学生号检索docker信息，可能为空！！！
	 *
	 * @param stuNumber 学生id
	 * @return 该docker信息
	 */
	Docker getDockerByStuNumber(long stuNumber);


	/**
	 * 前台使用
	 * 学生申请pod，为其分配一个，返回此pod地址；如无没有可用pod，则返回string提示要求等待
	 *
	 * @param stuNumber 学生号
	 * @param statusCode   sleeping:0、project:1、experiment:2
	 * @param processingId 正在进行的project或experiment的id；如为sleeping，则设置为0
	 * @return 完整ip地址  如暂时没有，则返回null
	 */
	String getPodForStu(long stuNumber, int statusCode, long processingId);

	/**
	 * 前台使用
	 * 释放pod
	 */
	boolean resetPod(long stuNumber);

	/**
	 * 前台使用
	 * 通过当前时间与DB中的lastTime做差，对超过半小时则对此pod做reset，更新到数据库中的最新版本
	 */
	boolean checkTimeReset();

	/**
	 * 前台使用
	 * 前台每x分钟调用，更新仍在使用，便于通过时间进行重置
	 * @return true 修改成功，false 学生暂无pod
	 */
	boolean updateLastTimeByStuNum(long stuNumber);


	/**
	 * 后台使用 仅限管理员
	 * 检查pod是否有版本更新
	 * @return 如有更新，则返回最新版本号（如v1）；如无则返回null
	 */
	String checkUpgrade();

	/**
	 * 后台使用 仅限管理员
	 * 如通过上条方法检索到新版本，则对空闲pod进行释放与更新
	 * @return
	 */
	boolean upgradePods(String latestVersion);



	/**
	 * 后台使用
	 * 通过教师身份查询运行中或睡眠中的docker
	 *
	 * @param teaNumber 教师号
	 * @param awaken    true则查询运行中docker，false则查询睡眠中docker
	 */
	List<Docker> listDockerByRoleAndAwake(long teaNumber, boolean awaken);

	/**
	 * 后台使用
	 * 教师管理员登录首页，实验机状态比例显示
	 * @return sleeping:0、project:1、experiment:2
	 */
	int[] countDockerByStatus(long teaNumber);


}

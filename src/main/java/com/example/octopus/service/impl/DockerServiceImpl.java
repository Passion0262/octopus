package com.example.octopus.service.impl;

import com.example.octopus.dao.DockerMapper;
import com.example.octopus.dao.SysUserRoleMapper;
import com.example.octopus.entity.user.Docker;
import com.example.octopus.service.DockerService;
import com.example.octopus.utils.ShellUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

/**
 * @author: Hao
 * @date: 2021/6/7 15:43
 */

@Service
public class DockerServiceImpl implements DockerService {

	private static final int[] FORBIDDEN_PORTS = {8080, 3306};

	private static final ShellUtils SHELL_UTILS = new ShellUtils();

	@Autowired
	private DockerMapper dockerMapper;

	@Autowired
	SysUserRoleMapper sysUserRoleMapper;


	@Override
	public List<Docker> listDockerByRole(long teaNumber) {
		long role = sysUserRoleMapper.getRoleByUserId(teaNumber);
		if (role == 1) {
			//管理员获取全部
			return dockerMapper.listAllDockers();
		} else return dockerMapper.listDockersByTeaId(teaNumber);
	}

	@Override
	public Docker getDockerByStuNumber(long stuNumber) {
		return dockerMapper.getDockerByStuNum(stuNumber);
	}

	@Override
	public String getPodForStu(long stuNumber, int statusCode, long processingId) {
		List<Docker> available = dockerMapper.listAvailableDocker();

		//当前没有可用虚拟机，需要稍后刷新
		if (available.isEmpty()){
			System.out.println("========================= no available pod, please wait a sec to refresh....===========================");
			return null;
		}

		String dockerStatus = null;
		if (statusCode == 0) {
			dockerStatus = "sleeping";
			processingId = 0;
		} else if (statusCode == 1) dockerStatus = "project";
		else if (statusCode == 2) dockerStatus = "experiment";
		available.get(0).setProcessingId(processingId);
		available.get(0).setStuNumber(stuNumber);
		available.get(0).setStatus(dockerStatus);

		dockerMapper.updateStatusByStuNum(available.get(0));
		return available.get(0).getAddress();
	}

	@Override
	@Transactional
	public boolean resetPod(long stuNumber){
		try{
			Docker docker = dockerMapper.getDockerByStuNum(stuNumber);
			dockerMapper.deleteDocker(docker.getId());

			//该docker版本需要更新
			String latestVersionInDB = dockerMapper.checkDBLatestVersion();
			System.out.println(latestVersionInDB);
			if(!docker.getVersion().equals(latestVersionInDB))
				docker.upgrade(latestVersionInDB);

			docker.generate(SHELL_UTILS.getAddress());
			SHELL_UTILS.resetPod(docker.getId(), docker.getPort(), docker.getVersion());
			dockerMapper.createDocker(docker);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String checkUpgrade(){
		String dbLatestVersion = dockerMapper.checkDBLatestVersion().toLowerCase(Locale.ROOT);
		String latestVersion = SHELL_UTILS.latestVersion().toLowerCase(Locale.ROOT);

		if(latestVersion.equals(dbLatestVersion)){
			System.out.print("using the latest version, no need to upgrade\n");
			return null;
		}
		else return latestVersion;
	}

	@Override
	@Transactional
	public boolean upgradePods(String latestVersion){
		try{
			List<Docker> ds = dockerMapper.listDockerNeededUpgrade();
			dockerMapper.batchDelete(ds);
			SHELL_UTILS.upgradePods(ds, latestVersion);
			dockerMapper.batchInsert(ds);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}

	}

//	@Override
//	public boolean updateDockerStatusByStuNum(long stuNumber) {
//		String status = null;
//		if (statusCode == 0) {
//			status = "sleeping";
//			processingId = 0;
//		} else if (statusCode == 1) status = "project";
//		else if (statusCode == 2) status = "experiment";
//		else return false;
//		return dockerMapper.updateStatusByStuNum(stuNumber, status, processingId);
//	}


//	@Override
//	public boolean createNewDocker(long stuNumber) {
//
//		if (existsStuNumDocker(stuNumber)) {
//			System.out.println("该学生已有docker容器了，不需要再创建");
//			return false;
//		}
//
//		List<String> ports = dockerMapper.getAllPorts();  //获取所有端口
//
//		String stuName = userService.getStudentByStuNumber(stuNumber).getName();
//		String dockerName = String.valueOf(stuNumber);
//		int dockerPort = Integer.parseInt(Collections.max(ports)) + 1;
//		for (int i = 0; i < FORBIDDEN_PORTS.length; i++) {
//			if (dockerPort == FORBIDDEN_PORTS[i]) {
//				dockerPort++;
//				i--;
//			}
//		}
//
//		String dockerCommand = "docker run -idt -p " + dockerPort + "-e USER=test -e PASSWORD=123 --name "
//				+ dockerName + "  centminmod/docker-ubuntu-vnc-desktop";
//		SHELL_UTILS.sshRemoteCallLogin();
//		SHELL_UTILS.executeCommand(dockerCommand);
//		SHELL_UTILS.closeSession();
//
//
//		String address = "http://172.18.146.123:" + String.valueOf(dockerPort) + "/#/";
//		try {
//			dockerMapper.createDocker(dockerName, dockerPort, stuNumber, stuName, address);
//			return true;
//		} catch (Exception e) {
//			throw e;
//		}
//
//	}

	@Override
	public List<Docker> listDockerByRoleAndAwake(long teaNumber, boolean awaken) {
		long role = sysUserRoleMapper.getRoleByUserId(teaNumber);
		if (role == 1) {
			//管理员获取全部
			if (awaken) return dockerMapper.listAllAwakeDocker();
			else return dockerMapper.listAllSleepDocker();
		} else {
			if (awaken) return dockerMapper.listAwakeDockerByTeaID(teaNumber);
			else return dockerMapper.listSleepDockerByTeaID(teaNumber);
		}
	}

	@Override
	public int[] countDockerByStatus(long teaNumber) {
		long role = sysUserRoleMapper.getRoleByUserId(teaNumber);
		int[] result = {0, 0, 0};
		int size;
		String status;
		List<Docker> d;

		if (role == 1) d = dockerMapper.listAllDockers();
		else d = dockerMapper.listDockersByTeaId(teaNumber);

		size = d.size();
		for (int i = 0; i < size; i++) {
			status = d.get(i).getStatus();
			if (status.equals("experiment")) ++result[2];
			else if (status.equals("project")) ++result[1];
			else ++result[0];
		}
		return result;
	}
}

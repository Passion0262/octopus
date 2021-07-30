package com.example.octopus.service.impl;

import com.example.octopus.dao.DockerMapper;
import com.example.octopus.dao.SysUserRoleMapper;
import com.example.octopus.entity.user.Docker;
import com.example.octopus.service.DockerService;
import com.example.octopus.utils.ShellUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

/**
 * @author: Hao
 * @date: 2021/6/7 15:43
 */

@Service
public class DockerServiceImpl implements DockerService {

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
//		List<Docker> available = dockerMapper.listAvailableDocker();
//
//		//当前没有可用虚拟机，需要稍后刷新
//		if (available.isEmpty()){
//			System.out.println("========================= no available pod, please wait a sec to refresh....===========================");
//			return null;
//		}
//
//		String dockerStatus = null;
//		if (statusCode == 0) {
//			dockerStatus = "sleeping";
//			processingId = 0;
//		} else if (statusCode == 1) dockerStatus = "project";
//		else if (statusCode == 2) dockerStatus = "experiment";
//		available.get(0).setProcessingId(processingId);
//		available.get(0).setStuNumber(stuNumber);
//		available.get(0).setStatus(dockerStatus);
//
//		dockerMapper.updateStatusByStuNum(available.get(0));
//		return available.get(0).getAddress();

		Docker docker = dockerMapper.getDockerByStuNum(stuNumber);
		String address = null;

		if (docker == null) {
			// 该学生没有被分配pod，需要对其进行新的分配

			checkTimeReset();  //在分配前，需要进行check

			List<Docker> available = dockerMapper.listAvailableDocker();

			// 当前没有可用pod，需要稍后刷新
			if (available.isEmpty()) System.out.println(" no available pod, please wait a sec to refresh....");
			// 为学生分配新的pod
			else docker = available.get(0);
		}

		if (docker != null) {
			String dockerStatus = null;
			if (statusCode == 0) {
				dockerStatus = "sleeping";
				processingId = 0;
			} else if (statusCode == 1) dockerStatus = "project";
			else if (statusCode == 2) dockerStatus = "experiment";
			docker.setProcessingId(processingId);
			docker.setStuNumber(stuNumber);
			docker.setStatus(dockerStatus);

			dockerMapper.updateStatusByStuNum(docker);
			address = docker.getAddress();
		}
		return address;
	}

	@Override
	@Transactional
	public boolean resetPod(long stuNumber) {
		try {
			Docker docker = dockerMapper.getDockerByStuNum(stuNumber);
			dockerMapper.deleteDocker(docker.getId());

			//该docker版本需要更新
			String latestVersionInDB = dockerMapper.checkDBLatestVersion();
			System.out.println(latestVersionInDB);
			if (!docker.getVersion().equals(latestVersionInDB))
				docker.setVersion(latestVersionInDB);

			docker.generate(SHELL_UTILS.getAddress());
			SHELL_UTILS.resetPod(docker.getId(), docker.getPort(), docker.getVersion());
			dockerMapper.createDocker(docker);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional
	public boolean checkTimeReset() {
		try {
			List<Docker> dockers = dockerMapper.listResetNeedingDocker();  // 获取超时无操作的docker
			dockerMapper.batchDelete(dockers);  // 从数据库中批量删除

			// 检查是否需要更新
			String latestVersionInDB = dockerMapper.checkDBLatestVersion();  // 获取数据库中最新版本docker
			for (int i = 0; i < dockers.size(); ++i) {
				if (!dockers.get(i).getVersion().equals(latestVersionInDB))
					dockers.get(i).setVersion(latestVersionInDB);
				dockers.get(i).generate(SHELL_UTILS.getAddress());
			}

			SHELL_UTILS.upgradePods(dockers, latestVersionInDB);
			dockerMapper.batchInsert(dockers);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean updateLastTimeByStuNum(long stuNumber){
		return dockerMapper.updateLastTimeByStuNum(stuNumber);
	}

	@Override
	public String checkUpgrade() {
		String dbLatestVersion = dockerMapper.checkDBLatestVersion().toLowerCase(Locale.ROOT);
		String latestVersion = SHELL_UTILS.latestVersion().toLowerCase(Locale.ROOT);

		if (latestVersion.equals(dbLatestVersion)) {
			System.out.print("using the latest version, no need to upgrade\n");
			return null;
		} else return latestVersion;
	}

	@Override
	@Transactional
	public boolean upgradePods(String latestVersion) {
		try {
			List<Docker> ds = dockerMapper.listDockerNeededUpgrade();
			dockerMapper.batchDelete(ds);
			SHELL_UTILS.upgradePods(ds, latestVersion);
			for (int i = 0; i < ds.size(); ++i) ds.get(i).setVersion(latestVersion);
			dockerMapper.batchInsert(ds);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


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

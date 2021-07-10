package com.example.octopus.service.impl;

import com.example.octopus.dao.SysUserRoleMapper;
import com.example.octopus.dao.TeacherMapper;
import com.example.octopus.entity.VOs.AdminInfoVO;
import com.example.octopus.entity.user.SysUserRole;
import com.example.octopus.entity.user.Teacher;
import com.example.octopus.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Hao
 * @date: 2021/6/10 20:15
 */
@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherMapper teacherMapper;
	@Autowired
	private SysUserRoleMapper sysUserRoleMapper;

	@Override
	public Teacher getTeacherByTeaNumber(long teaNumber) {
		Teacher teacher = teacherMapper.getByTeaNumber(teaNumber);
		teacher.setPassword(sysUserRoleMapper.getByUserId(teaNumber).getPassword());
		return teacher;
	}

	@Override
	public boolean updatePasswordByTeaNumber(long teaNumber, String newPassword) {
		return sysUserRoleMapper.updatePassword(teaNumber, newPassword);
	}

	@Override
	public List<Teacher> getAllTeachers() {
		return teacherMapper.getAllTeachers();
	}

	@Override
	public boolean updateTeacher(Teacher teacher) {
		Teacher old = teacherMapper.getByTeaNumber(teacher.getTeaNumber());
		Teacher t = old.mergeUpdate(old, teacher);
		long roleId = t.getAdminRights() ? 1 : 3;
		return teacherMapper.updateTeacher(t) && sysUserRoleMapper.updateRoleId(teacher.getTeaNumber(), roleId)
				&& sysUserRoleMapper.updatePassword(teacher.getTeaNumber(), teacher.getPassword());
	}

	@Override
	public boolean updateLoginInfo(long teaNumber) {
		return teacherMapper.updateLoginInfoByTeaNumber(teaNumber);
	}

	@Override
	public boolean addTeacher(Teacher teacher) {
		long roleId = teacher.getAdminRights() ? 1 : 3;
		//如无密码，则设置初始密码为123
		if (teacher.getPassword() == null)
			teacher.setPassword("123");
		SysUserRole sysUserRole = new SysUserRole(teacher.getTeaNumber(), roleId, teacher.getPassword());

		boolean add2TeaTable = teacherMapper.addTeacher(teacher);
		boolean add2SURTable = false;
		if (add2TeaTable) {
			add2SURTable = sysUserRoleMapper.insert(sysUserRole);
			//如成功加入到了teacher表但因主键问题等没有加入到SUR表，则删除先加在teacher表中的数据
			if (!add2SURTable) teacherMapper.deleteTeacher(teacher.getTeaNumber());
		}
		return add2TeaTable && add2SURTable;
	}

	@Override
	public boolean deleteTeacher(long teaNumber) {
		return teacherMapper.deleteTeacher(teaNumber) && sysUserRoleMapper.deleteUser(teaNumber);
	}

	@Override
	public int[] getSumVideoTimeByRole(long teaNumber) {
		long role = sysUserRoleMapper.getRoleByUserId(teaNumber);
		int[] sum = {0, 0, 0, 0, 0, 0, 0, 0};
		List<AdminInfoVO> result;
		if (role == 1) result = teacherMapper.getAllVideoStudyTimeSum();
		else result = teacherMapper.getVideoStudyTimeSumByTeaNumber(teaNumber);
		for (AdminInfoVO a : result) {
			int i = (int) (a.getToday().getTime() - a.getStudyDate().getTime()) / 1000 / 60 / 60 / 24;
			sum[sum.length - i - 1] = a.getSumStudyTime();
		}
		return sum;
	}

	@Override
	public int[] getSumExperimentTimeByRole(long teaNumber) {
		long role = sysUserRoleMapper.getRoleByUserId(teaNumber);
		int[] sum = {0, 0, 0, 0, 0, 0, 0, 0};
		List<AdminInfoVO> result;
		if (role == 1) result = teacherMapper.getAllExperimentTimeSum();
		else result = teacherMapper.getExperimentTimeSumByTeaNumber(teaNumber);
		for (AdminInfoVO a : result) {
			int i = (int) (a.getToday().getTime() - a.getStudyDate().getTime()) / 1000 / 60 / 60 / 24;
			sum[sum.length - i - 1] = a.getSumStudyTime();
		}
		return sum;
	}
}

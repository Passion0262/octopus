package com.example.octopus.service.impl;

import com.example.octopus.dao.SysUserRoleMapper;
import com.example.octopus.dao.TeacherMapper;
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
		long roleId = teacher.getAdminRights() ? 1 : 3;

		return teacherMapper.updateTeacher(teacher) && sysUserRoleMapper.updateRoleId(teacher.getTeaNumber(), roleId)
                && sysUserRoleMapper.updatePassword(teacher.getTeaNumber(), teacher.getPassword());
	}

	@Override
	public boolean addTeacher(Teacher teacher) {
        long roleId = teacher.getAdminRights() ? 1 : 3;
		SysUserRole sysUserRole = new SysUserRole(teacher.getTeaNumber(), roleId, teacher.getPassword());
		return teacherMapper.addTeacher(teacher) && sysUserRoleMapper.insert(sysUserRole);
	}

	@Override
	public boolean deleteTeacher(long teaNumber) {
		return teacherMapper.deleteTeacher(teaNumber) && sysUserRoleMapper.deleteUser(teaNumber);
	}

}

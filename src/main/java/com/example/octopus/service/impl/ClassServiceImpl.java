package com.example.octopus.service.impl;

import com.example.octopus.dao.*;
import com.example.octopus.entity.user.Class_;
import com.example.octopus.service.ClassService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/8 3:31 下午
 */
@Service
public class ClassServiceImpl implements ClassService {

	@Autowired
	UserMapper userMapper;
	@Autowired
	TeacherMapper teacherMapper;
	@Autowired
	ClassMapper classMapper;

	@Autowired
	TeacherCourseMapper teacherCourse_mapper;
	@Autowired
	SysUserRoleMapper sysUserRoleMapper;

	@Override
	public boolean insertClass(Class_ class_){
		if (classMapper.isExist(class_.getClassName(),class_.getSchool())){
			return false;
		} else {
			return classMapper.insertClass(class_);
		}
	}

	@Override
	public List<Class_> listClass_s() {
		return classMapper.listClass_s();
	}

	@Override
	public Class_ getClass_Byid(long classId) {
		return classMapper.getById(classId);
	}

	@Override
	public boolean deleteByClassName(String className) {
		return classMapper.deleteByClassName(className);
	}

	@Override
	public boolean deleteByClassId(long classId) {
		return classMapper.deleteByClassId(classId);
	}

	@Override
	public boolean updateClass(Class_ class_) {
		return classMapper.updateClass(class_);
	}

	@Override
	public List<Class_> listClassesByMajorId(long majorId) {
		return classMapper.listClass_sByMajorId(majorId);
	}

	@Override
	public List<Class_> listSchoolClassesByUserId(long userId) {
		long role = sysUserRoleMapper.getRoleByUserId(userId);
        String school;
        if (role == 2) {
            school = userMapper.getSchoolById(userId);
        } else {
            school = teacherMapper.getSchoolByTeaNumber(userId);
        }
        return classMapper.listSchoolClasses(school);
    }

    @Override
	public List<Class_> listSchoolMajorClassesByUserId(long userId, long majorId){
		long role = sysUserRoleMapper.getRoleByUserId(userId);
		String school;
		if (role == 2) {
			school = userMapper.getSchoolById(userId);
		} else {
			school = teacherMapper.getSchoolByTeaNumber(userId);
		}
		return classMapper.listSchoolMajorClasses(school, majorId);
	}
}

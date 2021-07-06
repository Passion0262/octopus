package com.example.octopus.service.impl;

import com.example.octopus.dao.*;
import com.example.octopus.entity.user.Student;
import com.example.octopus.entity.user.SysUserRole;
import com.example.octopus.service.UserService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/12 3:22 下午
 * @modified By：
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    TeacherCourseMapper teacherCourseMapper;

    @Autowired
    StudentCourseMapper studentCourseMapper;

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    TeacherMapper teacherMapper;

    @Override
    public List<Student> listStudents() {
        return userMapper.listAllStudents();
    }

    @Override
    public List<Student> listStudentsByTeaNumber(long teaNumber) {
//        List<Long> courseIdList = teacherCourseMapper.listCourseIdsByTeaNumber(teaNumber);
//
//        ArrayList<Long> stuNumberList = new ArrayList<>();
//        for (long courseId : courseIdList) {
//            stuNumberList.addAll(studentCourseMapper.listStuNumbersByCourseId(courseId));
//        }
//
//        ArrayList<Student> studentList = new ArrayList<>();
//        for (long stuNumber : stuNumberList) {
//            studentList.add(userMapper.getStudentByStuNumber(stuNumber));
//        }
//        return studentList;
		return userMapper.listStudentsByTeaNum(teaNumber);
    }

    @Override
    public Student getStudentByStuNumber(long stuNumber) {
        return userMapper.getStudentByStuNumber(stuNumber);
    }

    @Override
    public Student getStudentByName(String name) {
        return userMapper.getStudentByName(name);
    }

    @Override
    public boolean insertStudent(Student student, long teaNumber) {
        student.setSchool(teacherMapper.getSchoolByTeaNumber(teaNumber));
        SysUserRole sysUserRole = new SysUserRole(student.getStuNumber(), 2,  student.getPassword());
        return userMapper.insertStudent(student) && sysUserRoleMapper.insert(sysUserRole);
    }

    @Override
    public boolean updateStudent(Student student) {
        return userMapper.updateStudent(student) && sysUserRoleMapper.updatePassword(student.getStuNumber(), student.getPassword());
    }

    @Override
    public void updateLoginInfo(long stuNumber) {
        userMapper.updateLoginInfoByStuNumber(stuNumber);
    }

    @Override
    public void resetPassword(long stuNumber) {
        sysUserRoleMapper.updatePassword(stuNumber, "000000");
    }

    @Override
    public boolean updatePhoneNumber(long stuNumber, String phoneNumber) {
        return userMapper.updatePhoneByStuNumber(stuNumber, phoneNumber);
    }

}

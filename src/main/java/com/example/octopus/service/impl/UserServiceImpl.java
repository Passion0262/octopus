package com.example.octopus.service.impl;

import com.example.octopus.dao.StudentCourseMapper;
import com.example.octopus.dao.SysUserRoleMapper;
import com.example.octopus.dao.TeacherCourseMapper;
import com.example.octopus.dao.UserMapper;
import com.example.octopus.entity.user.Student;
import com.example.octopus.service.UserService;
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

    @Override
    public List<Student> listStudents() {
        return userMapper.listAllStudents();
    }

    @Override
    public List<Student> listStudentsByTeaNumber(long teaNumber) {
        List<Long> courseIdList = teacherCourseMapper.listCourseIdsByTeaNumber(teaNumber);

        ArrayList<Long> stuNumberList = new ArrayList<>();
        for (long courseId : courseIdList) {
            stuNumberList.addAll(studentCourseMapper.listStuNumbersByCourseId(courseId));
        }

        ArrayList<Student> studentList = new ArrayList<>();
        for (long stuNumber : stuNumberList) {
            studentList.add(userMapper.getStudentByStuNumber(stuNumber));
        }
        return studentList;
    }

    @Override
    public Student getStudentByStuNumber(long stuNumber) {
        return userMapper.getStudentByStuNumber(stuNumber);
    }

    @Override
    public Student getStudentByName(String name) {
        return userMapper.getStudentByName(name);
    }

//    @Override
//    public Student login(long stuNumber, String password) {
//        return userMapper.getStudentByStuNumberAndPassword(stuNumber, password);
//    }

    @Override
    public boolean insertStudent(Student student) {
        return userMapper.insertStudent(student) && sysUserRoleMapper.updatePassword(student.getStuNumber(), student.getPassword());
    }

    //todo 确认是否在user和teacher表中存储密码，（建议不做存储，密码仅保存在sysuserrole中），同时需确定这两个实例类和表属性是否对齐
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
        //userMapper.resetPassword(stuNumber);
        sysUserRoleMapper.updatePassword(stuNumber, "000000");
    }

    @Override
    public boolean updatePhoneNumber(long stuNumber, String phoneNumber) {
        return userMapper.updatePhoneByStuNumber(stuNumber, phoneNumber);
    }

}

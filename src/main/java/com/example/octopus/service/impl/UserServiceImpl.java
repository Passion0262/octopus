package com.example.octopus.service.impl;

import com.example.octopus.dao.UserMapper;
import com.example.octopus.entity.user.Student;
import com.example.octopus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<Student> getStudentList() {
        return userMapper.queryAllStudents();
    }

    @Override
    public Student findStudentByStuNumber(String stuNumber) {
        return userMapper.queryStudentByStuNumber(stuNumber);
    }

    @Override
    public Student findStudentByName(String name) {
        return userMapper.queryStudentByName(name);
    }

    @Override
    public Student login(String stuNumber, String password) {
        return userMapper.queryStudentByStuNumberAndPassword(stuNumber, password);
    }

//    @Override
//    public Student Register(String name, String stuNumber, String password) {
//        return null;
//    }

    @Override
    public void updateLoginInfo(String stuNumber) {
        userMapper.updateLoginInfoByStuNumber(stuNumber);
    }

    @Override
    public void resetPassword(String stuNumber) {
        userMapper.resetPassword(stuNumber);
    }

    @Override
    public boolean updatePhoneNumber(String stuNumber, String phoneNumber) {
        return userMapper.changePhone(stuNumber, phoneNumber);
    }
}

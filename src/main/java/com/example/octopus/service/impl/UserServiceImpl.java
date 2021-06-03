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
        return userMapper.listAllStudents();
    }

    @Override
    public Student findStudentByStuNumber(long stuNumber) {
        return userMapper.getStudentByStuNumber(stuNumber);
    }

    @Override
    public Student findStudentByName(String name) {
        return userMapper.getStudentByName(name);
    }

    @Override
    public Student login(long stuNumber, String password) {
        return userMapper.getStudentByStuNumberAndPassword(stuNumber, password);
    }

//    @Override
//    public Student Register(String name, String stuNumber, String password) {
//        return null;
//    }

    @Override
    public void updateLoginInfo(long stuNumber) {
        userMapper.updateLoginInfoByStuNumber(stuNumber);
    }

    @Override
    public void resetPassword(long stuNumber) {
        userMapper.resetPassword(stuNumber);
    }

    @Override
    public boolean updatePhoneNumber(long stuNumber, String phoneNumber) {
        return userMapper.changePhone(stuNumber, phoneNumber);
    }
}

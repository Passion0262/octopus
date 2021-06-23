package com.example.octopus.service.impl;

import com.example.octopus.dao.TeacherMapper;
import com.example.octopus.entity.user.Teacher;
import com.example.octopus.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Hao
 * @date: 2021/6/10 20:15
 */
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher getTeacherByTeaNumber(long teaNumber){
        return teacherMapper.getByTeaNumber(teaNumber);
    }

    @Override
    public boolean resetPasswordByTeaNumber(long teaNumber, String newPassword){
        return teacherMapper.updateTeacherPassword(teaNumber, newPassword);
    }

}

package com.example.octopus.service.impl;

import com.example.octopus.dao.MajorMapper;
import com.example.octopus.dao.TeacherMapper;
import com.example.octopus.entity.user.Major;
import com.example.octopus.entity.user.Teacher;
import com.example.octopus.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/26 5:23 下午
 * @modified By：
 */
@Service
public class MajorServiceImpl implements MajorService {

    @Autowired
    MajorMapper majorMapper;

    @Autowired
    TeacherMapper  teacherMapper;

    @Override
    public List<Major> listMajors() {
        return majorMapper.listMajors();
    }

    @Override
    public Major getById(long id) {
        return majorMapper.getById(id);
    }

    @Override
    public Major getByTeaNumber(long teaNumber) {
        long majorId = teacherMapper.getByTeaNumber(teaNumber).getMajorId();
        return majorMapper.getById(majorId);
    }

    @Override
    public boolean insertMajor(Major major) {
        return majorMapper.insertMajor(major);
    }

    @Override
    public boolean updateMajor(Major major) {
        return majorMapper.updateMajor(major);
    }

    @Override
    public boolean deleteById(long id) {
        return majorMapper.deleteMajorById(id);
    }
}

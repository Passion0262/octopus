package com.example.octopus.service.impl;

import com.example.octopus.dao.ClassMapper;
import com.example.octopus.dao.TeacherClass_Mapper;
import com.example.octopus.entity.user.Class_;
import com.example.octopus.service.ClassService;
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
    ClassMapper classMapper;

    @Autowired
    TeacherClass_Mapper teacherClass_mapper;

    @Override
    public boolean insertClass(Class_ class_) {
        return classMapper.insertClass(class_);
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
    public List<Class_> listClass_sByTeaNumber(long teaNumber) {
        List<Class_> classList = new ArrayList<>();
        List<Long> classIdList = teacherClass_mapper.listClass_idsByTeaNumber(teaNumber);
        for (long classId:classIdList) {
            classList.add(classMapper.getById(classId));
        }
        return classList;
    }

    @Override
    public boolean deleteByClassName(String className) {
        return classMapper.deleteByClassName(className);
    }

}

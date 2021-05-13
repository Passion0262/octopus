package com.example.octopus.service.impl;

import com.example.octopus.entity.Student;
import com.example.octopus.repository.StudentRepository;
import com.example.octopus.service.StudentService;
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
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public List<Student> getStudentList() {
        return studentRepository.findAll();
    }

    @Override
    public Student findStudentById(long id) {
        return studentRepository.findById(id);
    }
}

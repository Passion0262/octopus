package com.example.octopus.service;

import com.example.octopus.entity.Student;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/12 3:21 下午
 * @modified By：
 */
public interface StudentService {

    public List<Student> getStudentList();

    public Student findStudentById(long id);
}

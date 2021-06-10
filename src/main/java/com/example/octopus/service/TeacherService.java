package com.example.octopus.service;

import com.example.octopus.entity.user.Teacher;
import org.springframework.stereotype.Service;

/**
 * @author: Hao
 * @date: 2021/6/10 18:48
 */
public interface TeacherService {

    /**
     * 通过教师号查教师信息
     * @param teaNumber 教师号
     * @return 教师信息
     */
    Teacher getTeacherByTeaNumber(long teaNumber);

}

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

    /**
     * 重置教师登录密码
     * @param teaNumber 教师号
     * @param newPassword 新密码
     * @return 修改成功与否
     */
    boolean resetPasswordByTeaNumber(long teaNumber, String newPassword);

}

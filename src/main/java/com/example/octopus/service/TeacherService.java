package com.example.octopus.service;

import com.example.octopus.entity.user.Teacher;
import org.springframework.stereotype.Service;

import java.util.List;

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
    boolean updatePasswordByTeaNumber(long teaNumber, String newPassword);

    /**
     * 获取所有的老师
     * @return 返回所有老师的列表
     */
    List<Teacher> getAllTeachers();

    /**
     * 更新老师信息
     * @param teacher 老师实体
     * @return 更新成功与否
     */
    boolean updateTeacher(Teacher teacher);

    /**
     * 更新最近登陆时间和登录次数
     * @param teaNumber 教师号
     * @return 成功与否
     */
    boolean updateLoginInfo(long teaNumber);

    /**
     * 增加老师
     * @param teacher 老师实体
     * @return 成功与否
     */
    boolean addTeacher(Teacher teacher);

    /**
     * 删除老师
     * @param teaNumber 老师号
     * @return 成功与否
     */
    boolean deleteTeacher(long teaNumber);


}

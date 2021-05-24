package com.example.octopus.service;

import com.example.octopus.entity.user.Student;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/12 3:21 下午
 * @modified By：
 */
public interface StudentService {
    /**
     * 获取所有学生列表
     * @return  学生的list
     */
    public List<Student> getStudentList();

    /**
     * @param stuNumber 学生的id
     * @return  该学生对象
     */
    public Student findStudentByStuNumber(String stuNumber);

    /**
     * 用于登陆时的验证
     * @param stuNumber 学生学号
     * @param password  学生密码
     * @return  返回学生实体
     */
    public Student login(String stuNumber, String password);

    /**
     * 注册账号
     * @param name  姓名
     * @param stuNumber 学号
     * @param password  密码
     * @return  学生实体
     */
//    public Student Register(String name, String stuNumber, String password);

    /**
     *  更新最近登陆时间和登录次数
     * @param stuNumber 学生
     * @return
     */
    public void updateLoginInfo(String stuNumber);

    public void resetPassword(String stuNumber);
}

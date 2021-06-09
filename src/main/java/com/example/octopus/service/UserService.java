package com.example.octopus.service;

import com.example.octopus.entity.user.Student;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/12 3:21 下午
 * @modified By：
 */
public interface UserService {
    /**
     * 获取所有学生列表
     * @return  学生的list
     */
    List<Student> listStudents();

    /**
     * 根据老师编号查询所教授的课程的所有学生list
     * @param teaNumber 老师编号
     * @return 学生实体list
     */
    List<Student> listStudentsByTeaNumber(long teaNumber);

    /**
     * @param stuNumber 学生的id
     * @return  该学生对象
     */
    Student getStudentByStuNumber(long stuNumber);

    /**
     * @param name 学生的name
     * @return  该学生对象
     */
    Student getStudentByName(String name);

    /**
     * 用于登陆时的验证
     * @param stuNumber 学生学号
     * @param password  学生密码
     * @return  返回学生实体
     */
    Student login(long stuNumber, String password);

    /**
     * 新增学生
     * @param student 学生实体
     * @return 成功为true，失败为false
     */
    boolean insertStudent(Student student);

    /**
     * 更新学生信息
     * @param student 学生实体
     * @return 成功为true，失败为false
     */
    boolean updateStudent(Student student);

    /**
     *  更新最近登陆时间和登录次数
     * @param stuNumber 学生
     * @return
     */
    void updateLoginInfo(long stuNumber);

    void resetPassword(long stuNumber);

    /**
     * 根据学号更改手机号
     * @param stuNumber 学号
     * @param phoneNumber 手机号
     */
    boolean updatePhoneNumber(long stuNumber, String phoneNumber);

}

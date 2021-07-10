package com.example.octopus.service;

import com.example.octopus.entity.user.Class_;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/21 9:55 上午
 * @modified By：
 */
public interface ClassService {

    /**
     * 新增班级
     */

    boolean insertClass(Class_ class_);

    /**
     * 查询所有课程
     *
     * @return class实体list
     */
    List<Class_> listClass_s();

    /**
     * 根据课程id查询课程
     *
     * @param classId 课程id
     * @return 返回class_实体
     */
    Class_ getClass_Byid(long classId);

    /**
     * 根据className删除班级
     *
     * @param className 班级名称
     * @return 成功为true，失败为false
     */
    boolean deleteByClassName(String className);

    /**
     * 根据classId删除班级
     *
     * @param classId 班级id
     * @return 成功为true，失败为false
     */
    boolean deleteByClassId(long classId);

    /**
     * 修改班级信息
     *
     * @param class_ 班级对象
     * @return 成功为true
     */
    boolean updateClass(Class_ class_);

    /**
     * 通过专业号列出所有该专业下所有班级
     * @param majorId 专业号
     * @return 班级实体列表
     */
    List<Class_> listClassesByMajorId(long majorId);

    /**
     * 通过用户号，列出用户所在学校的所有班级
     * @param userId 用户号
     * @return 班级实体列表
     */
    List<Class_> listSchoolClassesByUserId(long userId);

    /**
     * 通过用户号和专业号列出同学校专业的班级
     * @param userId 用户号
     * @param majorId 专业号
     * @return 班级实体列表
     */
    List<Class_> listSchoolMajorClassesByUserId(long userId, long majorId);
}

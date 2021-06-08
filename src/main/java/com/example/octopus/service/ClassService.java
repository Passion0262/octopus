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
     * 新增课程
     * @param class_ 课程实体
     * @return 成功为true，失败为false
     */
    boolean insertClass(Class_ class_);

    /**
     * 找到所有课程
     * @return class实体list
     */
    List<Class_> listClass_s();

    /**
     * 根据课程id查询课程
     * @param classId 课程id
     * @return 返回class_实体
     */
    Class_ getClass_Byid(long classId);

    /**
     * 根据教师编号查询所有该教师教授的课程
     * @param teaNumber 教师编号
     * @return class实体 list
     */
    List<Class_> listClass_sByTeaNumber(long teaNumber);

    /**
     * 根据className删除班级
     * @param className 班级名称
     * @return 成功为true，失败为false
     */
    boolean deleteByClassName(String className);
}

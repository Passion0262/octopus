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
     * 查询所有课程
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
     * 根据className删除班级
     * @param className 班级名称
     * @return 成功为true，失败为false
     */
    boolean deleteByClassName(String className);

    /**
     * 修改班级信息
     * @param class_ 班级对象
     * @return 成功为true
     */
    boolean updateClass(Class_ class_);
}

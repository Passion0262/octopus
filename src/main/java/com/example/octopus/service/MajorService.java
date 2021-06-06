package com.example.octopus.service;

import com.example.octopus.entity.user.Major;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/26 5:21 下午
 * @modified By：
 */
public interface MajorService {
    /**
     * 获取所有专业列表
     * @return  Major实体List
     */
    List<Major> listMajors();

    /**
     * 根据教师工号获取所教专业信息
     * @return Major实体
     */
    Major getByTeaNumber(long teaNumber);

    /**
     * 新增major
     * @param major major实体
     * @return 成功为true，失败为false
     */
    boolean insertMajor(Major major);

    /**
     * 更新major
     * @param major major实体
     * @return 成功为true，失败为false
     */
    boolean updateMajor(Major major);

    /**
     * 根据id删除major（注意:不是根据majorCode）
     * @param id 专业id
     * @return 成功为true，失败为false
     */
    boolean deleteById(long id);

}

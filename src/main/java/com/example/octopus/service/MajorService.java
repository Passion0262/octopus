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
    List<Major> findAllMajor();



}

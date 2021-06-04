package com.example.octopus.service.impl;

import com.example.octopus.dao.MajorMapper;
import com.example.octopus.entity.user.Major;
import com.example.octopus.service.MajorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/26 5:23 下午
 * @modified By：
 */
@Service
public class MajorServiceImpl implements MajorService {

    @Autowired
    MajorMapper majorMapper;

    @Override
    public List<Major> findAllMajor() {
        return majorMapper.listMajors();
    }
}

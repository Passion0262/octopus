package com.example.octopus.service;

import com.example.octopus.entity.dataset.Dataset;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/2 5:00 下午
 */
public interface DatasetService {

    /**
     * 返回所有的数据集
     * @return 数据集set
     */
    List<Dataset> findAllDataset();

}

package com.example.octopus.service;

import com.example.octopus.entity.dataset.Dataset;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/2 5:00 下午
 */
public interface DatasetService {

    /**
     * 查找所有的数据集
     * @return 数据集set
     */
    List<Dataset> listDatasets();

    /**
     * 根据id查找数据集
     * @param id 数据集id
     * @return 数据集实体
     */
    Dataset getDatasetById(long id);

    /**
     *  数据集下载次数+1
     */
    boolean increaseDownloadNum(long id);

}

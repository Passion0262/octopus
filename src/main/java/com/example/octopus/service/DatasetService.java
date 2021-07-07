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

    /**
     * 更新数据集，需保证对象的各项属性完整且正确
     * @param dataset 数据集对象
     */
    boolean updateDatasetById(Dataset dataset);

    /**
     * 增加数据集
     * @param dataset 数据集对象
     */
    boolean addDataset(Dataset dataset);

    /**
     * 根据id删除数据集
     * @param datasetId 数据集id
     */
    boolean deleteDataset(long datasetId);

}

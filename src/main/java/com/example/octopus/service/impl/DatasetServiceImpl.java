package com.example.octopus.service.impl;

import com.example.octopus.dao.DatasetMapper;
import com.example.octopus.entity.dataset.Dataset;
import com.example.octopus.service.DatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/2 5:01 下午
 */
@Service
public class DatasetServiceImpl implements DatasetService {

    @Autowired
    DatasetMapper datasetMapper;

    @Override
    public List<Dataset> listDatasets() {
        return datasetMapper.listDatasets();
    }

    @Override
    public Dataset getDatasetById(long id) {
        return datasetMapper.getDatasetById(id);
    }

    @Override
    public boolean increaseDownloadNum(long id) {
        return datasetMapper.updateDownloadNumById(id);
    }

    @Override
    public boolean updateDatasetById(Dataset dataset){
        Dataset old = datasetMapper.getDatasetById(dataset.getId());
        if (old!=null){
            datasetMapper.deleteDatasetById(old.getId());
            return datasetMapper.addDataset(dataset);
        } else {
            System.out.println("Dataset id is wrong! Cannot find the specific dataset.");
            return false;
        }

    }

    @Override
    public boolean addDataset(Dataset dataset){
        return datasetMapper.addDataset(dataset);
    }

    @Override
    public boolean deleteDataset(long datasetId){
        return datasetMapper.deleteDatasetById(datasetId);
    }
}

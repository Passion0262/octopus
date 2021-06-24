package com.example.octopus.service.impl;

import com.example.octopus.dao.experiment.SubExperimentReportSaveMapper;
import com.example.octopus.entity.experiment.SubExperimentReportSave;
import com.example.octopus.service.SubExperimentReportSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/24 8:24 下午
 */
@Service
public class SubExperimentReportSaveServiceImpl implements SubExperimentReportSaveService {

    @Autowired
    SubExperimentReportSaveMapper subExperimentReportSaveMapper;

    @Override
    public List<SubExperimentReportSave> listByStuNumberAndSubExperimentId(long subExperimentId, long stuNumber) {
        return subExperimentReportSaveMapper.listByStuNumberAndSubExperimentId(subExperimentId,stuNumber);
    }

    @Override
    public SubExperimentReportSave getLatest(long subExperimentId, long stuNumber) {
        return subExperimentReportSaveMapper.getLatest(subExperimentId,stuNumber);
    }

    @Override
    public SubExperimentReportSave getById(long id) {
        return subExperimentReportSaveMapper.getById(id);
    }

    @Override
    public boolean insert(SubExperimentReportSave subExperimentReportSave) {
        return subExperimentReportSaveMapper.insert(subExperimentReportSave);
    }

    @Override
    public boolean update(SubExperimentReportSave subExperimentReportSave) {
        return subExperimentReportSaveMapper.update(subExperimentReportSave);
    }

    @Override
    public boolean delete(long id) {
        return subExperimentReportSaveMapper.deleteById(id);
    }
}

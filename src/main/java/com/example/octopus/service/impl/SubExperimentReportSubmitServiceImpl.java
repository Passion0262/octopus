package com.example.octopus.service.impl;

import com.example.octopus.dao.experiment.SubExperimentReportSaveMapper;
import com.example.octopus.dao.experiment.SubExperimentReportSubmitMapper;
import com.example.octopus.entity.experiment.SubExperimentReportSubmit;
import com.example.octopus.service.SubExperimentReportSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/24 8:33 下午
 */
@Service
public class SubExperimentReportSubmitServiceImpl implements SubExperimentReportSubmitService {

    @Autowired
    SubExperimentReportSubmitMapper subExperimentReportSubmitMapper;

    @Override
    public SubExperimentReportSubmit getByStuNumberAndSubExperimentId(long subExperimentId, long stuNumber) {
        return subExperimentReportSubmitMapper.getByStuNumberAndSubExperimentId(subExperimentId,stuNumber);
    }

    @Override
    public SubExperimentReportSubmit getById(long id) {
        return subExperimentReportSubmitMapper.getById(id);
    }

    @Override
    public boolean insert(SubExperimentReportSubmit submit) {
        SubExperimentReportSubmit pre = getByStuNumberAndSubExperimentId(submit.getSubExperimentId(),submit.getStuNumber());
        if (pre != null){  //如果原本已存在提交记录，则无法再次提交
            return false;
        } else {
            return subExperimentReportSubmitMapper.insert(submit);
        }
    }

    @Override
    public boolean update(SubExperimentReportSubmit subExperimentReportSubmit) {
        return subExperimentReportSubmitMapper.update(subExperimentReportSubmit);
    }

    @Override
    public boolean deleteById(long id) {
        return subExperimentReportSubmitMapper.deleteById(id);
    }
}

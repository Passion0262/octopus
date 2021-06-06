package com.example.octopus.service.impl;

import com.example.octopus.dao.ExperimentMapper;
import com.example.octopus.entity.experiment.Experiment;
import com.example.octopus.service.ExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/2 4:12 下午
 */
@Service
public class ExperimentServiceImpl implements ExperimentService {

    @Autowired
    ExperimentMapper experimentMapper;

    @Override
    public List<Experiment> listExperiments() {
        return experimentMapper.listExperiments();
    }

    @Override
    public Experiment getExperimentById(long id) {
        return experimentMapper.getExperimentById(id);
    }

    @Override
    public List<Experiment> listChosenByStuNumber(long stuNumber) {
        //根据学生学号获取所有选的课对应的所有experiment的id
        List<Long> id_list = experimentMapper.listChosenExperiments(stuNumber);
        //根据experiment id 遍历获取experiment实体
        List<Experiment> exp_list = new ArrayList<Experiment>();
        for (long id:id_list) {
            Experiment exp = experimentMapper.getExperimentById(id);
            exp_list.add(exp);
        }
        return exp_list;
    }
}

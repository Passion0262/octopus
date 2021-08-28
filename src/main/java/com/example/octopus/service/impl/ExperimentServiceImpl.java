package com.example.octopus.service.impl;

import com.example.octopus.dao.CourseExperimentMapper;
import com.example.octopus.dao.experiment.ExperimentMapper;
import com.example.octopus.entity.VOs.experiment.ExperimentTimeHistoryVO;
import com.example.octopus.entity.VOs.experiment.ExperimentTimeVO;
import com.example.octopus.entity.experiment.Experiment;
import com.example.octopus.service.CourseService;
import com.example.octopus.service.ExperimentService;
import com.example.octopus.service.StudentCourseService;
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

    @Autowired
    CourseExperimentMapper courseExperimentMapper;

    @Autowired
    StudentCourseService studentCourseService;

    @Autowired
    CourseService courseService;

    @Override
    public List<Experiment> listExperiments() {
        return experimentMapper.listExperiments();
    }

    @Override
    public Experiment getExperimentById(long id) {
        return experimentMapper.getExperimentById(id);
    }

    @Override
    public Experiment getExperimentByCourseId(long courseId) {
        long experimentId = courseExperimentMapper.getExperimentIdByCourseId(courseId);
        return getExperimentById(experimentId);
    }

    @Override
    public List<Experiment> listExperimentsByStuNumber(long stuNumber) {
        //根据学生学号获取所有选的课程id list
        List<Long> courseIdList = studentCourseService.listCourseIdsByStuNumber(stuNumber);
        //根据experiment id 遍历获取experiment实体
        List<Experiment> exp_list = new ArrayList<Experiment>();
        for (long courseId:courseIdList) {
            Long courseIdstatic = courseService.getCourseById(courseId).getCourseStaticId();
            Long experimentId = courseExperimentMapper.getExperimentIdByCourseId(courseIdstatic);
            if (experimentId!=null) {
                Experiment exp = getExperimentById(experimentId);
                if (exp!=null) {
                    exp_list.add(exp);
                }
            }
        }
        return exp_list;
    }

    @Override
    public List<ExperimentTimeVO> countExperimentTime(long stuNumber) {
        return experimentMapper.countExperimentTimeByStuNumberGroupByCourseId(stuNumber);
    }

    @Override
    public List<ExperimentTimeHistoryVO> getHistory7DaysExperimentTime(long stuNumber) {
        return experimentMapper.getHistory7DaysExperimentTime(stuNumber);
    }
}

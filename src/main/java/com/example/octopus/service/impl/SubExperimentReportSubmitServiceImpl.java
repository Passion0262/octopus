package com.example.octopus.service.impl;

import com.example.octopus.dao.experiment.SubExperimentReportSaveMapper;
import com.example.octopus.dao.experiment.SubExperimentReportSubmitMapper;
import com.example.octopus.entity.experiment.SubExperimentReportSubmit;
import com.example.octopus.entity.user.Course;
import com.example.octopus.service.CourseService;
import com.example.octopus.service.ExperimentService;
import com.example.octopus.service.SubExperimentReportSubmitService;
import com.example.octopus.service.SubExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/24 8:33 下午
 */
@Service
public class SubExperimentReportSubmitServiceImpl implements SubExperimentReportSubmitService {

    @Autowired
    SubExperimentReportSubmitMapper subExperimentReportSubmitMapper;

    @Autowired
    CourseService courseService;

    @Autowired
    ExperimentService experimentService;

    @Autowired
    SubExperimentService subExperimentService;

    @Override
    public SubExperimentReportSubmit getByStuNumberAndSubExperimentId(long subExperimentId, long stuNumber) {
        return subExperimentReportSubmitMapper.getByStuNumberAndSubExperimentId(subExperimentId,stuNumber);
    }

    @Override
    public SubExperimentReportSubmit getById(long id) {
        return subExperimentReportSubmitMapper.getById(id);
    }

    @Override
    public List<SubExperimentReportSubmit> listByTeaNumber(long teaNumber) {
        List<SubExperimentReportSubmit> result = new ArrayList<>();
        List<Course> courseList = courseService.listCoursesByTeaNumber(teaNumber);  //获取老师教的所有课
        for (Course course:courseList) {
            long ExperimentId = experimentService.getExperimentByCourseId(course.getId()).getId();  //获取这门课对应的实验id
            List<Long> subExperimentIdList = subExperimentService.listIdsByExperimentId(ExperimentId);  //获取实验对应的所有子实验id
            for (Long subExperimentId:subExperimentIdList) {
                result.addAll(subExperimentReportSubmitMapper.listBySubExperimentId(subExperimentId));  //添加所有子实验对应的提交记录
            }
        }
        return result;
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
    public boolean updateBySubmit(long subExperimentId, long stuNumber, String content) {
        return subExperimentReportSubmitMapper.updateBySubmit(subExperimentId,stuNumber,content);
    }

    @Override
    public boolean updateByExamine(long subExperimentId, long stuNumber, long teaNumber, int score) {
        return subExperimentReportSubmitMapper.updateByExamine(subExperimentId,stuNumber,teaNumber,score);
    }

    @Override
    public boolean deleteById(long id) {
        return subExperimentReportSubmitMapper.deleteById(id);
    }
}

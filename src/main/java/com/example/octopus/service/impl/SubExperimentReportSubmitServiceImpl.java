package com.example.octopus.service.impl;

import com.example.octopus.dao.SysUserRoleMapper;
import com.example.octopus.dao.experiment.SubExperimentReportSubmitMapper;
import com.example.octopus.entity.VOs.SubExperimentReportSummaryVO;
import com.example.octopus.entity.experiment.SubExperimentReportSubmit;
import com.example.octopus.service.CourseService;
import com.example.octopus.service.ExperimentService;
import com.example.octopus.service.SubExperimentReportSubmitService;
import com.example.octopus.service.SubExperimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Autowired
	SysUserRoleMapper sysUserRoleMapper;

	@Override
	public SubExperimentReportSubmit getByStuNumberAndSubExperimentId(long subExperimentId, long stuNumber) {
		return subExperimentReportSubmitMapper.getByStuNumberAndSubExperimentId(subExperimentId, stuNumber);
	}

	@Override
	public SubExperimentReportSubmit getById(long id) {
		return subExperimentReportSubmitMapper.getById(id);
	}

	@Override
	public List<SubExperimentReportSubmit> listAll() {
		return subExperimentReportSubmitMapper.listAll();
	}

	@Override
	public List<SubExperimentReportSubmit> listByTeaNumber(long teaNumber) {
		return subExperimentReportSubmitMapper.listByTeaNumber(teaNumber);
	}

	@Override
	public boolean insert(SubExperimentReportSubmit submit) {
		SubExperimentReportSubmit pre = getByStuNumberAndSubExperimentId(submit.getSubExperimentId(), submit.getStuNumber());
		if (pre != null) {  //如果原本已存在提交记录，则无法再次提交
			return false;
		} else {
			return subExperimentReportSubmitMapper.insert(submit);
		}
	}

	@Override
	public boolean updateBySubmit(long subExperimentId, long stuNumber, String content) {
		return subExperimentReportSubmitMapper.updateBySubmit(subExperimentId, stuNumber, content);
	}

	@Override
	public boolean updateByExamine(long subExperimentId, long stuNumber, long teaNumber, int score) {
		return subExperimentReportSubmitMapper.updateByExamine(subExperimentId, stuNumber, teaNumber, score);
	}

	@Override
	public boolean deleteById(long id) {
		return subExperimentReportSubmitMapper.deleteById(id);
	}

	@Override
	public List<SubExperimentReportSummaryVO> getReportSummaryByRole(long teaNumber) {
		long role = sysUserRoleMapper.getRoleByUserId(teaNumber);
		//todo 修改mapper，将listAllProcessNum作为主要，向里面添加各种名，listAllReportSummary是次要，只存放examined和unexamined
		List<SubExperimentReportSummaryVO> result, tem;
		if (role == 1) {
			//管理员获取全部
			result = subExperimentReportSubmitMapper.listAllReportSummary1();
			tem = subExperimentReportSubmitMapper.listAllReportSummary2();
		} else {
			result = subExperimentReportSubmitMapper.listReportSummaryByTeaId1(teaNumber);
			tem = subExperimentReportSubmitMapper.listReportSummaryByTeaId2(teaNumber);
		}

		// 对list这样使用for循环效率更高（虽然有限）
		int lenResult = result.size(), lenTem = tem.size();
		for (int i = 0; i < lenResult; ++i) {
            for (int j = 0; j < lenTem; ++j)
                if(tem.get(j).getSubExperimentId()==result.get(i).getSubExperimentId()){
                    result.get(i).setExaminedNum(tem.get(j).getExaminedNum());
                    result.get(i).setUnexaminedNum(tem.get(j).getUnexaminedNum());
                    int notSubmitNum = result.get(i).getNotSubmitNum()-tem.get(i).getExaminedNum()-tem.get(i).getUnexaminedNum();
                    result.get(i).setNotSubmitNum(notSubmitNum);
                    break;
                }
		}

		return result;
	}
}

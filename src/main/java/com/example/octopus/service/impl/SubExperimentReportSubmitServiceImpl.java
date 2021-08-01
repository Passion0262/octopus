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
	SubExperimentReportSubmitMapper reportSubmitMapper;

	@Autowired
	CourseService courseService;

	@Autowired
	ExperimentService experimentService;

	@Autowired
	SubExperimentService subExperimentService;

	@Autowired
	SysUserRoleMapper sysUserRoleMapper;

	@Override
	public SubExperimentReportSubmit getByStuNumberAndSubExperimentId(long subExpId, long stuNumber) {
		return reportSubmitMapper.getByStuNumberAndSubExperimentId(subExpId, stuNumber);
	}

	@Override
	public SubExperimentReportSubmit getById(long id) {
		return reportSubmitMapper.getById(id);
	}

	@Override
	public List<SubExperimentReportSubmit> listAll() {
		return reportSubmitMapper.listAll();
	}

	@Override
	public List<SubExperimentReportSubmit> listByTeaNumber(long teaNumber) {
		return reportSubmitMapper.listByTeaNumber(teaNumber);
	}

	@Override
	public boolean insert(SubExperimentReportSubmit submit) {
		SubExperimentReportSubmit pre = getByStuNumberAndSubExperimentId(submit.getSubExperimentId(), submit.getStuNumber());
		if (pre != null) {  //如果原本已存在提交记录，则无法再次提交
			return false;
		} else {
			return reportSubmitMapper.insert(submit);
		}
	}

//	@Override
//	public boolean updateBySubmit(long subExperimentId, long stuNumber, String content) {
//		return reportSubmitMapper.updateBySubmit(subExperimentId, stuNumber, content);
//	}

	@Override
	public boolean updateByExamine(long subExpId, long stuNumber, long teaNumber, int score) {
		return reportSubmitMapper.updateByExamine(subExpId, stuNumber, teaNumber, score);
	}

	@Override
	public boolean deleteById(long id) {
		return reportSubmitMapper.deleteById(id);
	}

	@Override
	public List<SubExperimentReportSummaryVO> getReportSummaryByRole(long teaNumber) {
		long role = sysUserRoleMapper.getRoleByUserId(teaNumber);
		List<SubExperimentReportSummaryVO> result, tem;
		if (role == 1) {
			//管理员获取全部
			result = reportSubmitMapper.listAllReportSummary1();
			tem = reportSubmitMapper.listAllReportSummary2();
		} else {
			result = reportSubmitMapper.listReportSummaryByTeaId1(teaNumber);
			tem = reportSubmitMapper.listReportSummaryByTeaId2(teaNumber);
		}

		// 对list这样使用for循环效率更高（虽然有限）
		int lenResult = result.size(), lenTem = tem.size();
		for (int i = 0; i < lenResult; ++i) {
			for (int j = 0; j < lenTem; ++j)
				if (tem.get(j).getSubExperimentId() == result.get(i).getSubExperimentId()) {
					result.get(i).setExaminedNum(tem.get(j).getExaminedNum());
					result.get(i).setUnexaminedNum(tem.get(j).getUnexaminedNum());
					int notSubmitNum = result.get(i).getNotSubmitNum() - tem.get(j).getExaminedNum() - tem.get(j).getUnexaminedNum();
					result.get(i).setNotSubmitNum(notSubmitNum);
					break;
				}
		}

		return result;
	}

	@Override
	public int[] getReportAnalysisByRoleAndSubExpId(long teaNumber, long subExpId) {
		int[] analysis = {0, 0, 0, 0, 0, 0};  //0-60，60-70，70-80，80-90，90-95，95-100
		List<Integer> scores;
		long role = sysUserRoleMapper.getRoleByUserId(teaNumber);
		scores = (role == 1) ? reportSubmitMapper.getAllScoreBySubExpId(subExpId) : reportSubmitMapper.getScoreByTeaIdAndSubExpId(teaNumber, subExpId);
		for(int i=0;i<scores.size();++i){
			int s = scores.get(i);
			if (s<60) ++analysis[0];
			else if (s<70) ++analysis[1];
			else if(s<80) ++analysis[2];
			else if (s<90)  ++analysis[3];
			else if(s<95) ++analysis[4];
			else ++analysis[5];
		}
		return analysis;
	}

	@Override
	public List<SubExperimentReportSubmit> listReportScoreByRoleAndSubExpId(long teaNumber, long subExpId){
		long role = sysUserRoleMapper.getRoleByUserId(teaNumber);
		return (role==1)?reportSubmitMapper.listAllScoreBySubExpId(subExpId):reportSubmitMapper.listScoreByTeaIdAndSubExpId(teaNumber, subExpId);

	}
}

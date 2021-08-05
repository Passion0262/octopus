package com.example.octopus.service.impl;

import com.example.octopus.dao.SysUserRoleMapper;
import com.example.octopus.dao.experiment.SubExperimentReportSubmitMapper;
import com.example.octopus.entity.VOs.experiment.ReportAnalysisVO;
import com.example.octopus.entity.VOs.experiment.SubExperimentReportSummaryVO;
import com.example.octopus.entity.experiment.SubExperimentReportSubmit;
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
	public List<SubExperimentReportSummaryVO> listReportSummaryByRole(long teaNumber) {
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
		for (int i = 0; i < result.size(); ++i) {
			for (int j = 0; j < tem.size(); ++j)
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
	public List<SubExperimentReportSummaryVO> listClassReportSummaryByRoleAndSubExpId(long teaNumber, long subExpId){
		long role = sysUserRoleMapper.getRoleByUserId(teaNumber);
		List<SubExperimentReportSummaryVO> result, tem;
		if (role == 1) {
			//管理员获取全部
			result = reportSubmitMapper.listClassReportSummaryBySubExpId1(subExpId);
			tem = reportSubmitMapper.listClassReportSummaryBySubExpId2(subExpId);
		} else {
			result = reportSubmitMapper.listClassReportSummaryByTeaIdAndSubExpId1(teaNumber, subExpId);
			tem = reportSubmitMapper.listClassReportSummaryByTeaIdAndSubExpId2(teaNumber, subExpId);
		}

		for (int i = 0; i < result.size(); ++i) {
			for (int j = 0; j < tem.size(); ++j)
				if (tem.get(j).getClassId() == result.get(i).getClassId()) {
					result.get(i).setExaminedNum(tem.get(j).getExaminedNum());
					result.get(i).setUnexaminedNum(tem.get(j).getUnexaminedNum());
					int notSubmitNum = result.get(i).getNotSubmitNum() - tem.get(j).getExaminedNum() - tem.get(j).getUnexaminedNum();
					result.get(i).setNotSubmitNum(notSubmitNum);
					break;
				}
		}

		return result;
	}

	//子实验中，按照班级统计各分数段人数
	@Override
	public List<ReportAnalysisVO> listReportAnalysisByRoleAndSubExpId(long teaNumber, long subExpId) {
		long role = sysUserRoleMapper.getRoleByUserId(teaNumber);
		List<SubExperimentReportSubmit> classScores = (role == 1) ? reportSubmitMapper.listAllScoreBySubExpId(subExpId) : reportSubmitMapper.listScoreByTeaIdAndSubExpId(teaNumber, subExpId);
		if(classScores.isEmpty()) return null;  //防止classScores为空报错

		List<ReportAnalysisVO> reportAnalysis = new ArrayList<>();
		ReportAnalysisVO temReportAnalysis = new ReportAnalysisVO();
		int[] temAnalysis = {0, 0, 0, 0, 0, 0};  //0-60，60-70，70-80，80-90，90-95，95-100

		temReportAnalysis.setClassId(classScores.get(0).getClassId());
		temReportAnalysis.setClassName(classScores.get(0).getClassName());

		//classScores已经按照classId排好序
		for (int i = 0; i < classScores.size(); ++i) {
			//如果是新班级，则将原本的temReportAnalysis添加到reportAnalysis中
			if (i != 0 && classScores.get(i).getClassId() != temReportAnalysis.getClassId()) {
				temReportAnalysis.setScores(temAnalysis);
				reportAnalysis.add(temReportAnalysis);

				temReportAnalysis = new ReportAnalysisVO();
				temAnalysis = new int[]{0, 0, 0, 0, 0, 0};
				temReportAnalysis.setClassId(classScores.get(i).getClassId());
				temReportAnalysis.setClassName(classScores.get(i).getClassName());
			}

			//归并分数到相应分数段
			int s = classScores.get(i).getScore();
			if (s < 60) ++temAnalysis[0];
			else if (s < 70) ++temAnalysis[1];
			else if (s < 80) ++temAnalysis[2];
			else if (s < 90) ++temAnalysis[3];
			else if (s < 95) ++temAnalysis[4];
			else ++temAnalysis[5];
		}
		temReportAnalysis.setScores(temAnalysis);
		reportAnalysis.add(temReportAnalysis);

		return reportAnalysis;
	}

	@Override
	public ReportAnalysisVO getReportAnalysisByRoleAndSubExpIdAndClassId(long teaNumber, long subExpId, long classId){
		long role = sysUserRoleMapper.getRoleByUserId(teaNumber);
		List<SubExperimentReportSubmit> scores = (role==1)?reportSubmitMapper.listAllClassScoreBySubExpId(subExpId, classId):reportSubmitMapper.listClassScoreByTeaIdAndSubExpId(teaNumber, subExpId, classId);
		if(scores.isEmpty()) return null;  //防止scores为空报错

		ReportAnalysisVO reportAnalysis = new ReportAnalysisVO();
		int[] temAnalysis = {0, 0, 0, 0, 0, 0};  //0-60，60-70，70-80，80-90，90-95，95-100
		for(int i=0;i< scores.size();++i){
			int s = scores.get(i).getScore();
			if (s < 60) ++temAnalysis[0];
			else if (s < 70) ++temAnalysis[1];
			else if (s < 80) ++temAnalysis[2];
			else if (s < 90) ++temAnalysis[3];
			else if (s < 95) ++temAnalysis[4];
			else ++temAnalysis[5];
		}
		reportAnalysis.setClassId(scores.get(0).getClassId());
		reportAnalysis.setClassName(scores.get(0).getClassName());
		reportAnalysis.setScores(temAnalysis);
		return reportAnalysis;
	}

	@Override
	public List<SubExperimentReportSubmit> listReportScoreByRoleAndSubExpId(long teaNumber, long subExpId) {
		long role = sysUserRoleMapper.getRoleByUserId(teaNumber);
		return (role == 1) ? reportSubmitMapper.listAllScoreBySubExpId(subExpId) : reportSubmitMapper.listScoreByTeaIdAndSubExpId(teaNumber, subExpId);

	}
}

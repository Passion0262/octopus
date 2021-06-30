package com.example.octopus.service.impl;

import com.example.octopus.dao.SysUserRoleMapper;
import com.example.octopus.dao.experiment.VideoProgressMapper;
import com.example.octopus.dao.experiment.VideoMapper;
import com.example.octopus.entity.VOs.VideoStudySummaryVO;
import com.example.octopus.entity.experiment.VideoProgress;
import com.example.octopus.service.VideoProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/19 4:29 下午
 */
@Service
public class VideoProgressServiceImpl implements VideoProgressService {

	@Autowired
	VideoProgressMapper videoProgressMapper;

	@Autowired
	VideoMapper videoMapper;

	@Autowired
	SysUserRoleMapper sysUserRoleMapper;

	@Override
	public List<VideoProgress> listByStuNumber(long stuNumber) {
		return videoProgressMapper.listByStuNumber(stuNumber);
	}

	@Override
	public List<VideoProgress> listByCourseIdAndStuNumber(long courseId, long stuNumber) {
		List<Long> videoIdList = videoMapper.listVideoIdsByCourseId(courseId);
		List<VideoProgress> videoProgressList = new ArrayList<>();
		for (long videoId : videoIdList) {
			videoProgressList.add(videoProgressMapper.getByVideoIdAndStuNumber(videoId,stuNumber));
		}
		return videoProgressList;
	}

	@Override
	public int getStudyTimeByCourseIdAndStuNumber(long courseId, long stuNumber) {
		List<Long> videoIdList = videoMapper.listVideoIdsByCourseId(courseId);
		int total_time = 0;
		for (long videoId : videoIdList) {
			total_time += videoProgressMapper.getByVideoIdAndStuNumber(videoId, stuNumber).getStudyTime();
		}
		return total_time;
	}

	@Override
	public VideoProgress getById(long id) {
		return videoProgressMapper.getById(id);
	}

	@Override
	public VideoProgress getByVideoIdAndStuNumber(long videoId, long stuNumber) {
		return videoProgressMapper.getByVideoIdAndStuNumber(videoId, stuNumber);
	}

	@Override
	public boolean updateById(VideoProgress videoProgress) {
		return videoProgressMapper.updateById(videoProgress);
	}

	@Override
	public boolean insertVideoProgress(VideoProgress videoProgress) {
		return videoProgressMapper.insertVideoProgress(videoProgress);
	}

	@Override
	public List<VideoStudySummaryVO> getVideoStudySummaryByRole(long teaNumber) {
		long role = sysUserRoleMapper.getRoleByUserId(teaNumber);
		if (role == 1) {
			return videoProgressMapper.getAllVideoStudySummary();
		} else return videoProgressMapper.getVideoStudySummaryByTeacherId(teaNumber);
	}

	@Override
	public double getCourseProgress(long courseId, long stuNumber) {
		List<Long> videoIdList = videoMapper.listVideoIdsByCourseId(courseId);
		double stu_progress = 0;		//学生学习进度
		double course_progress = videoIdList.size()*100; 	//课程进度总和
		for (long videoId : videoIdList) {
			VideoProgress vp = videoProgressMapper.getByVideoIdAndStuNumber(videoId,stuNumber);
			if (vp!=null){
				stu_progress += vp.getProgress();
			}
		}
		double result = stu_progress/course_progress;
		BigDecimal b = new BigDecimal(result);
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();	//保留2位小数
	}
}

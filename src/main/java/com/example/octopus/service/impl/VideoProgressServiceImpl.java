package com.example.octopus.service.impl;

import com.example.octopus.dao.SysUserRoleMapper;
import com.example.octopus.dao.experiment.VideoProgressMapper;
import com.example.octopus.dao.experiment.VideoMapper;
import com.example.octopus.entity.VOs.VideoProgressDetailVO;
import com.example.octopus.entity.VOs.VideoProgressHistoryVO;
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
			videoProgressList.addAll(videoProgressMapper.listByVideoIdAndStuNumber(videoId, stuNumber));
		}
		return videoProgressList;
	}

	@Override
	public List<VideoProgress> listLatestByCourseIdAndStuNumber(long courseId, long stuNumber) {
		List<Long> videoIdList = videoMapper.listVideoIdsByCourseId(courseId);
		List<VideoProgress> videoProgressList = new ArrayList<>();
		for (long videoId : videoIdList) {
			videoProgressList.add(videoProgressMapper.getLatestByVideoIdAndStuNumber(videoId, stuNumber));
		}
		return videoProgressList;
	}

	@Override
	public int getStudyTimeByCourseIdAndStuNumber(long courseId, long stuNumber) {
		List<Long> videoIdList = videoMapper.listVideoIdsByCourseId(courseId);
		int total_time = 0;
		for (long videoId : videoIdList) {
			Integer cur_time = videoProgressMapper.countStudyTime(videoId, stuNumber);
			if (cur_time != null) {
				total_time += cur_time;
			}
		}
		return total_time;
	}

	@Override
	public VideoProgress getById(long id) {
		return videoProgressMapper.getById(id);
	}

	@Override
	public VideoProgress getByVideoIdAndStuNumber(long videoId, long stuNumber) {
		return videoProgressMapper.getLatestByVideoIdAndStuNumber(videoId, stuNumber);
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
	public List<VideoProgressHistoryVO> getVideoProgressHistoryByRole(long teaNumber) {
		long role = sysUserRoleMapper.getRoleByUserId(teaNumber);
		if (role == 1) {
			//管理员获取全部
			return videoProgressMapper.getAllVideoStudyDetail();
		} else return videoProgressMapper.getVideoStudyDetailByTeacherId(teaNumber);
	}

	@Override
	public double getCourseProgress(long courseId, long stuNumber) {
		List<Long> videoIdList = videoMapper.listVideoIdsByCourseId(courseId);
		if (videoIdList.size() == 0) {
			return 0.0;
		}
		double stu_progress = 0;        //学生学习进度
		double course_progress = videoIdList.size() * 100;    //课程进度总和
		for (long videoId : videoIdList) {
			VideoProgress vp = videoProgressMapper.getLatestByVideoIdAndStuNumber(videoId, stuNumber);
			if (vp != null) {
				stu_progress += vp.getProgress();
			}
		}
		double result = stu_progress / course_progress;
		BigDecimal b = new BigDecimal(result);
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();    //保留2位小数
	}

	@Override
	public List<VideoProgressDetailVO> getVideoProgressDetailByRole(long teaNumber) {
		long role = sysUserRoleMapper.getRoleByUserId(teaNumber);
		if (role == 1) {
			//管理员获取全部
			return videoProgressMapper.getAllVideoProgressDetail();
		} else return videoProgressMapper.getVideoProgressDetailByTeacherId(teaNumber);
	}
}

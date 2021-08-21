package com.example.octopus.service.impl;

import com.example.octopus.dao.CourseMapper;
import com.example.octopus.dao.SysUserRoleMapper;
import com.example.octopus.dao.experiment.VideoProgressMapper;
import com.example.octopus.dao.experiment.VideoMapper;
import com.example.octopus.entity.VOs.*;
import com.example.octopus.entity.VOs.video.VideoProgressDetailVO;
import com.example.octopus.entity.VOs.video.VideoProgressHistoryVO;
import com.example.octopus.entity.VOs.video.VideoTimeHistoryVO;
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
	CourseMapper courseMapper;

	@Autowired
	SysUserRoleMapper sysUserRoleMapper;

	@Override
	public List<VideoProgress> listByStuNumber(long stuNumber) {
		return videoProgressMapper.listByStuNumber(stuNumber);
	}

	@Override
	public List<CourseTimeVO> countStudyTimeByStuNumberGroupByCourseId(long stuNumber) {
		return videoProgressMapper.countStudyTimeByStuNumberGroupByCourseId(stuNumber);
	}

	@Override
	public List<VideoProgress> listByCourseIdAndStuNumber(long courseId, long stuNumber) {
		List<Long> videoIdList = videoMapper.listVideoIdsByCourseStaticId(courseId);
		List<VideoProgress> videoProgressList = new ArrayList<>();
		for (long videoId : videoIdList) {
			videoProgressList.addAll(videoProgressMapper.listByVideoIdAndStuNumber(videoId, stuNumber));
		}
		return videoProgressList;
	}

	@Override
	public List<VideoProgress> listLatestByCourseIdAndStuNumber(long courseId, long stuNumber) {
		List<Long> videoIdList = videoMapper.listVideoIdsByCourseStaticId(courseId);
		List<VideoProgress> videoProgressList = new ArrayList<>();
		for (long videoId : videoIdList) {
			videoProgressList.add(videoProgressMapper.getLatestByVideoIdAndStuNumber(videoId, stuNumber));
		}
		return videoProgressList;
	}

	@Override
	public List<VideoTimeHistoryVO> getHistory7DaysStudyTime(long stuNumber) {
		return videoProgressMapper.getHistory7DaysStudyTime(stuNumber);
	}

	@Override
	public int getStudyTimeByCourseIdAndStuNumber(long courseId, long stuNumber) {
		List<Long> videoIdList = videoMapper.listVideoIdsByCourseStaticId(courseId);
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
		VideoProgress vp1 = videoProgressMapper.getLatestByVideoIdAndStuNumber(videoProgress.getVideoId(),videoProgress.getStuNumber());
		long a = videoProgress.getStartTime().getTime() - vp1.getEndTime().getTime();
//		System.out.println(vp1.getEndTime());
//		System.out.println(vp1.getEndTime().getTime());
//		System.out.println(videoProgress.getStartTime());
//		System.out.println(videoProgress.getStartTime().getTime());
//		System.out.println(a);
		if (videoProgress.getStartTime().getTime() - vp1.getEndTime().getTime() < 60000 && videoProgress.getStartTime().getTime() - vp1.getEndTime().getTime()>0){
//			System.out.println("dasdas");
			VideoProgress vp2 = new VideoProgress();
			vp2.setId(vp1.getId());
			vp2.setTeaCourseId(vp1.getTeaCourseId());
			vp2.setVideoId(vp1.getVideoId());
			vp2.setStuNumber(vp1.getStuNumber());
			vp2.setStartTime(vp1.getStartTime());
			vp2.setEndTime(videoProgress.getEndTime());
			vp2.setProgress(videoProgress.getProgress());
			vp2.setLastVideoProgress(videoProgress.getLastVideoProgress());
//			Long ltime = videoProgress.getEndTime().getTime() - vp1.getStartTime().getTime();
//			int ltime1 = ltime.intValue();
			int ltime1 = videoProgress.getStudyTime()+vp1.getStudyTime();
			vp2.setStudyTime(ltime1);
			videoProgressMapper.updateById(vp2);
		}else{
			return videoProgressMapper.insertVideoProgress(videoProgress);
		}
		return false;
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
		long courseStaticId = courseMapper.getCourseById(courseId).getCourseStaticId();
		List<Long> videoIdList = videoMapper.listVideoIdsByCourseStaticId(courseStaticId);
		if (videoIdList.size() == 0) {
			return 0.0;
		}
		double stu_progress = 0;        //学生学习进度
		double course_progress = videoIdList.size() * 100;    //课程进度总和
		for (long videoId : videoIdList) {
			VideoProgress vp = videoProgressMapper.getLatestByVideoIdAndStuNumber(videoId, stuNumber);
			if (vp != null) {
				System.out.println(vp.getVideoId() + "" + vp.getProgress());
				stu_progress += vp.getProgress();
			}
		}
		double result = stu_progress / course_progress;
		if (result>0.99){
			courseMapper.updateCompleted(courseId,stuNumber);
		}
		BigDecimal b = new BigDecimal(result);
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();    //保留2位小数
	}

	@Override
	public List<VideoProgressDetailVO> getVideoProgressDetailByRole(long teaNumber) {
		long role = sysUserRoleMapper.getRoleByUserId(teaNumber);
		List<VideoProgressDetailVO> result;
		if (role == 1) {
			//管理员获取全部
			result= videoProgressMapper.getAllVideoProgressDetail();
		} else result=videoProgressMapper.getVideoProgressDetailByTeacherId(teaNumber);
		int len = result.size();
		for (int i=0; i<len; ++i){
			result.get(i).sec2time();
		}
		return result;
	}
}

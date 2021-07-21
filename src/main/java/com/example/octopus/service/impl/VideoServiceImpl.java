package com.example.octopus.service.impl;

import com.example.octopus.dao.SysUserRoleMapper;
import com.example.octopus.dao.experiment.VideoMapper;
import com.example.octopus.dao.experiment.VideoSubExperimentMapper;
import com.example.octopus.entity.VOs.VideoManageVO;
import com.example.octopus.entity.VOs.VideoStudySummaryVO;
import com.example.octopus.entity.experiment.Video;
import com.example.octopus.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/10 7:11 下午
 */
@Service
public class VideoServiceImpl implements VideoService {

	@Autowired
	VideoMapper videoMapper;

	@Autowired
	VideoSubExperimentMapper videoSubExperimentMapper;

	@Autowired
	SysUserRoleMapper sysUserRoleMapper;

	@Override
	public List<Video> listVideos() {
		return videoMapper.listVideos();
	}

	@Override
	public List<Video> listVideosByCourseId(long courseId) {
		return videoMapper.listVideosByCourseId(courseId);
	}

	@Override
	public List<Video> listVideosByChapterId(long chapterId) {
		return videoMapper.listVideosByChapterId(chapterId);
	}

	@Override
	public Video getById(long id) {
		return videoMapper.getById(id);
	}

	@Override
	public Long getVideoIdBySubExperimentId(long subExperimentId) {
		return videoSubExperimentMapper.getVideoIdBySubExperimentId(subExperimentId);
	}

	@Override
	public Video getVideoBySubExperimentId(long subExperimentId) {
		Long videoId = getVideoIdBySubExperimentId(subExperimentId);
		if (videoId == null) {
			return null;
		} else {
			return getById(videoId);
		}
	}

	@Override
	public List<VideoManageVO> getVideoManageInfoByRole(long teaNumber) {
		long role = sysUserRoleMapper.getRoleByUserId(teaNumber);
		if (role == 1) {
			//管理员获取全部
			return videoMapper.getAllVideoManage();
		} else return videoMapper.getVideoManageByTeaId(teaNumber);
	}

	@Override
	public boolean addVideo(Video video) {
		List<Video> existVideos = videoMapper.getVideoByCourseIdAndChapterIdAndNumber(video.getNumber(), video.getChapterId(), video.getCourseId());
		//如是新加入的，就直接添加整个实体
		if (existVideos.isEmpty()) return videoMapper.addVideo(video);
			//如已经有同课程同章节同编号的视频，则为其更新名字与地址
		else return videoMapper.updateNameAndPathById(existVideos.get(0).getId(), video.getName(), video.getPath());
	}

	@Override
	public boolean updateVideo(Video video) {
		//根据传入的videoId来查看表中有无相关数据
		Video old = videoMapper.getById(video.getId());
		//根据传入的课程号章节号小节号定位
		List<Video> existVideos = videoMapper.getVideoByCourseIdAndChapterIdAndNumber(video.getNumber(), video.getChapterId(), video.getCourseId());

		//如表中已有此videoId，则直接删除旧数据添加新数据
		if (old != null) {
			videoMapper.deleteVideoById(old.getId());
			return videoMapper.addVideo(video);
		}
		//如无此videoId，但表中有课程、章节、小节号都对应的数据，则对这条数据进行修改
		else if (!existVideos.isEmpty()) {
			return videoMapper.updateNameAndPathById(existVideos.get(0).getId(), video.getName(), video.getPath());
		}
		//如无此videoId，也在表中没有课程、章节、小节号都对应的数据，则直接添加这条数据（videoId由数据库自动生成）
		else {
			return videoMapper.addVideo(video);
		}
	}

	@Override
	public boolean deleteVideoById(long id) {
		return videoMapper.deleteVideoById(id);
	}

	@Override
	public List<VideoStudySummaryVO> getVideoStudySummaryByRole(long teaNumber) {
		long role = sysUserRoleMapper.getRoleByUserId(teaNumber);

		List<VideoStudySummaryVO> result;
		if (role == 1) {
			//管理员获取全部
			result =  videoMapper.getAllVideoStudySummary();
		} else result = videoMapper.getVideoStudySummaryByTeacherId(teaNumber);

		int len = result.size();
		for (int i=0; i<len; ++i){
			result.get(i).sec2time();
		}
		return result;
	}
}

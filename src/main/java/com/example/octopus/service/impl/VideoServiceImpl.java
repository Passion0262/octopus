package com.example.octopus.service.impl;

import com.example.octopus.dao.experiment.VideoMapper;
import com.example.octopus.dao.experiment.VideoSubExperimentMapper;
import com.example.octopus.entity.VOs.VideoManageVO;
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
	public List<VideoManageVO> getAllVideoManageInfo() {
		return videoMapper.getAllVideoManage();
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
		Video old = videoMapper.getById(video.getId());
		List<Video> existVideos = videoMapper.getVideoByCourseIdAndChapterIdAndNumber(video.getNumber(), video.getChapterId(), video.getCourseId());

		if (!existVideos.isEmpty() && old.getId() == existVideos.get(0).getId()) {
			//只修改了名或地址，使用update，不删除
			return videoMapper.updateNameAndPathById(video.getId(), video.getName(), video.getPath());
		} else {
			videoMapper.deleteVideoById(old.getId());
			return videoMapper.addVideo(video);
		}
	}

	@Override
	public boolean deleteVideoById(long id) {
		return videoMapper.deleteVideoById(id);
	}
}

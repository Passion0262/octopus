package com.example.octopus.service.impl;

import com.example.octopus.dao.experiment.VideoQuestionMapper;
import com.example.octopus.entity.experiment.VideoQuestion;
import com.example.octopus.service.VideoQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Hao
 * @date: 2021/8/7 17:10
 */
@Service
public class VideoQuestionServiceImpl implements VideoQuestionService {
	@Autowired
	VideoQuestionMapper videoQuestionMapper;

	@Override
	public List<VideoQuestion> listQuestionsByVideoId(long videoId){
		return videoQuestionMapper.listQuestionByVideoId(videoId);
	}
}

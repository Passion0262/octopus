package com.example.octopus.service;

import com.example.octopus.entity.experiment.VideoQuestion;

import java.util.List;

/**
 * 服务接口：视频后小测验具体题目
 * @author: Hao
 * @date: 2021/8/7 17:08
 */

public interface VideoQuestionService {
	/**
	 * 通过视频号获取其下对应小测验的所有具体题目
	 */
	List<VideoQuestion> listQuestionsByVideoId(long videoId);
}

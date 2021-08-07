package com.example.octopus.dao.experiment;

import com.example.octopus.entity.experiment.VideoQuestion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author: Hao
 * @date: 2021/8/7 17:11
 */
@Mapper
public interface VideoQuestionMapper {
	@Select("SELECT * FROM video_question WHERE video_id=#{videoId}")
	List<VideoQuestion> listQuestionByVideoId(long videoId);
}

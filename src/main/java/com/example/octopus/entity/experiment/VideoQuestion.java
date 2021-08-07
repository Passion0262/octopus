package com.example.octopus.entity.experiment;

import lombok.Data;

/**
 * 视频后小测验具体题目
 * @author: Hao
 * @date: 2021/8/6 12:57
 */

@Data
public class VideoQuestion {
	private long id;
	private long videoId;
	private String question;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionAnswer;

}

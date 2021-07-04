package com.example.octopus.entity.VOs;

import com.example.octopus.entity.experiment.Video;
import lombok.Data;

/**
 * @author: Hao
 * @date: 2021/7/4 20:34
 * 视频课程管理视图类
 */
@Data
public class VideoManageVO extends Video {
	private String chapterName;  //章节名
	private String courseName;  //课程名
}

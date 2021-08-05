package com.example.octopus.entity.VOs;

import com.example.octopus.entity.experiment.Video;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 视频课程管理视图类，相关操作存放位置与Video相同
 * @author: Hao
 * @date: 2021/7/4 20:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoManageVO extends Video {
	private String chapterName;  //章节名
	private int chapterNumber;  //章节号
	private String courseName;  //课程名
}

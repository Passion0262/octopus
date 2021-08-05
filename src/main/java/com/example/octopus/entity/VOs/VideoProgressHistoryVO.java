package com.example.octopus.entity.VOs;

import com.example.octopus.entity.experiment.VideoProgress;
import lombok.Data;

/**
 * 视频学习历史记录视图类，相关操作在videoProcess的service、impl、mapper中
 * @author: Hao
 * @date: 2021/6/26 11:32
 */

@Data
public class VideoProgressHistoryVO extends VideoProgress {
	private String name;  //学生名
	private String className;  //班级名
	private String majorName;  //专业名
	private String courseName;  //课程名
}

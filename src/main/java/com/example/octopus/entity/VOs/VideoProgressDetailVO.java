package com.example.octopus.entity.VOs;

import lombok.Data;
import org.bouncycastle.util.Times;

import java.sql.Timestamp;

/**
 * @author: Hao
 * @date: 2021/7/5 18:54
 * 视频学习详情视图类
 * 相关操作在videoProcess的service、impl、mapper中
 */
@Data
public class VideoProgressDetailVO {
	private long stuNumber;
	private String name;
	private long videoId;
	private String videoName;
	private long courseId;
	private String courseName;
	private int studyTimeSum;  //该学生学习此视频的时长
	private Timestamp veryStartTime;  //首次学习时间
	private Timestamp veryLastTime;  //末次学习时间
}

package com.example.octopus.entity.videoStudy;

import com.example.octopus.entity.user.Course;
import lombok.Data;

import javax.persistence.*;


/**
 * @author: Hao
 * @date: 2021/5/22 12:19
 * 视频学习原始内容表
 */
@Entity
@Data
@Table
public class VideoStudyOrigin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    @ManyToOne
//    @JoinColumn(name = "courseId")
//    private Course course;  //视频所属课程

    private String videoPath;  //视频存放地址

    private int videoChapterNo;  //视频所属章节号

    private String videoChapterName;  //视频所属章节名

    private int videoNo;  //视频所属小节号

    private String videoName;  //视频名称

}

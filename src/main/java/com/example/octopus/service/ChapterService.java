package com.example.octopus.service;

import com.example.octopus.entity.experiment.Chapter;

import java.util.List;

public interface ChapterService {
    /**
     * 根据static_courseId查询章节
     * @param staticCourseId  课程id
     * @return chapter list
     */
    List<Chapter> listChaptersByCourseId(long staticCourseId) ;
}

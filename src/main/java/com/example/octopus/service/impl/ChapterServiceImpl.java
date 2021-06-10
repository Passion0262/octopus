package com.example.octopus.service.impl;

import com.example.octopus.dao.ChapterMapper;
import com.example.octopus.entity.experiment.Chapter;
import com.example.octopus.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterMapper chapterMapper;

    @Override
    public List<Chapter> listChapterByCourseId(long courseId) {
        return chapterMapper.listChapterByCourseId(courseId);
    }
}

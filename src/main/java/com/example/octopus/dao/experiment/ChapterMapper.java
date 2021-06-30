package com.example.octopus.dao.experiment;

import com.example.octopus.entity.experiment.Chapter;
import com.example.octopus.entity.experiment.Module;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChapterMapper {

    /**
     * 根据课程Id查询章节
     * @param courseId 课程id
     * @return chapter list
     */
    @Select("SELECT * FROM chapter where course_id = #{courseId} order by chapter_number")
    List<Chapter> listChaptersByCourseId(long courseId) ;
}

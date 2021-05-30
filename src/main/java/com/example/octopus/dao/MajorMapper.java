package com.example.octopus.dao;

import com.example.octopus.entity.user.Course;
import com.example.octopus.entity.user.Major;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/5/28 5:19 下午
 * @modified By：
 */
@Mapper
public interface MajorMapper {

    /**
     * 查询所有专业并返回
     * @return 课程list
     */
    @Select("SELECT * FROM major")
    List<Major> queryAllMajors();

    /**
     * 如果majorid存在则更新，如果不存在则添加。
     * @param major  专业实体
     * @return 成功为true，失败为false
     */
    boolean saveCourse(Major major);

    /**
     * 根据majorid删除专业
     * @param majorid 专业id
     * @return 成功为true，失败为false
     */
    boolean deleteMajorById(long majorid);

}

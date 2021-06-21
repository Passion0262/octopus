package com.example.octopus.dao;

import com.example.octopus.entity.user.Course;
import com.example.octopus.entity.user.Major;
import org.apache.ibatis.annotations.*;
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
    List<Major> listMajors();

    /**
     * 根据majorCode查找major
     * @return Major实体
     */
    @Select("SELECT * FROM major WHERE major_code = #{majorCode}")
    Major getByMajorCode(String majorCode);

    /**
     * 根据id查找Major
     * @param id Major id
     * @return Major实体
     */
    @Select("SELECT * FROM major WHERE id = #{id}")
    Major getById(long id);

    /**
     *  根据id获取majorName
     */
    @Select("SELECT major_name FROM major WHERE id = #{id}")
    String getNameById(long id);

    /**
     * 新增专业
     * @param major major实体
     * @return 成功为true，失败为false
     */
    @Insert("INSERT INTO major(major_code,major_name,create_time,creator) VALUES (#{majorCode},#{majorName},CURRENT_TIMESTAMP,#{creator})")
    boolean insertMajor(Major major);

    /**
     * 更新Major,根据id查询
     * @param major 专业实体
     * @return 成功为true，失败为false
     */
    @Update("UPDATE major SET major_code=#{majorCode}, major_name=#{majorName}, creator = #{creator} WHERE id = #{id}")
    boolean updateMajor(Major major);

    /**
     * 根据id删除专业
     * @param id 专业id
     * @return 成功为true，失败为false
     */
    @Delete("DELETE FROM major WHERE id = #{id}")
    boolean deleteMajorById(long id);


}

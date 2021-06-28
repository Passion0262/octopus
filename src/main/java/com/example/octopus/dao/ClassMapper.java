package com.example.octopus.dao;

import com.example.octopus.entity.user.Class_;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/8 2:52 下午
 */
@Mapper
public interface ClassMapper {

    /**
     * 新增班级
     * @param class_ 班级实体
     * @return 成功为true，失败为false
     */
    @Insert("INSERT INTO class_(class_name, major_id, creator, create_time) VALUES (#{className},#{majorId},#{creator},CURRENT_TIMESTAMP)")
    boolean insertClass(Class_ class_);

    /**
     * 查找所有的班级
     * @return class_ list
     */
    @Select("SELECT c.*, m.major_name FROM class_ c, major m WHERE c.major_id=m.id")
    List<Class_> listClass_s();

    /**
     * 根据id查找班级
     * @param id 班级id
     * @return class_实体
     */
    @Select("SELECT c.*, m.major_name FROM class_ c, major m WHERE c.id = #{id} and c.major_id=m.id")
    Class_ getById(long id);

    /**
     *  根据id查找className
     */
    @Select("SELECT class_name FROM class_ WHERE id = #{id}")
    String getNameById(long id);

    /**
     * 根据className删除班级
     * @param className 班级名称
     * @return 成功为true，失败为false
     */
    @Delete("DELETE FROM class_ WHERE class_name = #{className}")
    boolean deleteByClassName(String className);

    @Delete("DELETE FROM class_ WHERE id = #{classId}")
    boolean deleteByClassId(long classId);

    @Update("UPDATE class_ SET class_name=#{className}, major_id=#{majorId}, creator=#{creator} WHERE id = #{id}")
    boolean updateClass(Class_ class_);

}

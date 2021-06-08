package com.example.octopus.dao;

import com.example.octopus.entity.user.Class_;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/8 2:52 下午
 */
@Mapper
public interface Class_Mapper {

    /**
     * 查找所有的班级
     * @return class_ list
     */
    @Select("SELECT * FROM class_")
    List<Class_> listClass_s();

    /**
     * 根据id查找班级
     * @param id 班级id
     * @return class_实体
     */
    @Select("SELECT * FROM class_ WHERE id = #{id}")
    Class_ getById(long id);

}

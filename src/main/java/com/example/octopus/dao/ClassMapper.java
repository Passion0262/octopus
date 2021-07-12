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
     */
    @Insert("INSERT INTO class_(class_name, major_id, school, creator, create_time) VALUES (#{className},#{majorId},#{school},#{creator},CURRENT_TIMESTAMP)")
    boolean insertClass(Class_ class_);

    /**
     *  查询班级是否已存在
     */
    @Select("SELECT * FROM class_ WHERE class_name=#{className}AND school=#{school}")
    boolean isExist(String className,String school);

    /**
     * 查找所有的班级
     */
    @Select("SELECT c.*, m.major_name FROM class_ c, major m WHERE c.major_id=m.id")
    List<Class_> listClass_s();

    /**
     * 根据id查找班级
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
     */
    @Delete("DELETE FROM class_ WHERE class_name = #{className}")
    boolean deleteByClassName(String className);

    @Delete("DELETE FROM class_ WHERE id = #{classId}")
    boolean deleteByClassId(long classId);

    @Update("UPDATE class_ SET class_name=#{className}, major_id=#{majorId}, creator=#{creator} WHERE id = #{id}")
    boolean updateClass(Class_ class_);

    @Select("SELECT c.*, m.major_name FROM class_ c, major m WHERE c.major_id=m.id and c.major_id=#{majorId}")
    List<Class_> listClass_sByMajorId(long majorId);

    @Select("SELECT c.*, m.major_name FROM class_ c, major m WHERE c.major_id=m.id and c.school=#{school}")
    List<Class_> listSchoolClasses(String school);

    @Select("SELECT c.*, m.major_name FROM class_ c, major m WHERE c.major_id=#{majorId} and c.school=#{school} and c.major_id=m.id")
    List<Class_> listSchoolMajorClasses(String school, long majorId);
}

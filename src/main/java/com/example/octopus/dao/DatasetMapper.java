package com.example.octopus.dao;

import com.example.octopus.entity.dataset.Dataset;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/2 4:57 下午
 */
@Mapper
public interface DatasetMapper {

    @Select("SELECT * FROM dataset")
    List<Dataset> queryAllDataset();

}

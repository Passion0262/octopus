package com.example.octopus.dao;

import com.example.octopus.entity.dataset.Dataset;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/2 4:57 下午
 */
@Mapper
public interface DatasetMapper {

    /**
     * 查找所有的数据集
     * @return 数据集list
     */
    @Select("SELECT * FROM dataset")
    List<Dataset> listDatasets();

    /**
     * 根据id查找数据集
     * @param id 数据集id
     * @return 数据集实体
     */
    @Select("SELECT * FROM dataset WHERE id = #{id}")
    Dataset getDatasetById(long id);

    @Delete("DELETE FROM dataset WHERE id=#{id}")
    boolean deleteDatasetById(long id);

    /**
     *  下载次数+1
     */
    @Update("UPDATE dataset SET download_num = download_num+1 WHERE id = #{id}")
    boolean updateDownloadNumById(long id);

    @Insert("INSERT INTO dataset (name, description, uploader, size, download_num, download_path, file_format, image_path) " +
            "VALUES (#{name}, #{description}, #{uploader}, #{size}, 0, #{downloadPath}, #{fileFormat}, #{imagePath})")
    boolean addDataset(Dataset dataset);
}

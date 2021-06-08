package com.example.octopus.entity.dataset;

import lombok.Data;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/2 4:55 下午
 */
@Data
public class Dataset {

    long id;

    String name;

    String description;

    String uploader;

    int size;

    int downloadNum;

    String fileFormat;

    String imagePath;

}

package com.example.octopus.entity.dataset;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/2 4:55 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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

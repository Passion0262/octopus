package com.example.octopus.entity.project;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ：shadow
 * @date ：Created in 2021/7/1 5:06 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectModule {

    private long moduleId;

    private long projectId;

    private int moduleNumber;

    private String moduleName;

}

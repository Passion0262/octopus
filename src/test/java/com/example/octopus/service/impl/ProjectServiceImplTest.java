package com.example.octopus.service.impl;

import com.example.octopus.entity.project.Project;
import com.example.octopus.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/2 4:47 下午
 */
@SpringBootTest
@RunWith(SpringRunner.class)
class ProjectServiceImplTest {

    @Autowired
    ProjectService projectService;

    @Test
    void findAllProject() {
        List<Project> list = projectService.findAllProject();
        assertEquals(1,list.size());
    }
}
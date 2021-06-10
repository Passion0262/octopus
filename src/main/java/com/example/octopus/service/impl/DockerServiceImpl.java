package com.example.octopus.service.impl;

import com.example.octopus.dao.DockerMapper;
import com.example.octopus.entity.Docker;
import com.example.octopus.entity.user.Student;
import com.example.octopus.service.DockerService;
import com.example.octopus.service.UserService;
import com.example.octopus.utils.ShellUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author: Hao
 * @date: 2021/6/7 15:43
 */

@Service
public class DockerServiceImpl implements DockerService {

    private static ShellUtils shellUtils = new ShellUtils();

    @Autowired
    private DockerMapper dockerMapper;

    @Autowired
    private UserService userService;

    @Override
    public boolean existsIdDocker(long id) {
        Docker docker = findDockerByStuNumber(id);
        return false ? docker == null : true;
    }

    @Override
    public boolean existsStuNumDocker(long stuNumber) {
        Student student = userService.getStudentByStuNumber(stuNumber);
        if (student != null) {
            Docker docker = findDockerByStuNumber(stuNumber);
            return docker != null;
        }
        return false;
    }


    /**
     * 获取所有docker信息
     *
     * @return docker信息列表
     */
    @Override
    public List<Docker> getDockerList() {
        return dockerMapper.listAllDockers();
    }


    /**
     * 通过docker id获取docker信息
     *
     * @param id docker的id
     * @return 该docker信息
     */
    @Override
    public Docker findDockerById(long id) {
        return dockerMapper.getDockerById(id);
    }

    /**
     * 通过学生号检索docker信息
     *
     * @param stuNumber 学生id
     * @return 该docker信息
     */
    @Override
    public Docker findDockerByStuNumber(long stuNumber) {
        return dockerMapper.getDockerByStuNum(stuNumber);
    }

    /**
     * 通过学生号获取docker的完整ip地址
     *
     * @param stuNumber 学生号
     * @return 完整ip地址，如 http://172.18.146.123:6081/#/
     */
    @Override
    public String getAddressByStuNumber(long stuNumber) {
        return dockerMapper.getDockerByStuNum(stuNumber).getAddress();
    }

    /**
     * 通过学生号获取其虚拟机状况（未在使用、在使用、无虚拟机）
     *
     * @param stuNumber 学生号
     * @return 返回虚拟机容器状况
     */
    @Override
    public String getStatusByStuNumber(long stuNumber) {
        return dockerMapper.getDockerByStuNum(stuNumber).getStatus();
    }

    private static final int[] FORBIDDEN_PORTS = {8080, 3306};

    @Override
    public boolean createNewDocker(long stuNumber) {

        if (existsStuNumDocker(stuNumber)) {
            System.out.println("该学生已有docker容器了，不需要再创建");
            return false;
        }

        List<String> ports = dockerMapper.getAllPorts();  //获取所有端口

        String stuName = userService.getStudentByStuNumber(stuNumber).getName();
        String dockerName = String.valueOf(stuNumber);
        int dockerPort = Integer.valueOf(Collections.max(ports)) + 1;
        for (int i = 0; i < FORBIDDEN_PORTS.length; i++) {
            if (dockerPort == FORBIDDEN_PORTS[i]) {
                dockerPort++;
                i--;
            }
        }

        String dockerCommand = "docker run -idt -p " + dockerPort + "-e USER=test -e PASSWORD=123 --name "
                + dockerName + "  centminmod/docker-ubuntu-vnc-desktop";
        shellUtils.sshRemoteCallLogin();
        shellUtils.execCommand(dockerCommand);
        shellUtils.closeSession();


        String address = "http://172.18.146.123:" + String.valueOf(dockerPort) + "/#/";
        try {
            dockerMapper.createDocker(dockerName, dockerPort, stuNumber, stuName, address);
            return true;
        } catch (Exception e) {
            throw e;
        }

    }
}

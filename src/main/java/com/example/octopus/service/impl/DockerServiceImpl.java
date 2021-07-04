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

    private static final int[] FORBIDDEN_PORTS = {8080, 3306};

    private static ShellUtils shellUtils = new ShellUtils();

    @Autowired
    private DockerMapper dockerMapper;

    @Autowired
    private UserService userService;

    @Override
    public boolean existsStuNumDocker(long stuNumber) {
        Student student = userService.getStudentByStuNumber(stuNumber);
        if (student != null) {
            Docker docker = findDockerByStuNumber(stuNumber);
            return docker != null;
        }
        return false;
    }




    @Override
    public List<Docker> getDockerList() {
        return dockerMapper.listAllDockers();
    }


    @Override
    public Docker findDockerById(long id) {
        return dockerMapper.getDockerById(id);
    }

    @Override
    public Docker findDockerByStuNumber(long stuNumber) {
        return dockerMapper.getDockerByStuNum(stuNumber);
    }


    @Override
    public String getAddressByStuNumber(long stuNumber) {
        return dockerMapper.getDockerByStuNum(stuNumber).getDockerAddress();
    }

    @Override
    public String[] getStatusByStuNum(long stuNumber) {
        String[] result = new String[2];
        Docker docker = dockerMapper.getDockerByStuNum(stuNumber);
        result[0] = docker.getDockerStatus();
        result[1] = String.valueOf(docker.getProcessingId());
        return result;
    }

    @Override
    public boolean updateStatusByStuNum(long stuNumber, int statusCode, long processingId){
        String status=null;
        if (statusCode==0) {
            status="sleeping";
            processingId=0;
        }
        else if(statusCode==1) status="project";
        else if(statusCode==2) status="experiment";
        else return false;
        return dockerMapper.updateStatusByStuNum(stuNumber, status, processingId);
    }


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

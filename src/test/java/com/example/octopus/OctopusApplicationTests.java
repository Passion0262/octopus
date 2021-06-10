package com.example.octopus;

import com.example.octopus.service.DockerService;
import com.example.octopus.utils.ShellUtils;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OctopusApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    DockerService dockerService;

    @Test
    void dockerTest() throws JSchException, SftpException {
//        System.out.println(dockerService.getDockerList());
//        dockerService.findDockerById(111);
        dockerService.createNewDocker(2);
        ShellUtils shellUtils = new ShellUtils();

        shellUtils.sshRemoteCallLogin();
        shellUtils.execCommand("ls");
        shellUtils.closeSession();

    }

}

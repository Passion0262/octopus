package com.example.octopus;

import com.example.octopus.dao.ClassMapper;
import com.example.octopus.dao.TeacherMapper;
import com.example.octopus.dao.UserMapper;
import com.example.octopus.entity.user.Student;
import com.example.octopus.entity.user.Teacher;
import com.example.octopus.service.*;
import com.example.octopus.utils.ShellUtils;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
//import org.graalvm.compiler.lir.LIRInstruction;
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

	@Autowired
	ClassMapper classMapper;
	@Autowired
	UserMapper userMapper;

	@Autowired
	TeacherMapper teacherMapper;

	@Autowired
	ClassService classService;
	@Autowired
	StudentCourseService studentCourseService;
	@Autowired
	UserService userService;
	@Autowired
	TeacherService tea;

	@Test
	void interfaceTest() {

        Teacher teacher = new Teacher(6, "LuBenwei", "lbwnb", 7, false, "33333333333", "卢本伟广场学院");
		System.out.println(tea.updateTeacher(teacher));
	}


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

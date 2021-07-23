package com.example.octopus;

import com.example.octopus.dao.ClassMapper;
import com.example.octopus.dao.TeacherMapper;
import com.example.octopus.dao.UserMapper;
import com.example.octopus.dao.experiment.ChapterMapper;
import com.example.octopus.dao.experiment.ModuleMapper;
import com.example.octopus.dao.experiment.SubExperimentMapper;
import com.example.octopus.dao.experiment.VideoMapper;
import com.example.octopus.entity.experiment.Video;
import com.example.octopus.entity.user.Major;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
	@Autowired
	MajorService majorService;

	@Autowired
	ModuleMapper moduleMapper;
	@Autowired
	ChapterMapper chapterMapper;
	@Autowired
	VideoService videoService;
	@Autowired
	TeacherService teacherService;
	@Autowired
	SubExperimentService subExperimentService;
	@Autowired
	SubExperimentMapper subExperimentMapper;
	@Autowired
	SubProjectService subProjectService;
	@Autowired
	VideoMapper videoMapper;
	@Autowired
	SubExperimentReportSubmitService subExperimentReportSubmitService;

	@Test
	void interfaceTest() {
		System.out.println(subExperimentReportSubmitService.getReportSummaryByRole(1));
	}


	@Test
	void dockerTest() throws JSchException, SftpException {
		dockerService.createNewDocker(2);
		ShellUtils shellUtils = new ShellUtils();

		shellUtils.sshRemoteCallLogin();
		shellUtils.execCommand("ls");
		shellUtils.closeSession();

	}

}

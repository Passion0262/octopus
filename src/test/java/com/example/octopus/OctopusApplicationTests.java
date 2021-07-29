package com.example.octopus;

import com.example.octopus.dao.ClassMapper;
import com.example.octopus.dao.DockerMapper;
import com.example.octopus.dao.TeacherMapper;
import com.example.octopus.dao.UserMapper;
import com.example.octopus.dao.experiment.ChapterMapper;
import com.example.octopus.dao.experiment.ModuleMapper;
import com.example.octopus.dao.experiment.SubExperimentMapper;
import com.example.octopus.dao.experiment.VideoMapper;
import com.example.octopus.entity.user.Docker;
import com.example.octopus.service.*;
import com.example.octopus.utils.ShellUtils;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
//import org.graalvm.compiler.lir.LIRInstruction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

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
	@Autowired
	DockerMapper dockerMapper;

	private ShellUtils shellUtils = new ShellUtils();

	@Test
	void interfaceTest() {
		Docker d = new Docker();
		d.setId(2);
		d.setVersion("v1");
		d.generate(shellUtils.getAddress());
		List<Docker> ds= new ArrayList<>();
		ds.add(d);
//		dockerMapper.createDocker(d);
		shellUtils.resetPod(1, 32004, "v1");
		shellUtils.resetPod(d.getId(), d.getPort(), d.getVersion());
		shellUtils.upgradePods(ds, "v1");
	}

}

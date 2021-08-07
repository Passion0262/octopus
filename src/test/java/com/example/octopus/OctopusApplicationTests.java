package com.example.octopus;

import com.example.octopus.dao.ClassMapper;
import com.example.octopus.dao.DockerMapper;
import com.example.octopus.dao.TeacherMapper;
import com.example.octopus.dao.UserMapper;
import com.example.octopus.dao.experiment.*;
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
import java.util.Arrays;
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
	SubExperimentReportSubmitMapper reportSubmitMapper;
	@Autowired
	DockerMapper dockerMapper;
	@Autowired
	ExperimentMapper experimentMapper;

	private ShellUtils shellUtils = new ShellUtils();

	@Autowired
	CourseStaticService staticService;

	@Autowired
	SubExperimentReportSubmitService reportSubmitService;

	@Test
	void interfaceTest() {
		System.out.println(reportSubmitService.listReportAnalysisByRoleAndSubExpId(1, 1));
		System.out.println(reportSubmitService.listReportSummaryByRole(1));
		System.out.println(reportSubmitService.listClassReportSummaryByRoleAndSubExpId(1, 1));
		System.out.println(reportSubmitService.getReportAnalysisByRoleAndSubExpIdAndClassId(1, 1,2));
		System.out.println(reportSubmitService.listClassReportByRoleAndSubExpIdAndClassId(1, 1, 2));
		System.out.println(reportSubmitService.getNextReportByTeaIdAndSubExpIdAndClassId(1, 1,1,2));
	}

}

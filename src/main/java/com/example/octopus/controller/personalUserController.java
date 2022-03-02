package com.example.octopus.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.octopus.entity.dataset.Dataset;
import com.example.octopus.entity.experiment.*;
import com.example.octopus.entity.personal.PersonalAdmin;
import com.example.octopus.entity.personal.PersonalUser;
import com.example.octopus.entity.personal.Plan;
import com.example.octopus.entity.project.Project;
import com.example.octopus.entity.project.ProjectModule;
import com.example.octopus.entity.project.SubProject;
import com.example.octopus.entity.user.Course;
import com.example.octopus.entity.user.Student;
import com.example.octopus.entity.user.Teacher;
import com.example.octopus.entity.vo.CourseTimeVO;
import com.example.octopus.entity.vo.experiment.ExperimentTimeHistoryVO;
import com.example.octopus.entity.vo.experiment.ExperimentTimeVO;
import com.example.octopus.entity.vo.video.VideoTimeHistoryVO;
import com.example.octopus.service.*;
import com.example.octopus.service.personal.*;
import com.example.octopus.utils.CookieTokenUtils;
import com.example.octopus.utils.PropertiesUtil;
import com.example.octopus.utils.TokenCheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 个人用户前台controller
 * @author: Hao
 * @date: 2022/1/11 20:18
 */
@Controller
public class personalUserController {
	private Logger logger = LoggerFactory.getLogger(adminController.class);

	private PropertiesUtil propertiesUtil = new PropertiesUtil();
	private String WEB_BASE_PATH = propertiesUtil.getFileSavePath();
	private String WEB_HOST = "/static/";


	@Autowired
	SysUserRoleService sysUserRoleService;

	@Autowired
	PlanService planService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	PersonalUserService personalUserService;

	@Autowired
	PersonalPlanService personalPlanService;


	@Autowired
	UserService userService;
	@Autowired
	CourseService courseService;
	@Autowired
	StudentCourseService studentCourseService;
	@Autowired
	ExperimentService experimentService;
	@Autowired
	SubExperimentService subExperimentService;
	@Autowired
	ProjectService projectService;
	@Autowired
	DatasetService datasetService;
	@Autowired
	ModuleService moduleService;
	@Autowired
	ChapterService chapterService;
	@Autowired
	TeacherService teacherService;
	@Autowired
	VideoService videoService;
	@Autowired
	ProjectModuleService projectModuleService;
	@Autowired
	SubProjectService subProjectService;
	@Autowired
	DockerService dockerService;
	@Autowired
	SubExperimentProgressService subExperimentProgressService;

	@Autowired
	VideoProgressService videoProgressService;
	@Autowired
	SubExperimentReportSaveService subExperimentReportSaveService;
	@Autowired
	SubExperimentReportSubmitService subExperimentReportSubmitService;

	@Autowired
	VideoQuestionService videoQuestionService;

	@Autowired
	PersonalAdminService personalAdminService;


	private final static String COOKIE_NAME = "cookiePersonalUser";

	private CookieTokenUtils cookieThings = new CookieTokenUtils();


	private boolean cookieCheck(Model model, HttpServletRequest request) {
		// 检查cookie合法性
		TokenCheckUtils tokenCheck = cookieThings.validateToken(request, COOKIE_NAME);
		if (tokenCheck.isSuccess()) {
			long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
			int role_id = sysUserRoleService.getRoleIdByUserId(user);
			model.addAttribute("username", tokenCheck.getUserName());  //TODO： 研究一下这块 要在页面右上角显示出用户电话
			if (role_id == 4) {
				model.addAttribute("role", "personal_user");
				return true;
			} else
				return false;
		} else {
			logger.info(tokenCheck.getErrorType() + "  需要重新登录!");
			return false;
		}
	}



	public static String getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String result = format.format(today);
		return result;
	}

	@RequestMapping("/per_index")
	public String per_index(Model model, HttpServletRequest request) {
		String perNumber = SecurityContextHolder.getContext().getAuthentication().getName();
		Long stuName = Long.parseLong(perNumber);
		if (!perNumber.equals(cookieThings.getCookieUserNum(request, COOKIE_NAME))) return "redirect:/";

		if (!cookieCheck(model, request)) return "redirect:/login";


//		int chosenCourse = courseService.countCourseChosen(stuName);
//		model.addAttribute("chosenCourse", chosenCourse);
//
//		int noFinished = chosenCourse - courseService.listCompletedCourses(stuName).size();
//		model.addAttribute("noFinished", noFinished);
//
//		Timestamp lastlogintime = userService.getStudentByStuNumber(stuName).getLastLoginTime();
//		model.addAttribute("lastlogintime", lastlogintime);


//		List<CourseTimeVO> videopro = videoProgressService.countStudyTimeByStuNumberGroupByCourseId(stuName);
//
//		List<ExperimentTimeVO> experpro = experimentService.countExperimentTime(stuName);
//
//
//		JSONArray videopros = new JSONArray();
//		JSONArray experpros = new JSONArray();
//		List<String> videoCoursenames = new ArrayList<>();
//		List<String> experCoursenames = new ArrayList<>();
//
//		logger.info("videopro:" + videopro);
//
//		for (int i = 0; i < videopro.size(); i++) {
//			JSONObject ob1 = new JSONObject();
//			JSONObject ob2 = new JSONObject();
////            String s1 = courseService.getCourseById(videopro.get(i).getCourseId()).getCourseName();
//			String s1 = courseService.getCourseById(videopro.get(i).getTeaCourseId()).getCourseName();
//			String s2 = courseService.getCourseById(experpro.get(i).getTeaCourseId()).getCourseName();
//			ob1.put("value", videopro.get(i).getTime());
//			ob1.put("name", s1);
//			videoCoursenames.add(s1);
//			ob2.put("value", experpro.get(i).getTime());
//			ob2.put("name", s2);
//			experCoursenames.add(s2);
//			videopros.add(ob1);
//			experpros.add(ob2);
//		}
//
//		model.addAttribute("videoCoursenames", videoCoursenames);
//		model.addAttribute("videopros", videopros);
//		model.addAttribute("experCoursenames", experCoursenames);
//		model.addAttribute("experpros", experpros);
//
//
//		List<ExperimentTimeHistoryVO> alastExper = experimentService.getHistory7DaysExperimentTime(stuName);
//
//		List<VideoTimeHistoryVO> alastVideo = videoProgressService.getHistory7DaysStudyTime(stuName);
//
//		List<String> pastDaysList = new ArrayList<>();
//		for (int i = 0; i < 7; i++) {
//			pastDaysList.add(getPastDate(i));
//		}
//
//		List<Integer> lastExper = new ArrayList<>();
//		List<Integer> lastvideo = new ArrayList<>();
//
//		int a = alastVideo.size() - 1, b = alastExper.size() - 1;
//		for (int i = 0; i < 7; i++) {
//			if (a >= 0) {
//				if (alastVideo.get(a).getDate().toString().equals(pastDaysList.get(i))) {
//					lastvideo.add(alastVideo.get(a).getTime());
//					a = a - 1;
//				} else {
//					lastvideo.add(0);
//				}
//			} else {
//				lastvideo.add(0);
//			}
//			if (b >= 0) {
//				if (alastExper.get(b).getDate().toString().equals(pastDaysList.get(i))) {
//					lastExper.add(alastExper.get(b).getTime());
//					b--;
//				} else {
//					lastExper.add(0);
//				}
//			} else {
//				lastExper.add(0);
//			}
//		}
//
//		List<Integer> lastExper0 = new ArrayList<>();
//		List<Integer> lastvideo0 = new ArrayList<>();
//		List<String> pastDaysList0 = new ArrayList<>();
//		for (int i = 0; i < 7; i++) {
//			lastExper0.add(lastExper.get(6 - i));
//			lastvideo0.add(lastvideo.get(6 - i));
//			pastDaysList0.add(pastDaysList.get(6 - i));
//		}
//
//		model.addAttribute("lastExper", lastExper0);
//		model.addAttribute("lastvideo", lastvideo0);
//		model.addAttribute("pastDaysList", pastDaysList0);


//        int experpros = subExperimentProgressService.countValidStudyTime(stuName);
//        model.addAttribute("experpros",experpros);
		return "per_index";
	}

	@RequestMapping("/per_userinfo")
	public String per_userinfo(Model model, HttpSession session, HttpServletRequest request) {
		if (!cookieCheck(model, request)) return "redirect:/login";

		String perName = cookieThings.getCookieUserName(request, COOKIE_NAME);
		String perNum = cookieThings.getCookieUserNum(request, COOKIE_NAME);

		PersonalUser per = personalUserService.getPersonalUser(Long.parseLong(perNum));
//		Student stu = userService.getStudentByStuNumber(Long.parseLong(perNum));
		model.addAttribute("per", per);

		return "per_userinfo";
	}




	@RequestMapping("/per_applycourse")
	public String per_applycourse(Model model, HttpServletRequest request) {
		if (!cookieCheck(model, request)) return "redirect:/login";

		List<Plan> allplans = planService.listAllPlan();
		model.addAttribute("allplans", allplans);
		logger.info("allplans"+allplans);

		return "per_applycourse";
	}


	@RequestMapping("/per_apply_detail/{id}")
	public String apply_detail(@PathVariable(value = "id") String id, Model model, HttpServletRequest request) {
		if (!cookieCheck(model, request)) return "redirect:/login";

		logger.info("plan_id:" + id);

  		Plan plan = planService.listAllPlan().get(0); //TODO: 根据id获取

//		Course course = courseService.getCourseById(Long.parseLong(id));
		model.addAttribute("plan", plan);


		String stuNum = cookieThings.getCookieUserNum(request, COOKIE_NAME);
//		boolean isapplied = studentCourseService.isChosen(Long.parseLong(stuNum), Long.parseLong(id));
		boolean isapplied = false; //TODO: 判断是否买过

		logger.info("isapplied:" + isapplied);

		model.addAttribute("isapplied", isapplied);

		return "per_apply_detail";
	}

	@RequestMapping("/per_apply_detail/per_sure_apply")
	public String sure_apply(String id, Model model, HttpServletRequest request) {
		if (!cookieCheck(model, request)) return "redirect:/login";

		String stuNum = cookieThings.getCookieUserNum(request, COOKIE_NAME);
		logger.info("确认课程_id:" + id);
		logger.info("确认学生_id:" + stuNum);
//		boolean issuccess = studentCourseService.insertStudentCourse(Long.parseLong(stuNum), Long.parseLong(id));
//
//		boolean isapplied = studentCourseService.isChosen(Long.parseLong(stuNum), Long.parseLong(id));
		boolean issuccess = true;

		boolean isapplied = true;
		model.addAttribute("isapplied", true);

		return "redirect:/per_apply_detail/" + id;
	}


	@RequestMapping("/per_mycourse")
	public String per_mycourse(Model model, HttpServletRequest request) {
		if (!cookieCheck(model, request)) return "redirect:/login";

		Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));

		List<Course> mycourses = courseService.listCoursesByStuNumber(2);  //TODO: 换成perid 选的课
		logger.info("mycourses:" + mycourses);
		model.addAttribute("mycourses", mycourses);

		//这里要计算完成了多少个视频 算百分比

		List<Integer> pros = new ArrayList<>();
		for (int i = 0; i < mycourses.size(); i++) {
//            logger.info("mycourse: " + mycourses.get(i).getCourseStaticId());
			double pro = videoProgressService.getCourseProgress(mycourses.get(i).getId(), stuNum);
			pros.add((int) (pro * 100));
		}
		logger.info("pros:" + pros);
		model.addAttribute("pros", pros);


		return "per_mycourse";
	}


	@RequestMapping("/per_course_video/{id}/{videoid}")
	public String per_course_video(@PathVariable(value = "id") String id, @PathVariable(value = "videoid") String videoid, Model model, HttpServletRequest request) {
		if (!cookieCheck(model, request)) return "redirect:/login";


		model.addAttribute("id", id);

		Long courseid_id = Long.parseLong(id);
		Long currvideo_id = Long.parseLong(videoid);

		Course course = courseService.getCourseById(courseid_id);
		courseid_id = course.getCourseStaticId();
		model.addAttribute("course", course);

		List<Chapter> chapters = chapterService.listChaptersByCourseId(courseid_id);
		if (chapters.size() == 0) {
			return "redirect:/per_mycourse";
		}

		model.addAttribute("chapters", chapters);
		model.addAttribute("chaptersnum", chapters.size());

		List<List<Video>> videos = new ArrayList<>();
		List<List<Long>> tosubexperiments = new ArrayList<>();
		List<List<VideoProgress>> videopros = new ArrayList<>();
		Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));

		for (int i = 0; i < chapters.size(); i++) {
			List<Video> video = videoService.listVideosByChapterId(chapters.get(i).getChapterId());
			videos.add(video);

			List<VideoProgress> videopro = new ArrayList<>();
			List<Long> tosubs = new ArrayList<>();

			for (int j = 0; j < video.size(); j++) {
				Long tosub = subExperimentService.getSubExperimentIdByVideoId(video.get(j).getId());
				VideoProgress videop = videoProgressService.getByVideoIdAndStuNumber(video.get(j).getId(), stuNum);
				videopro.add(videop);
				tosubs.add(tosub);
			}
			tosubexperiments.add(tosubs);
			videopros.add(videopro);
		}

		model.addAttribute("videos", videos);
		model.addAttribute("videopros", videopros);

		if (currvideo_id == 0 || currvideo_id == null) {
			model.addAttribute("startvideo", videos.get(0).get(0));
			model.addAttribute("startvideopro", videopros.get(0).get(0));
		} else {
			Video startvideo = videoService.getById(currvideo_id);
			VideoProgress startvideopro = videoProgressService.getByVideoIdAndStuNumber(currvideo_id, stuNum);
			model.addAttribute("startvideo", startvideo);
			model.addAttribute("startvideopro", startvideopro);
		}

		model.addAttribute("tosubexperiments", tosubexperiments);


		Experiment experiment = experimentService.getExperimentByCourseId(courseid_id);
		model.addAttribute("experiment", experiment);

		return "per_course_video";
	}

	@RequestMapping("/per_update_videotime")
	public String per_update_videotime(Model model, HttpServletRequest request) throws ParseException {


		logger.info("视频学习记录新增");

		Long id = Long.parseLong(request.getParameter("courseid"));
		String starttime = (request.getParameter("starttime"));
		String endtime = (request.getParameter("endtime"));
		Integer currenttime = Integer.parseInt((request.getParameter("currenttime")));
		Integer jindu = Integer.parseInt((request.getParameter("jindu")));
		Integer learntime = Integer.parseInt((request.getParameter("learntime")));
		Long videoid = Long.parseLong(request.getParameter("videoid"));
		Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));

//        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date startdate = simpleDateFormat.parse(starttime);
		Date startdate = new Date(starttime);
		Date enddate = new Date(endtime);
		Timestamp startst = new Timestamp(startdate.getTime());
		Timestamp endst = new Timestamp(enddate.getTime());

		VideoProgress videoProgress = new VideoProgress();
		videoProgress.setTeaCourseId(id);
		videoProgress.setVideoId(videoid);
		videoProgress.setStuNumber(stuNum);
		videoProgress.setStartTime(startst);
		videoProgress.setEndTime(endst);
		videoProgress.setProgress(jindu);
		videoProgress.setLastVideoProgress(currenttime);
		videoProgress.setStudyTime(learntime);
		boolean issuccess = videoProgressService.insertVideoProgress(videoProgress);

		return "redirect:/per_course_video/" + id + "/" + videoid;
	}


	@RequestMapping("/per_course_video_quiz/{id}")
	public String per_course_video_quiz(@PathVariable(value = "id") String id, Model model, HttpServletRequest request) {
		if (!cookieCheck(model, request)) return "redirect:/login";
		Long video_id = Long.parseLong(id);
		Video video = videoService.getById(video_id);
		model.addAttribute("video", video);
		logger.info("video:" + video);

		List<VideoQuestion> vqs = videoQuestionService.listQuestionsByVideoId(video_id);
		logger.info("vqs:" + vqs);
		model.addAttribute("vqs", vqs);

		if (vqs.size() == 0) {
			model.addAttribute("isq", 0);
		} else {
			model.addAttribute("isq", 1);
		}
		logger.info("isq:" + vqs.size());

		return "per_course_video_quiz";
	}

//
//	@RequestMapping("/experiment_task")
//	public String experiment_task(Model model, HttpServletRequest request) {
//		if (!cookieCheck(model, request)) return "redirect:/login";
//
//		Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
//		List<Experiment> allexperiments = experimentService.listExperimentsByStuNumber(stuNum);
//		model.addAttribute("allexperiments", allexperiments);
//
//		return "experiment_task";
//	}
//
//
//	@RequestMapping("/experiment_task_detail/{id}")
//	public String experiment_task_detail(@PathVariable(value = "id") String id, Model model, HttpServletRequest request) {
//		if (!cookieCheck(model, request)) return "redirect:/login";
//
//		Long experiment_id = Long.parseLong(id);
//
//		Experiment experiment = experimentService.getExperimentById(experiment_id);
//
//		model.addAttribute("experiment", experiment);
//
//		List<Module> modules = moduleService.listModulesByExperimentId(experiment_id);
//
//		model.addAttribute("modules", modules);
//		model.addAttribute("modulesnum", modules.size());
//
//		List<List<SubExperiment>> subExperiments = new ArrayList<>();
//		for (int i = 0; i < modules.size(); i++) {
//			List<SubExperiment> subExperiment = subExperimentService.listSubExperimentsByModuleId(modules.get(i).getModuleId());
//			subExperiments.add(subExperiment);
//		}
//		model.addAttribute("subExperiments", subExperiments);
//
//		return "experiment_task_detail";
//	}
//
//
//	@RequestMapping("/experiment_machine/{id}")
//	public String experiment_machine(@PathVariable("id") String id, Model model, HttpServletRequest request) {
//		if (!cookieCheck(model, request)) return "redirect:/login";
//		Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
//
//		logger.info("实验id:" + id);
//		Long sub_id = Long.parseLong(id);
//
//		SubExperiment subExperiment = subExperimentService.getById(sub_id);
//		logger.info("subExperiment:" + subExperiment);
//		model.addAttribute("subExperiment", subExperiment);
//
//		Experiment experiment = experimentService.getExperimentById(subExperiment.getExperimentId());
//		model.addAttribute("experiment", experiment);
//
//		SubExperimentReportSave subsave = subExperimentReportSaveService.getLatest(sub_id, stuNum);
//		String subcontent = "";
//		if (subsave != null) {
//			subcontent = subsave.getContent();
//		}
//		logger.info("subcontent:" + subcontent);
//		model.addAttribute("subcontent", subcontent);
//
//
//		Video video = videoService.getVideoBySubExperimentId(sub_id);
//		if (video == null) {
//			model.addAttribute("isvideo", 0);
//			model.addAttribute("videocourse", 0);
//		} else {
//			model.addAttribute("isvideo", 1);
////            logger.info("videocourse:"+video.getCourseId());
//			model.addAttribute("videocourse", video.getCourseId());
//			model.addAttribute("subvideoid", video.getId());
//		}
//
//		SubExperimentReportSubmit issub = subExperimentReportSubmitService.getByStuNumberAndSubExperimentId(sub_id, stuNum);
//		if (issub == null) {
//			model.addAttribute("isexpsubmit", 0);
//			logger.info("isexpsubmit:0");
//		} else {
//			model.addAttribute("isexpsubmit", 1);
//			logger.info("isexpsubmit:1");
//		}
//
//
//		// 这里换成查看是否学生有虚拟机资源
//		dockerService.checkTimeReset();
//		String docker_url = dockerService.getPodForStu(stuNum, 2, sub_id);
////        boolean a =dockerService.updateDockerStatusByStuNum(stuNum, 2, sub_id);
//		model.addAttribute("docker_url", docker_url);
//
//		model.addAttribute("expstarttime", new Date().getTime());
////        boolean b = subExperimentProgressService.insert(stuNum,sub_id);
//
//		return "experiment_machine";
//
//	}
//
//	@RequestMapping("/imageupload")
//	@ResponseBody
//	public JSONObject imageUpload(@RequestParam("editormd-image-file") MultipartFile file) {
//		JSONObject jsonObject = new JSONObject();
//		if (file != null) {
//			String path = propertiesUtil.getExpImageSavePath();
//			String fileName = "exp" + UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//			logger.info("fileName:" + fileName);
//			try {
//				File destFile = new File(path + "/" + fileName);
//				file.transferTo(destFile);
//				String exppicurl = propertiesUtil.getExpPicPath() + fileName;
//				jsonObject.put("url", exppicurl);
//				jsonObject.put("success", 1);
//				jsonObject.put("message", "upload success!");
//				return jsonObject;
//			} catch (Exception e) {
//				return null;
//			}
//		}
//		jsonObject.put("success", 0);
//		jsonObject.put("message", "upload error!");
//		return jsonObject;
//	}
//
//
////    @RequestMapping(value = "sendExperImage", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
////    @ResponseBody
////    public String sendExperImage(@RequestParam("file") MultipartFile file, Model model,HttpServletRequest request) throws Exception {
//////        logger.info("图片上传");
////        try {
////            String path =propertiesUtil.getExpImageSavePath();
////            String fileName ="exp" +  UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//////            logger.info("fileName:"+fileName);
////            File destFile = new File(path + "/" + fileName);
////            file.transferTo(destFile);
//////            FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
////            //上面代码是拷贝到本地的，但是因为感觉并不人性化，所以采取了上传到图片服务器的思路
//////            InputStream inputStream=file.getInputStream();
////
//////            String url = null;
//////            Boolean flag= FtpFileUtil.uploadFile(fileName,inputStream);
//////            if(flag){
//////                url = fileName;
//////            }
////
//////             String url = destFile.getAbsolutePath();
//////            logger.info("url:"+url);
////            for(int i = 0;i<50;i++){
////                if(destFile.exists()){
////                    Map<String,Object> params = new HashMap<>();
////                    params.put("state", "success");
////                    String exppic = propertiesUtil.getExpPicPath();
////                    params.put("picurl", exppic+fileName);
////                    return JSONArray.toJSON(params).toString();
////                }
////            }
////            Map<String,Object> params = new HashMap<>();
////            params.put("state", "fail");
////            params.put("picurl", "");
////            return JSONArray.toJSON(params).toString();
////        }catch (Exception e){
////            return  null;
////        }
////    }
////
////
////    @PostMapping(value = "/deleteExpImage")
////    @ResponseBody
////    public String deleteExpImage(HttpServletRequest request) {
////
////        String picUrl = request.getParameter("picUrl");
////        logger.info("deletepicUrl:" + picUrl);
////        String filename = picUrl.replace("http://localhost:8080/static/","");
////        String filepath = propertiesUtil.getExpImageSavePath();
////
////        File file = new File(filepath+filename);
////        Map<String,Object> params = new HashMap<>();
////        if (file.exists()) {
////            file.delete();
////            params.put("state", "success");
//////            System.out.println("===========删除成功=================");
////        } else {
////            params.put("state", "fail");
//////            System.out.println("===============删除失败==============");
////        }
////        return JSONArray.toJSON(params).toString();
////    }
//
//	@PostMapping(value = "/saveExperText")
//	@ResponseBody
//	public void saveExperText(Model model, HttpServletRequest request) {
//		Long experid = Long.parseLong(request.getParameter("experid"));
//		Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
//		String text = request.getParameter("text");
//
//		SubExperimentReportSave sub = new SubExperimentReportSave();
//		sub.setSubExperimentId(experid);
//		sub.setStuNumber(stuNum);
//		sub.setContent(text);
////        subExperimentReportSaveService.update(sub);
//		subExperimentReportSaveService.insert(sub);
//	}
//
//	@PostMapping(value = "/submitExperText")
//	@ResponseBody
//	public void submitExperText(HttpServletRequest request) {
//		Long experid = Long.parseLong(request.getParameter("experid"));
//		Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
//		String text = request.getParameter("text");
//		logger.info("experid:" + experid);
//		logger.info("text:" + text);
//		SubExperimentReportSubmit subexpsub = new SubExperimentReportSubmit();
//		subexpsub.setSubExperimentId(experid);
//		subexpsub.setContent(text);
//		subexpsub.setStuNumber(stuNum);
//		subExperimentReportSubmitService.insert(subexpsub);
//
//
////        String alltext = "<html><head></head><body>"+text+"</body></html>";
//
////        String savepath = propertiesUtil.getPdfSubmitPath();
////        String savepdfname = "exp"+UUID.randomUUID().toString().replace("-", "")+".pdf";
////        Document document = new Document(PageSize.A4);
////        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(savepath + savepdfname));
////        document.open();
//		//        document.close();
////        pdfWriter.close();
//		//
////        HTMLWorker htmlWorker = new HTMLWorker(document);
////        htmlWorker.parse(new StringReader(text));
////        ByteArrayInputStream bin = new ByteArrayInputStream(alltext.getBytes());
////        XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, bin, Charset.forName("UTF-8"));
//
//
////        InputStream stream = new ByteArrayInputStream(text.toString().getBytes("UTF-8"));
////        XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, stream);
////        XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
////        worker.parseXHtml(pdfWriter, document, new StringReader(text));
//
//	}
//
//
//	@RequestMapping("/projects")
//	public String projects(Model model, HttpServletRequest request) {
//		if (!cookieCheck(model, request)) return "redirect:/login";
//
//		List<Project> allprojects = projectService.listProjects();
//		model.addAttribute("allprojects", allprojects);
//
//		return "projects";
//	}
//
//	@RequestMapping("/project_detail/{id}")
//	public String project_detail(@PathVariable(value = "id") String id, Model model, HttpServletRequest request) {
//		if (!cookieCheck(model, request)) return "redirect:/login";
//
//		long project_id = Long.parseLong(id);
//
//		logger.info("project_id:" + project_id);
//
//		Project project = projectService.getProjectById(project_id);
//		model.addAttribute("project", project);
//
//		List<ProjectModule> modules = projectModuleService.listByProjectId(project_id);
//		model.addAttribute("modules", modules);
//		model.addAttribute("modulesnum", modules.size());
//
//		List<List<SubProject>> subprojects = new ArrayList<>();
//		for (int i = 0; i < modules.size(); i++) {
//			List<SubProject> subproject = subProjectService.listSubProjectsByModuleId(modules.get(i).getModuleId());
//			subprojects.add(subproject);
//		}
//		model.addAttribute("subprojects", subprojects);
//
//		return "project_detail";
//	}
//
//	@RequestMapping("/project_machine/{id}")
//	public String project_machine(@PathVariable("id") String id, Model model, HttpServletRequest request) {
//		if (!cookieCheck(model, request)) return "redirect:/login";
//		Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
//
//		logger.info("project实验机id:" + id);
//		Long sub_id = Long.parseLong(id);
//
//		SubProject subProject = subProjectService.getById(sub_id);
//		logger.info("subProject:" + subProject);
//		model.addAttribute("subProject", subProject);
//
//		Project project = projectService.getProjectById(subProject.getProjectId());
//		model.addAttribute("project", project);
//
////申请虚拟机资源
//
//		String docker_url = dockerService.getPodForStu(stuNum, 1, sub_id);
//		model.addAttribute("docker_url", docker_url);
//		//实验学习记录
////        SubExperimentProgress subexppro = new SubExperimentProgress();
//////        subexppro.setStuNumber(stuNum);
//////        subexppro.setSubExperimentId(sub_id);
//
//		return "project_machine";
//
//	}
//
//
	@RequestMapping("/per_studylog")
	public String studylog(Model model, HttpServletRequest request) {
		if (!cookieCheck(model, request)) return "redirect:/login";
		Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		List<VideoProgress> videopros = videoProgressService.listByStuNumber(stuNum);
		model.addAttribute("videopros", videopros);
		logger.info("videopros :" + videopros);

		List<Video> videos = new ArrayList<>();
		for (int i = 0; i < videopros.size(); i++) {
			Video v = videoService.getById(videopros.get(i).getVideoId());
			videos.add(v);
		}
		model.addAttribute("videos", videos);

		List<SubExperimentProgress> experpros = subExperimentProgressService.listByStuNumber(stuNum);
		model.addAttribute("experpros", experpros);

		List<SubExperiment> subexpers = new ArrayList<>();
		List<Long> courseids = new ArrayList<>();
		for (int i = 0; i < experpros.size(); i++) {
			SubExperiment w = subExperimentService.getById(experpros.get(i).getSubExperimentId());
			subexpers.add(w);
			Long c = courseService.getCourseIdByExperimentId(w.getExperimentId());
			courseids.add(c);
		}
		model.addAttribute("subexpers", subexpers);
		model.addAttribute("courseids", courseids);

		return "per_studylog";
	}


	@RequestMapping("/per_studylog_detail/{id}")
	public String studylog_detail(@PathVariable(value = "id") String id, Model model, HttpServletRequest request) {
		if (!cookieCheck(model, request)) return "redirect:/login";
		long cour_id = Long.parseLong(id);
		Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		Course course = courseService.getCourseById(cour_id);
		model.addAttribute("course", course);
		logger.info("course:" + course);

		List<VideoProgress> videopros = videoProgressService.listByCourseIdAndStuNumber(cour_id, stuNum);
		Integer videoprosnum = videopros.size();
		int videoprostudynum = videoProgressService.getStudyTimeByCourseIdAndStuNumber(cour_id, stuNum);
		model.addAttribute("videopros", videopros);
		model.addAttribute("videoprosnum", videoprosnum);
		model.addAttribute("videoprostudynum", videoprostudynum);

		List<Video> videos = new ArrayList<>();
		for (int i = 0; i < videopros.size(); i++) {
			Video v = videoService.getById(videopros.get(i).getVideoId());
			videos.add(v);
		}
		model.addAttribute("videos", videos);

		Long experid = experimentService.getExperimentByCourseId(cour_id).getId();
		List<SubExperimentProgress> experpros = subExperimentProgressService.listByStuNumberAndExperimentId(stuNum, experid);
		model.addAttribute("experpros", experpros);

		List<SubExperiment> subexpers = new ArrayList<>();
		List<Long> courseids = new ArrayList<>();
		for (int i = 0; i < experpros.size(); i++) {
			SubExperiment w = subExperimentService.getById(experpros.get(i).getSubExperimentId());
			subexpers.add(w);
			Long c = courseService.getCourseIdByExperimentId(w.getExperimentId());
			courseids.add(c);
		}
		int experprostudynum = subExperimentProgressService.countValidStudyTimeOnExperiment(stuNum, experid);
		model.addAttribute("subexpers", subexpers);
		model.addAttribute("courseids", courseids);
		model.addAttribute("experprostudynum", experprostudynum);

		return "per_studylog_detail";
	}
//
//
//	@RequestMapping("/datasets")
//	public String datasets(Model model, HttpServletRequest request) {
//		if (!cookieCheck(model, request)) return "redirect:/login";
//
//		List<Dataset> alldatasets = datasetService.listDatasets();
//		logger.info("alldatasets" + alldatasets);
//		model.addAttribute("alldatasets", alldatasets);
//
//		return "datasets";
//	}
//
//	@RequestMapping("/dataset_detail/{id}")
//	public String dataset_detail(@PathVariable(value = "id") String id, Model model, HttpServletRequest request) {
//		if (!cookieCheck(model, request)) return "redirect:/login";
//
//		long dataset_id = Long.parseLong(id);
//		logger.info("dataset_id:" + dataset_id);
//
//		//        这里需要一个根据数据集的id 返回dataset操作
////        Dataset dataset = datasetService.listDatasets().get(0);
//		Dataset dataset = datasetService.getDatasetById(dataset_id);
//		model.addAttribute("dataset", dataset);
//
//		return "dataset_detail";
//	}
//
//
//	@PostMapping(value = "/datasetdownload")
//	@ResponseBody
//	public String datasetdownload(HttpServletRequest request) {
//		Long datasetid = Long.parseLong(request.getParameter("datasetid"));
//		datasetService.increaseDownloadNum(datasetid);
//		Dataset d = datasetService.getDatasetById(datasetid);
//		Map<String, Object> params = new HashMap<>();
//		params.put("num", d.getDownloadNum());
//		return JSONArray.toJSON(params).toString();
//	}
//
//
////    @PostMapping(value = "/applymachine")
////    @ResponseBody
////    public String applymachine(HttpServletRequest request) {
////        logger.info("申请资源");
////        Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
////        String type = request.getParameter("type");
////        String sid = request.getParameter("id");
////        Long id = Long.parseLong(sid);
////        String docker_url = dockerService.getAddressByStuNumber(stuNum);
////        if(type=="experiment"){
////            boolean a =dockerService.updateDockerStatusByStuNum(stuNum, 2, id);
////        }else{
////            boolean b =dockerService.updateDockerStatusByStuNum(stuNum, 1, id);
////        }
////        Map<String,Object> params = new HashMap<>();
////
////        params.put("url", docker_url);
////
////        return JSONArray.toJSON(params).toString();
////    }`
//
//	@RequestMapping("/applymachine")
//	@ResponseBody
//	public JSONObject applymachine(HttpServletRequest request) {
//		logger.info("申请资源");
//		Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
//		String status = request.getParameter("status");
//		Long proceid = Long.parseLong(request.getParameter("proceid"));
//		int statusCode = 0;
//		if (status == "exper") {
//			statusCode = 2;
//		} else if (status == "project") {
//			statusCode = 1;
//		}
//
//		String docker_url = dockerService.getPodForStu(stuNum, statusCode, proceid);
//
//		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("docker_url", docker_url);
//		return jsonObject;
//	}
//
//	@RequestMapping("/update_vn_time")
//	@ResponseBody
//	public void update_vn_time(HttpServletRequest request) {
//		logger.info("更新虚拟机最近使用时间记录");
//		Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
//		dockerService.updateLastTimeByStuNum(stuNum);
//	}
//
//
//	@PostMapping(value = "/sleepmachine")
//	@ResponseBody
//	public void sleepmachine(HttpServletRequest request) {
//		logger.info("提交实验学习时间记录");
//		Long experid = Long.parseLong(request.getParameter("experid"));
//		Long stime = Long.parseLong(request.getParameter("start_time"));
//		logger.info("stime" + stime);
//		Timestamp start_time = new Timestamp(stime);
//		Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
//		Long dd = new Date().getTime();
//		Long interval = Math.round((double) (dd - stime) / 1000);
//		Timestamp end_time = new Timestamp(dd);
//		SubExperimentProgress sp = new SubExperimentProgress();
//		sp.setSubExperimentId(experid);
//		sp.setStartTime(start_time);
//		sp.setStuNumber(stuNum);
//		sp.setEndTime(end_time);
//		sp.setValidStudyTime(interval.intValue());
//		boolean b = subExperimentProgressService.insert(sp);
//
//	}


}

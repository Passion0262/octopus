package com.example.octopus.controller;

import com.example.octopus.entity.dataset.Dataset;
import com.example.octopus.entity.experiment.*;
import com.example.octopus.entity.experiment.Module;
import com.example.octopus.entity.project.Project;
import com.example.octopus.entity.user.Teacher;
import com.example.octopus.service.*;
import com.example.octopus.utils.CookieTokenUtils;
import com.example.octopus.utils.TokenCheckUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.octopus.entity.user.Student;
import com.example.octopus.entity.user.Course;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class studentController {

    private Logger logger = LoggerFactory.getLogger(studentController.class);

    @Autowired
    SysUserRoleService sysUserRoleService;

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
    VideoProgressService videoProgressService;


    private final static String cookieName = "cookie_";

    private CookieTokenUtils cookieThings = new CookieTokenUtils();


    private boolean cookieCheck(Model model, HttpServletRequest request) {
        // 检查cookie合法性
        TokenCheckUtils tokenCheck = cookieThings.validateToken(request, cookieName);
        if (tokenCheck.isSuccess()) {
            model.addAttribute("stuname", tokenCheck.getUserName());
            return true;
        } else {
            logger.info(tokenCheck.getErrorType() + "  需要重新登录!");
            return false;
        }
    }

    @RequestMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        cookieThings.deleteCookie(request, cookieName);
        return "auth-login";
//        return "login";
    }

    @RequestMapping("/")
    public String showHome(Model model, HttpServletRequest request, HttpServletResponse response) {

        String userNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        int role = sysUserRoleService.getRoleIdByUserId(Long.parseLong(userNumber));  //查表获取用户权限代码
        if (role == 2) {
            //身份为学生，进入前台系统
            Student stu = userService.getStudentByStuNumber(Long.parseLong(userNumber));
            logger.info("当前登陆身份为：学生        欢迎您，" + userNumber + ":" + stu.getName());
            cookieThings.setCookie(userNumber, stu.getName(), response, cookieName);
            return "redirect:/index";
        } else {
            //身份为教师或管理员，进入后台系统
            Teacher tea = teacherService.getTeacherByTeaNumber(Long.parseLong(userNumber));
            logger.info("当前登陆身份为：教师/管理员        欢迎您，" + userNumber + ":" + tea.getName());
            cookieThings.setCookie(userNumber, tea.getName(), response, cookieName);
            return "redirect:/admin_index";
        }
    }

    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request) {
        String stuNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!stuNumber.equals(cookieThings.getCookieUserNum(request, cookieName))) return "redirect:/";

        if (!cookieCheck(model, request)) return "redirect:/login";

        // String stuNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        // Student stu = userService.findStudentByStuNumber(Long.parseLong(tokenCheck.getUserNum()));
        // session.setAttribute("stuname",stu.getName());

        // String stuname = (String)session.getAttribute("stuname");
        // model.addAttribute("stuname", stuname);
        return "index";
    }

    @RequestMapping("/userinfo")
    public String userinfo(Model model, HttpSession session, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

        // todo 获取用户名及用户id的方法使用如下语句
        String stuName = cookieThings.getCookieUserName(request, cookieName);
        String stuNum = cookieThings.getCookieUserNum(request, cookieName);


//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);
//
//        String stuNumber = SecurityContextHolder.getContext().getAuthentication().getName();
//        Student stu = userService.findStudentByStuNumber(Long.parseLong(stuNumber));
        Student stu = userService.getStudentByStuNumber(Long.parseLong(stuNum));
        model.addAttribute("stu", stu);

        return "userinfo";
    }


    @RequestMapping("/update_phoneNumber")
    public String update_phoneNumber(@RequestParam("phoneNumber") String phoneNumber, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

        logger.info("phoneNumber:" + phoneNumber);

        Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        boolean status = userService.updatePhoneNumber(stuNum, phoneNumber);

        return "redirect:userinfo";
    }


    @RequestMapping("/applycourse")
    public String applycourse(Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);

        List<Course> allcourses = courseService.listCourses();
//        logger.info("allcourses:" + allcourses);
        model.addAttribute("allcourses", allcourses);


        return "applycourse";
    }


    @RequestMapping("/apply_detail/{id}")
    public String apply_detail(@PathVariable(value = "id") String id, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);

        logger.info("coursedetail_id:" + id);


        Course course = courseService.getCourseById(Long.parseLong(id));

        logger.info("course——detail:" + course);
        model.addAttribute("course", course);


        String stuNum = cookieThings.getCookieUserNum(request, cookieName);
        boolean isapplied = studentCourseService.isChosen(Long.parseLong(stuNum), Long.parseLong(id));

        logger.info("isapplied:" + isapplied);

        model.addAttribute("isapplied", isapplied);

        return "apply_detail";
    }

    @RequestMapping("/apply_detail/sure_apply")
    public String sure_apply(String id, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");


        String stuNum = cookieThings.getCookieUserNum(request, cookieName);
        logger.info("确认课程_id:" + id);
        logger.info("确认学生_id:" + stuNum);
        boolean issuccess = studentCourseService.insertStudentCourse( Long.parseLong(stuNum),Long.parseLong(id));

        boolean isapplied = studentCourseService.isChosen(Long.parseLong(stuNum), Long.parseLong(id));
        model.addAttribute("isapplied", isapplied);

        return "redirect:/apply_detail/" + id;
    }

    @RequestMapping("/apply_detail/cancel_apply")
    public String cancel_apply(String id, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");

        logger.info("取消申请_id:" + id);

        String stuNum = cookieThings.getCookieUserNum(request, cookieName);
        logger.info("确认课程_id:" + id);
        logger.info("确认学生_id:" + stuNum);
        boolean issuccess = studentCourseService.deleteStudentCourse( Long.parseLong(stuNum),Long.parseLong(id));
        logger.info("删除申请是否成功:" + issuccess);

        boolean isapplied = studentCourseService.isChosen(Long.parseLong(stuNum), Long.parseLong(id));
        model.addAttribute("isapplied", isapplied);

        return "redirect:/apply_detail/" + id;
    }


    @RequestMapping("/mycourse")
    public String mycourse(Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);

        Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));

        List<Course> mycourses = courseService.listCoursesByStuNumber(stuNum);
        logger.info("mycourses:" + mycourses);
        model.addAttribute("mycourses", mycourses);

        //这里要计算完成了多少个视频 算百分比

        return "mycourse";
    }


    @RequestMapping("/course_video/{id}")
    public String course_video(@PathVariable(value = "id") String id, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";


        model.addAttribute("id", id);

        logger.info("courseid_id:" + id);
        Long courseid_id = Long.parseLong(id);

        Course course = courseService.getCourseById(courseid_id);

        logger.info("course——detail:" + course);
        model.addAttribute("course", course);

        List<Chapter> chapters = chapterService.listChaptersByCourseId(courseid_id);
        logger.info("chapters:" + chapters);
        logger.info("chapters:" + chapters.size());
        model.addAttribute("chapters", chapters);
        model.addAttribute("chaptersnum", chapters.size());

        List<List<Video>> videos = new ArrayList<>();
        List<List<Long>> tosubexperiments = new ArrayList<>();

        for (int i=0;i<chapters.size();i++){
//            logger.info("chaptersid:" + chapters.get(i).getId());
            List<Video> video = videoService.listVideosByChapterId(chapters.get(i).getId());
            videos.add(video);

            List<Long> tosubs = new ArrayList<>();
            for (int j=0;j<video.size();j++){
                Long tosub = subExperimentService.getSubExperimentIdByVideoId(video.get(j).getId());
                tosubs.add(tosub);
            }
            tosubexperiments.add(tosubs);
        }

        logger.info("videos:" + videos);
        model.addAttribute("videos", videos);

        logger.info("startvideo:" + videos.get(0).get(0));
        model.addAttribute("startvideo", videos.get(0).get(0));


        logger.info("tosubexperiments:" + tosubexperiments);
        model.addAttribute("tosubexperiments", tosubexperiments);



        Experiment experiment = experimentService.getExperimentByCourseId(courseid_id);
        model.addAttribute("experiment", experiment);

        return "course_video";
    }

    @RequestMapping("/update_videotime")
    public String update_videotime( Model model, HttpServletRequest request) throws ParseException {
//        DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
//        try {
//        Date starttime = sdf.parse((request.getParameter("starttime")));
//        Date endtime = sdf.parse((request.getParameter("endtime")));
//        logger.info("starttime:" + starttime);
//        logger.info("endtime:" + endtime);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        Long id =Long.parseLong(request.getParameter("courseid"));
        String starttime =(request.getParameter("starttime"));
        String endtime = (request.getParameter("endtime"));
        Integer currenttime = Integer.parseInt((request.getParameter("currenttime")));
        Integer jindu = Integer.parseInt((request.getParameter("jindu")));
        Integer learntime = Integer.parseInt((request.getParameter("learntime")));
        Long videoid = Long.parseLong(request.getParameter("videoid"));
        Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));

//        String strDateFormat = "yyyy-MM-dd HH:mm:ss";
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Date startdate = simpleDateFormat.parse(starttime);
        Date startdate = new Date(starttime);
        Date enddate = new Date(endtime);
        Timestamp startst = new Timestamp(startdate.getTime());
        Timestamp endst = new Timestamp(enddate.getTime());
        logger.info("startdate:" + startdate);
        logger.info("enddate:" + startdate);

        VideoProgress videoProgress = new VideoProgress();
        videoProgress.setVideoId(videoid);
        videoProgress.setStuNumber(stuNum);
        videoProgress.setStartTime(startst);
        videoProgress.setEndTime(endst);
        videoProgress.setProgress(jindu);
        videoProgress.setLastVideoProgress(currenttime);
        videoProgress.setStudyTime(learntime);
        boolean issuccess = videoProgressService.insertVideoProgress(videoProgress);

        logger.info("videoid:" + videoid);
        logger.info("stuNum:" + stuNum);
        logger.info("starttime:" + starttime);
        logger.info("endtime:" + endtime);
        logger.info("currenttime:" + currenttime);
        logger.info("jindu:" + jindu);
        logger.info("learntime:" + learntime);

        return "redirect:/course_video/"+id;
    }




    @RequestMapping("/experiment_task")
    public String experiment_task(Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);

        Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        List<Experiment> allexperiments = experimentService.listExperimentsByStuNumber(stuNum);
        logger.info("allExperiment:" + allexperiments);
        model.addAttribute("allexperiments", allexperiments);

        return "experiment_task";
    }


    @RequestMapping("/experiment_task_detail/{id}")
    public String experiment_task_detail(@PathVariable(value = "id") String id, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);

        logger.info("experiment_id:" + id);
        Long experiment_id = Long.parseLong(id);

        Experiment experiment = experimentService.getExperimentById(experiment_id);

        logger.info("experiment——detail:" + experiment);
        model.addAttribute("experiment", experiment);

        List<Module> modules = moduleService.listModulesByExperimentId(experiment_id);
        logger.info("experiment——module:" + modules);
        logger.info("experiment——module-size:" + modules.size());
        model.addAttribute("modules", modules);
        model.addAttribute("modulesnum", modules.size());

        List<List<SubExperiment>> subExperiments = new ArrayList<>();
        for (int i = 0; i < modules.size(); i++) {
            logger.info("experiment——module-sub:" + modules.get(i).getId());
            List<SubExperiment> subExperiment = subExperimentService.listSubExperimentsByModuleId(modules.get(i).getId());
            subExperiments.add(subExperiment);
        }
        logger.info("subExperiments:" + subExperiments);
        model.addAttribute("subExperiments", subExperiments);


        return "experiment_task_detail";
    }



    @RequestMapping("/experiment_machine/{id}")
    public String experiment_machine(@PathVariable("id")String id,Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);

        logger.info("id:"+id);
        Long sub_id = Long.parseLong(id);
        logger.info("sub_id:"+sub_id);

        SubExperiment subExperiment = subExperimentService.getById(sub_id);
        logger.info("subExperiment:"+subExperiment);
        model.addAttribute("subExperiment", subExperiment );

        Experiment experiment = experimentService.getExperimentById(subExperiment.getExperimentId());
        logger.info("experiment:"+experiment);
        model.addAttribute("experiment", experiment );

        // 这里要调整
        Video video = videoService.getVideoBySubExperimentId(sub_id);
        logger.info("video:"+video);
        if(video==null){
            model.addAttribute("isvideo", 0);
            model.addAttribute("videocourse", 0);
        }else{
            model.addAttribute("isvideo", 1);
            logger.info("videocourse:"+video.getCourseId());
            model.addAttribute("videocourse", video.getCourseId());
        }


        return "experiment_machine";

    }

    @RequestMapping("/sendImage")
    public String sendImag(Model model, HttpServletRequest request) {

        logger.info("fileupload:" + request.getParameter("file"));
        logger.info("fileupload:" + request.getParameter("dir"));
        return "redirect:/experiment_machine";
    }

    @RequestMapping("/deleteImage")
    public String deleteImag(Model model, HttpServletRequest request) {

//        logger.info("fileupload:" + request.getParameter("file"));
//        logger.info("fileupload:" + request.getParameter("dir"));
        return "redirect:/experiment_machine";
    }


    @RequestMapping("/projects")
    public String projects(Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);

        List<Project> allprojects = projectService.listProjects();
//        logger.info("allProject:" + allprojects);
        model.addAttribute("allprojects", allprojects);

        return "projects";
    }

    @RequestMapping("/project_detail/{id}")
    public String project_detail(@PathVariable(value = "id") String id, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);
        long project_id = Long.parseLong(id);

        logger.info("project_id:" + project_id);

        //        这里需要一个根据实验的id 返回course操作
        Project project = projectService.getProjectById(project_id);
        logger.info("project——detail:" + project);
        model.addAttribute("project", project);

        return "project_detail";
    }


    @RequestMapping("/studylog")
    public String studylog(Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);
        return "studylog";
    }

    @RequestMapping("/studylog_detail")
    public String studylog_detail(Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);
        return "studylog_detail";
    }


    @RequestMapping("/datasets")
    public String datasets(Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);

        List<Dataset> alldatasets = datasetService.listDatasets();
        logger.info("alldatasets" + alldatasets);
        model.addAttribute("alldatasets", alldatasets);

        return "datasets";
    }

    @RequestMapping("/dataset_detail/{id}")
    public String dataset_detail(@PathVariable(value = "id") String id, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);

        long dataset_id = Long.parseLong(id);
        logger.info("dataset_id:" + dataset_id);

        //        这里需要一个根据数据集的id 返回dataset操作
//        Dataset dataset = datasetService.listDatasets().get(0);
        Dataset dataset = datasetService.getDatasetById(dataset_id);
        logger.info("dataset——detail:" + dataset);
        model.addAttribute("dataset", dataset);

        return "dataset_detail";
    }


//    @RequestMapping("/confirmlogin")
//    public String confirmlogin(@RequestParam("username") String username, @RequestParam("userpwd") String userpwd,Model model) {
////        System.out.println(username);
////        System.out.println(userpwd);
//        logger.info("username:" + username);
//        logger.info("password" + userpwd);
//        model.addAttribute("username", username);
//        return "index";
//    }
}
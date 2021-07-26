package com.example.octopus.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.octopus.entity.VOs.CourseTimeVO;
import com.example.octopus.entity.VOs.ExperimentTimeHistoryVO;
import com.example.octopus.entity.VOs.ExperimentTimeVO;
import com.example.octopus.entity.VOs.VideoTimeHistoryVO;
import com.example.octopus.entity.dataset.Dataset;
import com.example.octopus.entity.experiment.*;
import com.example.octopus.entity.experiment.Module;
import com.example.octopus.entity.project.Project;
import com.example.octopus.entity.project.ProjectModule;
import com.example.octopus.entity.project.SubProject;
import com.example.octopus.entity.user.Teacher;
import com.example.octopus.service.*;
import com.example.octopus.utils.CookieTokenUtils;
import com.example.octopus.utils.PropertiesUtil;
import com.example.octopus.utils.TokenCheckUtils;

import java.io.*;
import java.sql.Timestamp;
import java.text.ParseException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.PageSize;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.octopus.entity.user.Student;
import com.example.octopus.entity.user.Course;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;


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


    private final static String COOKIE_NAME = "cookiestu";

    private CookieTokenUtils cookieThings = new CookieTokenUtils();
    private PropertiesUtil propertiesUtil = new PropertiesUtil();


    private boolean cookieCheck(Model model, HttpServletRequest request) {
        // 检查cookie合法性
        TokenCheckUtils tokenCheck = cookieThings.validateToken(request, COOKIE_NAME);
        if (tokenCheck.isSuccess()) {
            model.addAttribute("stuname", tokenCheck.getUserName());
            return true;
        } else {
            logger.info(tokenCheck.getErrorType() + "  需要重新登录!");
            return false;
        }
    }

    @RequestMapping("/login")
    public String login(Model model, HttpServletRequest request, HttpServletResponse response) {
        cookieThings.deleteCookie(request, response, COOKIE_NAME);
        cookieThings.deleteCookie(request, response, "cookietea");
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
            cookieThings.setCookie(userNumber, stu.getName(),request, response, COOKIE_NAME);
            userService.updateLoginInfo(Long.parseLong(userNumber));
            return "redirect:/index";
        } else {
            //身份为教师或管理员，进入后台系统
            Teacher tea = teacherService.getTeacherByTeaNumber(Long.parseLong(userNumber));
            logger.info("当前登陆身份为：教师/管理员        欢迎您，" + userNumber + ":" + tea.getTeaName());
            cookieThings.setCookie(userNumber, tea.getTeaName(), request,response, "cookietea");
            teacherService.updateLoginInfo(Long.parseLong(userNumber));
            return "redirect:/admin_index";
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

    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request) {
        String stuNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        Long stuName = Long.parseLong(stuNumber);
        if (!stuNumber.equals(cookieThings.getCookieUserNum(request, COOKIE_NAME))) return "redirect:/";

        if (!cookieCheck(model, request)) return "redirect:/login";


        int chosenCourse = courseService.countCourseChosen(stuName);
        logger.info("chosenCourse:" + chosenCourse);
        model.addAttribute("chosenCourse",chosenCourse);

        int noFinished = chosenCourse - courseService.listCompletedCourses(stuName).size();
        logger.info("noFinished:" + noFinished);
        model.addAttribute("noFinished",noFinished);

        Timestamp lastlogintime = userService.getStudentByStuNumber(stuName).getLastLoginTime();
        logger.info("lastlogintime:" + lastlogintime);
        model.addAttribute("lastlogintime",lastlogintime);



        List<CourseTimeVO> videopro = videoProgressService.countStudyTimeByStuNumberGroupByCourseId(stuName);
        logger.info("videopro " + videopro);


        List<ExperimentTimeVO> experpro = experimentService.countExperimentTime(stuName);
        logger.info("experpro " + experpro);


        JSONArray videopros = new JSONArray();
        JSONArray experpros = new JSONArray();
        List<String> videoCoursenames = new ArrayList<>();
        List<String> experCoursenames = new ArrayList<>();



        for(int i=0;i<videopro.size();i++){
            JSONObject ob1 = new JSONObject();
            JSONObject ob2 = new JSONObject();
//            String s1 = courseService.getCourseById(videopro.get(i).getCourseId()).getCourseName();
            String s1 = courseService.getCourseById(videopro.get(i).getTeaCourseId()).getCourseName();
            String s2 = courseService.getCourseById(experpro.get(i).getTeaCourseId()).getCourseName();
            ob1.put("value",videopro.get(i).getTime());
            ob1.put("name",s1);
            videoCoursenames.add(s1);
            ob2.put("value",experpro.get(i).getTime());
            ob2.put("name",s2);
            experCoursenames.add(s2);
            videopros.add(ob1);
            experpros.add(ob2);
        }

        logger.info("videoCoursenames:" + videoCoursenames);
        model.addAttribute("videoCoursenames",videoCoursenames);
        logger.info("videopros:" + videopros);
        model.addAttribute("videopros",videopros);
        logger.info("experCoursenames:" + experCoursenames);
        model.addAttribute("experCoursenames",experCoursenames);
        logger.info("experpros:" + experpros);
        model.addAttribute("experpros",experpros);


        List<ExperimentTimeHistoryVO> alastExper = experimentService.getHistory7DaysExperimentTime(stuName);

        List<VideoTimeHistoryVO> alastvideo = videoProgressService.getHistory7DaysStudyTime(stuName);

        logger.info("alastExper:" + alastExper);
        logger.info("alastvideo:" + alastvideo);

        List<String> pastDaysList = new ArrayList<>();
        for (int i = 1; i <8; i++) {
            pastDaysList.add(getPastDate(i));
        }

        List<Integer> lastExper = new ArrayList<>();
        List<Integer> lastvideo = new ArrayList<>();

        int a = alastvideo.size()-1,b=alastExper.size()-1;
        for (int i=0;i<7;i++){
            if(a>=0){
                if(alastvideo.get(a).getDate().toString().equals(pastDaysList.get(i))){
                lastvideo.add(alastvideo.get(a).getTime());
                a= a-1;
                }else{
                lastvideo.add(0);
                }
            }else{
                lastvideo.add(0);
            }
            if(b>=0){
                if(alastExper.get(b).getDate().toString().equals(pastDaysList.get(i))){
                lastExper.add(alastExper.get(b).getTime());
                b--; }else{
                lastExper.add(0);
                }
            }else{
                lastExper.add(0);
            }
        }

        List<Integer> lastExper0 = new ArrayList<>();
        List<Integer> lastvideo0 = new ArrayList<>();
        List<String> pastDaysList0 = new ArrayList<>();
        for(int i=0;i<7;i++){
            lastExper0 .add(lastExper.get(6-i));
            lastvideo0.add(lastvideo.get(6-i));
            pastDaysList0.add(pastDaysList.get(6-i));
        }

        logger.info("lastExper:" + lastExper0);
        model.addAttribute("lastExper",lastExper0);
        logger.info("lastvideo:" + lastvideo0);
        model.addAttribute("lastvideo",lastvideo0);
        logger.info("pastDaysList:" +pastDaysList0);
        model.addAttribute("pastDaysList",pastDaysList0);




//        int experpros = subExperimentProgressService.countValidStudyTime(stuName);
//        logger.info("experpros:" + experpros);
//        model.addAttribute("experpros",experpros);



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
        String stuName = cookieThings.getCookieUserName(request, COOKIE_NAME);
        String stuNum = cookieThings.getCookieUserNum(request, COOKIE_NAME);


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

        Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
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


        String stuNum = cookieThings.getCookieUserNum(request, COOKIE_NAME);
        boolean isapplied = studentCourseService.isChosen(Long.parseLong(stuNum), Long.parseLong(id));

        logger.info("isapplied:" + isapplied);

        model.addAttribute("isapplied", isapplied);

        return "apply_detail";
    }

    @RequestMapping("/apply_detail/sure_apply")
    public String sure_apply(String id, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");


        String stuNum = cookieThings.getCookieUserNum(request, COOKIE_NAME);
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

        String stuNum = cookieThings.getCookieUserNum(request, COOKIE_NAME);
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

        Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));

        List<Course> mycourses = courseService.listCoursesByStuNumber(stuNum);
        logger.info("mycourses:" + mycourses);
        model.addAttribute("mycourses", mycourses);

        //这里要计算完成了多少个视频 算百分比

        List<Integer> pros = new ArrayList<>();
        for (int i=0;i<mycourses.size();i++) {
            logger.info("mycourse: " + mycourses.get(i).getCourseStaticId());
            double pro =  videoProgressService.getCourseProgress(mycourses.get(i).getId(),stuNum);
            pros.add((int)(pro*100));
        }
        logger.info("pros:" + pros);
        model.addAttribute("pros", pros);


        return "mycourse";
    }


    @RequestMapping("/course_video/{id}/{videoid}")
    public String course_video(@PathVariable(value = "id") String id,@PathVariable(value = "videoid") String videoid, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";


        model.addAttribute("id", id);

//        logger.info("courseid_id:" + id);
        Long courseid_id = Long.parseLong(id);
        Long currvideo_id = Long.parseLong(videoid);

        Course course = courseService.getCourseById(courseid_id);

//        logger.info("course——detail:" + course);
        model.addAttribute("course", course);

        List<Chapter> chapters = chapterService.listChaptersByCourseId(courseid_id);
        logger.info("chapters:" + chapters);
        if(chapters.size() == 0){
            return "redirect:/mycourse";
        }

//        logger.info("chapters:" + chapters);
//        logger.info("chapters:" + chapters.size());
        model.addAttribute("chapters", chapters);
        model.addAttribute("chaptersnum", chapters.size());

        List<List<Video>> videos = new ArrayList<>();
        List<List<Long>> tosubexperiments = new ArrayList<>();
        List<List<VideoProgress>> videopros = new ArrayList<>();
        Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));

        for (int i=0;i<chapters.size();i++){
//            logger.info("chaptersid:" + chapters.get(i).getId());
            List<Video> video = videoService.listVideosByChapterId(chapters.get(i).getChapterId());
            videos.add(video);

            List<VideoProgress> videopro = new ArrayList<>();
            List<Long> tosubs = new ArrayList<>();

            for (int j=0;j<video.size();j++){
                Long tosub = subExperimentService.getSubExperimentIdByVideoId(video.get(j).getId());
                VideoProgress videop = videoProgressService.getByVideoIdAndStuNumber(video.get(j).getId(),stuNum);
                videopro.add(videop);
                tosubs.add(tosub);
            }
            tosubexperiments.add(tosubs);
            videopros.add(videopro);
        }

//        logger.info("videos:" + videos);
        model.addAttribute("videos", videos);

        model.addAttribute("videopros", videopros);
//        logger.info("videopros:" + videopros);

//        logger.info("startvideo:" + videos.get(0).get(0));

        if(currvideo_id==0||currvideo_id==null){
            model.addAttribute("startvideo", videos.get(0).get(0));
            model.addAttribute("startvideopro", videopros.get(0).get(0));
        }else{
            Video startvideo = videoService.getById(currvideo_id);
            VideoProgress startvideopro = videoProgressService.getByVideoIdAndStuNumber(currvideo_id,stuNum);
            model.addAttribute("startvideo", startvideo);
            logger.info("startvideo:" + startvideo);
            model.addAttribute("startvideopro", startvideopro);
            logger.info("startvideopro:" + startvideopro);
        }



//        logger.info("tosubexperiments:" + tosubexperiments);
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

        logger.info("视频学习记录新增");

        Long id =Long.parseLong(request.getParameter("courseid"));
        String starttime =(request.getParameter("starttime"));
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
//        logger.info("startdate:" + startdate);
//        logger.info("enddate:" + startdate);

        VideoProgress videoProgress = new VideoProgress();
        videoProgress.setVideoId(videoid);
        videoProgress.setStuNumber(stuNum);
        videoProgress.setStartTime(startst);
        videoProgress.setEndTime(endst);
        videoProgress.setProgress(jindu);
        videoProgress.setLastVideoProgress(currenttime);
        videoProgress.setStudyTime(learntime);
        boolean issuccess = videoProgressService.insertVideoProgress(videoProgress);

//        logger.info("videoid:" + videoid);
//        logger.info("stuNum:" + stuNum);
//        logger.info("starttime:" + starttime);
//        logger.info("endtime:" + endtime);
//        logger.info("currenttime:" + currenttime);
//        logger.info("jindu:" + jindu);
//        logger.info("learntime:" + learntime);

        return "redirect:/course_video/"+id+"/"+videoid;
    }




    @RequestMapping("/experiment_task")
    public String experiment_task(Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);

        Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
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
            logger.info("experiment——module-sub:" + modules.get(i).getModuleId());
            List<SubExperiment> subExperiment = subExperimentService.listSubExperimentsByModuleId(modules.get(i).getModuleId());
            subExperiments.add(subExperiment);
        }
        logger.info("subExperiments:" + subExperiments);
        model.addAttribute("subExperiments", subExperiments);


        return "experiment_task_detail";
    }



    @RequestMapping("/experiment_machine/{id}")
    public String experiment_machine(@PathVariable("id")String id,Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);

        logger.info("实验机id:"+id);
        Long sub_id = Long.parseLong(id);
//        logger.info("sub_id:"+sub_id);

        SubExperiment subExperiment = subExperimentService.getById(sub_id);
        logger.info("subExperiment:"+subExperiment);
        model.addAttribute("subExperiment", subExperiment );

        Experiment experiment = experimentService.getExperimentById(subExperiment.getExperimentId());
//        logger.info("experiment:"+experiment);
        model.addAttribute("experiment", experiment );

        SubExperimentReportSave subsave = subExperimentReportSaveService.getLatest(sub_id,stuNum);
//        logger.info("subsave:"+subsave);
        String subcontent = "";
        if(subsave != null){
            subcontent = subsave.getContent();
        }
        logger.info("subcontent:"+subcontent);
        model.addAttribute("subcontent", subcontent);

        // 这里要调整
        Video video = videoService.getVideoBySubExperimentId(sub_id);
//        logger.info("video:"+video);
        if(video==null){
            model.addAttribute("isvideo", 0);
            model.addAttribute("videocourse", 0);
        }else{
            model.addAttribute("isvideo", 1);
//            logger.info("videocourse:"+video.getCourseId());
            model.addAttribute("videocourse", video.getCourseId());
            model.addAttribute("subvideoid", video.getId());
        }

        SubExperimentReportSubmit issub = subExperimentReportSubmitService.getByStuNumberAndSubExperimentId(sub_id,stuNum);
        if(issub == null){
            model.addAttribute("isexpsubmit", 0);
            logger.info("isexpsubmit:0");
        }else{
            model.addAttribute("isexpsubmit", 1);
            logger.info("isexpsubmit:1");
        }


        String docker_url = dockerService.getAddressByStuNumber(stuNum);
        boolean a =dockerService.updateStatusByStuNum(stuNum, 2, sub_id);
        model.addAttribute("docker_url", docker_url);

        model.addAttribute("expstarttime", new Date().getTime());
//        boolean b = subExperimentProgressService.insert(stuNum,sub_id);


        return "experiment_machine";

    }

    @RequestMapping("/imageupload")
    @ResponseBody
    public JSONObject imageUpload(@RequestParam("editormd-image-file") MultipartFile file) {
        JSONObject jsonObject = new JSONObject();
        if(file != null) {
            String path =propertiesUtil.getExpImageSavePath();
            String fileName ="exp" +  UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            logger.info("fileName:"+fileName);
            try {
            File destFile = new File(path + "/" + fileName);
            file.transferTo(destFile);
            String exppicurl = propertiesUtil.getExpPicPath()+fileName;
            jsonObject.put("url", exppicurl);
            jsonObject.put("success", 1);
            jsonObject.put("message", "upload success!");
            return jsonObject;
            }catch (Exception e){
            return  null;
        }
        }
        jsonObject.put("success", 0);
        jsonObject.put("message", "upload error!");
        return jsonObject;
    }


//    @RequestMapping(value = "sendExperImage", headers = ("content-type=multipart/*"), method = RequestMethod.POST)
//    @ResponseBody
//    public String sendExperImage(@RequestParam("file") MultipartFile file, Model model,HttpServletRequest request) throws Exception {
////        logger.info("图片上传");
//        try {
//            String path =propertiesUtil.getExpImageSavePath();
//            String fileName ="exp" +  UUID.randomUUID().toString().replace("-", "") + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
////            logger.info("fileName:"+fileName);
//            File destFile = new File(path + "/" + fileName);
//            file.transferTo(destFile);
////            FileUtils.copyInputStreamToFile(file.getInputStream(), destFile);
//            //上面代码是拷贝到本地的，但是因为感觉并不人性化，所以采取了上传到图片服务器的思路
////            InputStream inputStream=file.getInputStream();
//
////            String url = null;
////            Boolean flag= FtpFileUtil.uploadFile(fileName,inputStream);
////            if(flag){
////                url = fileName;
////            }
//
////             String url = destFile.getAbsolutePath();
////            logger.info("url:"+url);
//            for(int i = 0;i<50;i++){
//                if(destFile.exists()){
//                    Map<String,Object> params = new HashMap<>();
//                    params.put("state", "success");
//                    String exppic = propertiesUtil.getExpPicPath();
//                    params.put("picurl", exppic+fileName);
//                    return JSONArray.toJSON(params).toString();
//                }
//            }
//            Map<String,Object> params = new HashMap<>();
//            params.put("state", "fail");
//            params.put("picurl", "");
//            return JSONArray.toJSON(params).toString();
//        }catch (Exception e){
//            return  null;
//        }
//    }
//
//
//    @PostMapping(value = "/deleteExpImage")
//    @ResponseBody
//    public String deleteExpImage(HttpServletRequest request) {
//
//        String picUrl = request.getParameter("picUrl");
//        logger.info("deletepicUrl:" + picUrl);
//        String filename = picUrl.replace("http://localhost:8080/static/","");
//        String filepath = propertiesUtil.getExpImageSavePath();
//
//        File file = new File(filepath+filename);
//        Map<String,Object> params = new HashMap<>();
//        if (file.exists()) {
//            file.delete();
//            params.put("state", "success");
////            System.out.println("===========删除成功=================");
//        } else {
//            params.put("state", "fail");
////            System.out.println("===============删除失败==============");
//        }
//        return JSONArray.toJSON(params).toString();
//    }

    @PostMapping(value="/saveExperText")
    @ResponseBody
    public void saveExperText(Model model, HttpServletRequest request) {
        Long experid = Long.parseLong(request.getParameter("experid"));
        Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        String text = request.getParameter("text");
//        logger.info("experid:" + experid);
//        logger.info("text:" + text);
        SubExperimentReportSave sub = new SubExperimentReportSave();
        sub.setSubExperimentId(experid);
        sub.setStuNumber(stuNum);
        sub.setContent(text);
//        subExperimentReportSaveService.update(sub);
        subExperimentReportSaveService.insert(sub);
    }

    @PostMapping(value="/submitExperText")
    @ResponseBody
    public void submitExperText(HttpServletRequest request) {
        Long experid = Long.parseLong(request.getParameter("experid"));
        Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        String text = request.getParameter("text");
        logger.info("experid:" + experid);
        logger.info("text:" + text);
        SubExperimentReportSubmit subexpsub = new SubExperimentReportSubmit();
        subexpsub.setSubExperimentId(experid);
        subexpsub.setContent(text);
        subexpsub.setStuNumber(stuNum);
        subExperimentReportSubmitService.insert(subexpsub);


//        String alltext = "<html><head></head><body>"+text+"</body></html>";

//        String savepath = propertiesUtil.getPdfSubmitPath();
//        String savepdfname = "exp"+UUID.randomUUID().toString().replace("-", "")+".pdf";
//        Document document = new Document(PageSize.A4);
//        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(savepath + savepdfname));
//        document.open();
        //        document.close();
//        pdfWriter.close();
        //
//        HTMLWorker htmlWorker = new HTMLWorker(document);
//        htmlWorker.parse(new StringReader(text));
//        ByteArrayInputStream bin = new ByteArrayInputStream(alltext.getBytes());
//        XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, bin, Charset.forName("UTF-8"));


//        InputStream stream = new ByteArrayInputStream(text.toString().getBytes("UTF-8"));
//        XMLWorkerHelper.getInstance().parseXHtml(pdfWriter, document, stream);
//        XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
//        worker.parseXHtml(pdfWriter, document, new StringReader(text));

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


        long project_id = Long.parseLong(id);

        logger.info("project_id:" + project_id);


        Project project = projectService.getProjectById(project_id);
        logger.info("project——detail:" + project);
        model.addAttribute("project", project);

        List<ProjectModule> modules = projectModuleService.listByProjectId(project_id);
        logger.info("project——module:" + modules);
        logger.info("project——module-size:" + modules.size());
        model.addAttribute("modules", modules);
        model.addAttribute("modulesnum", modules.size());

        List<List<SubProject>> subprojects = new ArrayList<>();
        for (int i = 0; i < modules.size(); i++) {
            logger.info("subprojects——module-sub:" + modules.get(i).getModuleId());
            List<SubProject> subproject = subProjectService.listSubProjectsByModuleId(modules.get(i).getModuleId());
            subprojects.add(subproject);
        }
        logger.info("subprojects:" + subprojects);
        model.addAttribute("subprojects", subprojects);

        return "project_detail";
    }

    @RequestMapping("/project_machine/{id}")
    public String project_machine(@PathVariable("id")String id,Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);

        logger.info("project实验机id:"+id);
        Long sub_id = Long.parseLong(id);
//        logger.info("sub_id:"+sub_id);

        SubProject subProject = subProjectService.getById(sub_id);
        logger.info("subProject:"+subProject);
        model.addAttribute("subProject", subProject);

        Project project = projectService.getProjectById(subProject.getProjectId());
//        logger.info("experiment:"+experiment);
        model.addAttribute("project", project );

//申请虚拟机资源
        String docker_url = dockerService.getAddressByStuNumber(stuNum);
        boolean a =dockerService.updateStatusByStuNum(stuNum, 1, sub_id);
        model.addAttribute("docker_url", docker_url);
        //实验学习记录
//        SubExperimentProgress subexppro = new SubExperimentProgress();
////        subexppro.setStuNumber(stuNum);
////        subexppro.setSubExperimentId(sub_id);

        return "project_machine";

    }






    @RequestMapping("/studylog")
    public String studylog(Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        List<VideoProgress> videopros = videoProgressService.listByStuNumber(stuNum);
        model.addAttribute("videopros",  videopros);
        logger.info("videopros :" + videopros );

        List<Video> videos = new ArrayList<>();
        for (int i = 0; i < videopros.size(); i++) {
            Video v = videoService.getById(videopros.get(i).getVideoId());
            videos.add(v);
        }
        logger.info("videos:" + videos);
        model.addAttribute("videos", videos);


        List<SubExperimentProgress>  experpros = subExperimentProgressService.listByStuNumber(stuNum);
        logger.info("experpros:" + experpros);
        model.addAttribute("experpros", experpros);

        List<SubExperiment> subexpers = new ArrayList<>();
        List<Long> courseids = new ArrayList<>();
        for (int i = 0; i < experpros.size(); i++) {
            SubExperiment w = subExperimentService.getById(experpros.get(i).getSubExperimentId());
            subexpers.add(w);
            Long c = courseService.getCourseIdByExperimentId(w.getExperimentId());
            courseids.add(c);
        }
        logger.info("subexpers:" + subexpers);
        model.addAttribute("subexpers", subexpers);
        logger.info("courseids:" + courseids);
        model.addAttribute("courseids", courseids);

        return "studylog";
    }



    @RequestMapping("/studylog_detail/{id}")
    public String studylog_detail(@PathVariable(value = "id") String id,Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long cour_id = Long.parseLong(id);
        Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        Course course = courseService.getCourseById(cour_id);
        model.addAttribute("course",  course);
        logger.info("course:" + course );


        List<VideoProgress> videopros = videoProgressService.listByCourseIdAndStuNumber(cour_id,stuNum);
        Integer videoprosnum = videopros.size();
        int videoprostudynum = videoProgressService.getStudyTimeByCourseIdAndStuNumber(cour_id,stuNum);
        model.addAttribute("videopros",  videopros);
        logger.info("videopros:" + videopros );
        model.addAttribute("videoprosnum",  videoprosnum);
        logger.info("videoprosnum:" + videoprosnum );
        model.addAttribute("videoprostudynum",  videoprostudynum);
        logger.info("videoprostudynum:" +videoprostudynum );

        List<Video> videos = new ArrayList<>();
        for (int i = 0; i < videopros.size(); i++) {
            Video v = videoService.getById(videopros.get(i).getVideoId());
            videos.add(v);
        }
        logger.info("videos:" + videos);
        model.addAttribute("videos", videos);



        Long experid = experimentService.getExperimentByCourseId(cour_id).getId();
        List<SubExperimentProgress>  experpros = subExperimentProgressService.listByStuNumberAndExperimentId(stuNum,experid);
        logger.info("experpros:" + experpros);
        model.addAttribute("experpros", experpros);

        List<SubExperiment> subexpers = new ArrayList<>();
        List<Long> courseids = new ArrayList<>();
        for (int i = 0; i < experpros.size(); i++) {
            SubExperiment w = subExperimentService.getById(experpros.get(i).getSubExperimentId());
            subexpers.add(w);
            Long c = courseService.getCourseIdByExperimentId(w.getExperimentId());
            courseids.add(c);
        }
        int experprostudynum = subExperimentProgressService.countValidStudyTimeOnExperiment(stuNum,experid);
        logger.info("subexpers:" + subexpers);
        model.addAttribute("subexpers", subexpers);
        logger.info("courseids:" + courseids);
        model.addAttribute("courseids", courseids);
        model.addAttribute("experprostudynum",  experprostudynum);
        logger.info("experprostudynum:" +experprostudynum);





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


    @PostMapping(value = "/datasetdownload")
    @ResponseBody
    public String datasetdownload(HttpServletRequest request) {
        Long datasetid = Long.parseLong(request.getParameter("datasetid"));
        logger.info("datasetid:" + datasetid);
        datasetService.increaseDownloadNum(datasetid);
        Dataset d = datasetService.getDatasetById(datasetid);
        Map<String,Object> params = new HashMap<>();
        logger.info("datasetnum:" + d.getDownloadNum());
        params.put("num",d.getDownloadNum());
        return JSONArray.toJSON(params).toString();

    }


//    @PostMapping(value = "/applymachine")
//    @ResponseBody
//    public String applymachine(HttpServletRequest request) {
//        logger.info("申请资源");
//        Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
//        String type = request.getParameter("type");
//        String sid = request.getParameter("id");
//        Long id = Long.parseLong(sid);
//        String docker_url = dockerService.getAddressByStuNumber(stuNum);
//        if(type=="experiment"){
//            boolean a =dockerService.updateStatusByStuNum(stuNum, 2, id);
//        }else{
//            boolean b =dockerService.updateStatusByStuNum(stuNum, 1, id);
//        }
//        Map<String,Object> params = new HashMap<>();
//
//        params.put("url", docker_url);
//
//        return JSONArray.toJSON(params).toString();
//    }

    @PostMapping(value = "/sleepmachine")
    @ResponseBody
    public void sleepmachine(HttpServletRequest request) {
        logger.info("注销资源");
        Long experid = Long.parseLong(request.getParameter("experid"));
        Long stime = Long.parseLong(request.getParameter("start_time"));
        logger.info("stime"+stime);
        Timestamp start_time = new Timestamp(stime);

        Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        boolean a =dockerService.updateStatusByStuNum(stuNum, 0, 0);

        Long dd = new Date().getTime();
        Long interval = Math.round((double)(dd-stime)/1000);
        logger.info("dd"+dd);
        logger.info("dd"+stime);
        logger.info("intervaltime"+interval);
        Timestamp end_time = new Timestamp(dd);
        SubExperimentProgress sp = new SubExperimentProgress();
        sp.setSubExperimentId(experid);
        sp.setStartTime(start_time);
        sp.setStuNumber(stuNum);
        sp.setEndTime(end_time);
        sp.setValidStudyTime(interval.intValue());

        boolean b = subExperimentProgressService.insert(sp);
    }

}
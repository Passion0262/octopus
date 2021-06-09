package com.example.octopus.controller;

import com.example.octopus.entity.dataset.Dataset;
import com.example.octopus.entity.experiment.Experiment;
import com.example.octopus.entity.project.Project;
import com.example.octopus.service.*;
import com.example.octopus.utils.CookieTokenUtils;
import com.example.octopus.utils.TokenCheckUtils;
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
import java.util.List;

@Controller
public class studentController {

    private Logger logger = LoggerFactory.getLogger(studentController.class);

    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;
    @Autowired
    ExperimentService experimentService;
    @Autowired
    ProjectService projectService;
    @Autowired
    DatasetService datasetService;

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

        String stuNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        Student stu = userService.getStudentByStuNumber(Long.parseLong(stuNumber));
        logger.info("当前登陆用户：" + stuNumber + ":" + stu.getName());

        // 创建带token的cookie，token中包含用户id和name
//        CookieTokenUtils cookieTokenUtils = new CookieTokenUtils();
        cookieThings.setCookie(stuNumber, stu.getName(), response, cookieName);

//        model.addAttribute("stu", stu);
//        session.setAttribute("stuname",stu.getName());
//        return "home";
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request) {
        String stuNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!stuNumber.equals(cookieThings.getCookieUserNum(request, cookieName))) return "redirect:/";

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

        Long stuNum =Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        boolean status = userService.updatePhoneNumber(stuNum,phoneNumber);

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
        boolean isapplied = courseService.isChosen(Long.parseLong(stuNum),Long.parseLong(id));

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
        boolean issuccess = courseService.insertChooseCourse(Long.parseLong(id),Long.parseLong(stuNum));

        boolean isapplied = courseService.isChosen(Long.parseLong(stuNum),Long.parseLong(id));
        model.addAttribute("isapplied", isapplied);

        return "redirect:/apply_detail/" + id;
    }

    @RequestMapping("/apply_detail/cancel_apply")
    public String cancel_apply(String id, Model model,HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");

        logger.info("取消申请_id:" + id);

        String stuNum = cookieThings.getCookieUserNum(request, cookieName);
        logger.info("确认课程_id:" + id);
        logger.info("确认学生_id:" + stuNum);
        boolean issuccess = courseService.deleteChooseCourse(Long.parseLong(id),Long.parseLong(stuNum));

        boolean isapplied = courseService.isChosen(Long.parseLong(stuNum),Long.parseLong(id));
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
    public String course_video(@PathVariable(value = "id") String id,Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";


        model.addAttribute("id", id);

//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);



        return "course_video";
    }














    @RequestMapping("/experiment_task")
    public String experiment_task(Model model,HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);

        Long stuNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        List<Experiment> allexperiments = experimentService.listChosenByStuNumber(stuNum);
        logger.info("allExperiment:" + allexperiments);
        model.addAttribute("allexperiments", allexperiments);

        return "experiment_task";
    }


    @RequestMapping("/experiment_task_detail/{id}")
    public String experiment_task_detail(String id, Model model,HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);

        logger.info("experimentMission_id:" + id);

        //        这里需要一个根据实验的id 返回course操作
        Experiment experiment = experimentService.listExperiments().get(0);

        logger.info("experiment——detail:" + experiment);
        model.addAttribute("experiment", experiment);


        return "experiment_task_detail";
    }


    @RequestMapping("/experiment_machine")
    public String experiment_machine(Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);

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
    public String project_detail(String id, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);

        logger.info("project_id:" + id);

        //        这里需要一个根据实验的id 返回course操作
        Project project = projectService.listProjects().get(0);
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
    public String dataset_detail(String id, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return "redirect:/login";

//        String stuname = (String) session.getAttribute("stuname");
//        model.addAttribute("stuname", stuname);

        logger.info("dataset_id:" + id);

        //        这里需要一个根据数据集的id 返回dataset操作
        Dataset dataset = datasetService.listDatasets().get(0);

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

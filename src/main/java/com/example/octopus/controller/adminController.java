package com.example.octopus.controller;

import com.alibaba.fastjson.JSON;
import com.example.octopus.entity.user.*;
import com.example.octopus.service.*;
import com.example.octopus.utils.CookieTokenUtils;
import com.example.octopus.utils.TokenCheckUtils;
import com.example.octopus.utils.UploadFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
public class adminController {

    private Logger logger = LoggerFactory.getLogger(adminController.class);

    @Autowired
    TeacherService teacherService;

    @Autowired
    UserService userService;

    @Autowired
    MajorService majorService;

    @Autowired
    ClassService classService;

    @Autowired
    CourseService courseService;

    @Autowired
    StudentCourseService studentcourseService;

    @Autowired
    ExperimentService experimentService;

    @Autowired
    ProjectService projectService;

    @Autowired
    DatasetService datasetService;

    @Autowired
    VideoService videoService;

    private final static String cookieName = "cookie_";

    private CookieTokenUtils cookieThings = new CookieTokenUtils();


    private boolean cookieCheck(Model model, HttpServletRequest request) {
        // 检查cookie合法性
        TokenCheckUtils tokenCheck = cookieThings.validateToken(request, cookieName);
        if (tokenCheck.isSuccess()) {
            model.addAttribute("username", tokenCheck.getUserName());
            return true;
        } else {
            logger.info(tokenCheck.getErrorType() + "  需要重新登录!");
            return false;
        }
    }


    //登录
    @RequestMapping("/admin_login")
    public String admin_login() {
        return "redirect:/login";
    }

//    //登录验证 跳转到首页
//    @PostMapping("/admin_confirmlogin")
//    public String admin_confirmlogin(@RequestParam("username") String username, @RequestParam("userpwd") String userpwd, HttpSession session) {
//        logger.info("用户名：" + username);
//        logger.info("密码：" + userpwd);
//        session.setAttribute("user", username);
//        return "redirect:/admin_index";
//    }

    //个人资料
    @RequestMapping("/admin_userinfo")
    public String admin_userinfo(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        
        model.addAttribute("username", teaName);
        model.addAttribute("userinfo", teacherService.getTeacherByTeaNumber(teaNum));
        return "admin_userinfo";
    }

    //重置密码
    @PostMapping("/admin_reset_pwd_confirm")
    public String admin_reset_pwd_confirm(HttpServletRequest request) {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phonenumber = request.getParameter("phonenumber");
        String new_pwd = request.getParameter("new_pwd");
        logger.info("用户名：" + username);
        logger.info("邮箱：" + email);
        logger.info("手机号码：" + phonenumber);
        logger.info("新密码：" + new_pwd);
        //重置密码
        return "admin_reset_pwd";
    }

    @RequestMapping("/admin_reset_pwd")
    public String admin_reset_pwd(Model model) {
        //model.addAttribute("username", "李四");
        return "admin_reset_pwd";
    }


    //首页
    @RequestMapping("/admin_index")
    public String confirmlogin(HttpServletRequest request, Model model) {
        String teaNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!teaNumber.equals(cookieThings.getCookieUserNum(request, cookieName))) return "redirect:/";

        if (!cookieCheck(model, request)) return "redirect:/login";

        // 获取用户名及用户id的方法使用如下语句
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        String role = "teacher";
        if (teaNum == 1){
            role = "admin";
        }

        try {
            logger.info("用户名：" + teaName);
            model.addAttribute("username", teaName);
            model.addAttribute("role", role);
            model.addAttribute("sizeof_experiments", experimentService.listExperiments().size());
            model.addAttribute("sizeof_projects", experimentService.listExperiments().size());
            model.addAttribute("sizeof_datasets", datasetService.listDatasets().size());
            return "admin_index";
        } catch (Exception e) {
            return "";
        }
    }


    //专业管理
    @RequestMapping("/admin_major")
    public String admin_major(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        logger.info("进入专业管理");
        model.addAttribute("username", teaName);
        if (teaNum == 1) {
            model.addAttribute("majors", majorService.listMajors());
            model.addAttribute("role", "admin");
        }
        else {
            model.addAttribute("majors", majorService.getByTeaNumber(teaNum));
            model.addAttribute("role", "teacher");
        }
        return "admin_major";
    }

    //增加专业
    @GetMapping("/admin_major_add")
    public ModelAndView admin_major_add(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        if (teaNum == 1) {
            logger.info("进入admin_major_add，获取一个新Major()");
            model.addAttribute("major", new Major());
            model.addAttribute("username", teaName);
            model.addAttribute("role", "admin");
        }
        return new ModelAndView("admin_major_add", "majormodel", model);
    }

    @PostMapping("/add_major")
    public ModelAndView add_major(Major major, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        if (teaNum == 1){
            logger.info("提交新增的major: [{}]", major);
            majorService.insertMajor(major);
        }
        else{
            logger.info("[{}]没有新增专业权限！", teaName);
        }
        return new ModelAndView("redirect:/admin_major", "majormodel", model);
    }

    //修改专业
    @GetMapping("/admin_major_edit")
    public ModelAndView admin_major_edit(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        logger.info("进入admin_major_edit，获取指定编号的Major()");
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        if (teaNum == 1) {
            model.addAttribute("username", teaName);
            model.addAttribute("role", "admin");
            long id = Long.parseLong(request.getParameter("id"));
            logger.info("id=" + id);
            model.addAttribute("major", majorService.getById(id));
        }
        return new ModelAndView("admin_major_edit", "majormodel", model);
    }

    @PostMapping("/edit_major")
    public ModelAndView edit_major(Major major, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        if (teaNum == 1){
            logger.info("提交修改的major: [{}]", major);
            majorService.updateMajor(major);
        }
        else{
            logger.info("[{}]没有修改专业权限！", teaName);
        }
        return new ModelAndView("redirect:/admin_major", "majormodel", model);
    }

    //删除专业
    @RequestMapping("/admin_major_delete")
    public ModelAndView admin_major_delete(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        if (teaNum == 1){
            long id = Long.parseLong(request.getParameter("id"));
            logger.info("删除专业 id=[{}]", id);
            majorService.deleteById(id);
        }
        else{
            logger.info("[{}]没有删除专业权限！", teaName);
        }
        return new ModelAndView("redirect:/admin_major", "majormodel", model);
    }


    //班级管理
    @RequestMapping("/admin_class")
    public String admin_class(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        if (teaNum == 1) {
            logger.info("进入班级管理");
            model.addAttribute("classes", classService.listClass_s());
            model.addAttribute("username", teaName);
            model.addAttribute("role", "admin");
            return "admin_class";
        }
        else{
            return "redirect:/login";
        }
    }

    //增加班级
    @GetMapping("/admin_class_add")
    public ModelAndView admin_class_add(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        if (teaNum == 1) {
            logger.info("进入admin_major_add，获取一个新Class_()");
            model.addAttribute("class", new Class_());
            model.addAttribute("username", teaName);
            model.addAttribute("role", "admin");
        }
        return new ModelAndView("admin_class_add", "classmodel", model);
    }

    @PostMapping("/add_class")
    public ModelAndView add_class(Class_ class_, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        if (teaNum == 1) {
            logger.info("提交新增的class_: [{}]", class_);
            // todo class_.major = majorService.getMajorNameById(class_.major);
            classService.insertClass(class_);
        }
        else{
            logger.info("[{}]没有新增班级权限！", teaName);
        }
        return new ModelAndView("redirect:/admin_class", "classmodel", model);
    }

    //修改班级
    @GetMapping("/admin_class_edit")
    public ModelAndView admin_class_edit(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        if (teaNum == 1) {
            long id = Long.parseLong(request.getParameter("id"));
            logger.info("admin_class_edit，获取指定id的Class_(), class.id=", id);
            model.addAttribute("class", classService.getClass_Byid(id));
            //model.addAttribute("majors", majorService.listMajors());
            model.addAttribute("username", teaName);
            model.addAttribute("role", "admin");
        }
        return new ModelAndView("admin_class_edit", "classmodel", model);
    }

    @PostMapping("/edit_class")
    public ModelAndView edit_class(Class_ class_, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        if (teaNum == 1) {
            logger.info("提交修改的class_: [{}]", class_);
            // todo class_.major = majorService.getMajorNameById(class_.major);
            // todo 提交到数据库
        }
        else{
            logger.info("[{}]没有修改班级权限！", teaName);
        }
        return new ModelAndView("redirect:/admin_class", "classmodel", model);
    }

    //删除班级
    @RequestMapping("/admin_class_delete")
    public ModelAndView admin_class_delete(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        if (teaNum == 1) {
            String className = request.getParameter("className");
            logger.info("删除 className=" + className);
            classService.deleteByClassName(className);
        }
        else{
            logger.info("[{}]没有删除班级权限！", teaName);
        }
        return new ModelAndView("redirect:/admin_class", "classmodel", model);
    }

    //学生管理
    @RequestMapping("/admin_student")
    public String admin_student(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        model.addAttribute("username", teaName);
        logger.info("进入学生管理");
        if (teaNum == 1) {
            model.addAttribute("students", userService.listStudents());
            model.addAttribute("role", "admin");
        }
        else{
            model.addAttribute("students", userService.listStudentsByTeaNumber(teaNum));
            model.addAttribute("role", "teacher");
        }
        return "admin_student";
    }

    //增加学生
    @GetMapping("/admin_student_add")
    public ModelAndView admin_student_add(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        if (teaNum == 1) {
            logger.info("进入admin_student_add，获取一个新Student()");
            model.addAttribute("student", new Student());
            model.addAttribute("majors", majorService.listMajors());
            model.addAttribute("classes", classService.listClass_s());
            model.addAttribute("role", "admin");
            model.addAttribute("username", teaName);
        }
        return new ModelAndView("admin_student_add", "stumodel", model);
    }

    @PostMapping("/add_student")
    public ModelAndView add_student(Student student, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        if (teaNum == 1) {
            logger.info("提交新增的student: [{}]", student);
            userService.insertStudent(student);
        }
        else{
            logger.info("[{}]没有新增学生权限！", teaName);
        }
        return new ModelAndView("redirect:/admin_student", "stumodel", model);
    }

    //修改学生
    @GetMapping("/admin_student_edit")
    public ModelAndView admin_student_edit(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        if (teaNum == 1) {
            long stuNumber = Long.parseLong(request.getParameter("stuNumber"));
            logger.info("admin_student_edit，获取指定名字的Student(),stuNumber=" + stuNumber);
            Student student = userService.getStudentByStuNumber(stuNumber);
            model.addAttribute("student", student);
            model.addAttribute("majors", majorService.listMajors());
            model.addAttribute("classes", classService.listClass_s());
            model.addAttribute("role", "admin");
            model.addAttribute("username", teaName);
        }
        return new ModelAndView("admin_student_edit", "stumodel", model);
    }

    @PostMapping("/edit_student")
    public ModelAndView edit_student(Student student, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        if (teaNum == 1) {
            logger.info("提交修改的student: [{}]", student);
            userService.updateStudent(student);
        }
        else{
            logger.info("[{}]没有修改学生权限！", teaName);
        }
        return new ModelAndView("redirect:/admin_student", "stumodel", model);
    }

    //删除学生
    @RequestMapping("/admin_student_delete")
    public ModelAndView admin_student_delete(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        if (teaNum == 1) {
            String stuNumber = request.getParameter("stuNumber");
            logger.info("删除 stuNumber=" + stuNumber);
            //todo 删除学生
        }
        else{
            logger.info("[{}]没有删除学生权限！", teaName);
        }
        return new ModelAndView("redirect:/admin_student", "stumodel", model);
    }

    //开课计划管理
    @RequestMapping("/admin_course")
    public String admin_course(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        model.addAttribute("username", teaName);
        logger.info("进入开课计划管理");
        model.addAttribute("courses", courseService.listCourses());
        return "admin_course";
    }

    //课程增加
    @GetMapping("/admin_course_add")
    public ModelAndView admin_course_add(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        logger.info("进入admin_course_add，获取一个新Course()");
        model.addAttribute("course", new Course());
        return new ModelAndView("admin_course_add", "coursemodel", model);
    }

    @PostMapping("/add_course")
    public ModelAndView add_course(Course course, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        logger.info("提交新增的course: [{}]", course);
        courseService.insertCourse(course);
        return new ModelAndView("redirect:/admin_course", "coursemodel", model);
    }

    //修改课程
    @GetMapping("/admin_course_edit")
    public ModelAndView admin_course_edit(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        logger.info("进入admin_course_edit，获取指定名字的Course()");
        long courseId = Long.parseLong(request.getParameter("courseId"));
        logger.info("courseId=" + courseId);
        model.addAttribute("course", courseService.getCourseById(courseId));
        return new ModelAndView("admin_course_edit", "coursemodel", model);
    }

    @PostMapping("/edit_course")
    public ModelAndView edit_course(Course course, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        logger.info("提交修改的course: [{}]", course);
        courseService.updateCourse(course);
        return new ModelAndView("redirect:/admin_course", "coursemodel", model);
    }

    //删除课程
    @RequestMapping("/admin_course_delete")
    public ModelAndView admin_course_delete(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        long courseId = Long.parseLong(request.getParameter("courseId"));
        logger.info("删除 courseId=" + courseId);
        courseService.deleteCourseById(courseId);
        return new ModelAndView("redirect:/admin_course", "coursemodel", model);
    }

    //学生开课计划管理
    @RequestMapping("/admin_course_student")
    public String admin_course_student(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        model.addAttribute("username", teaName);
        logger.info("进入学生开课计划管理");
        //返回
        model.addAttribute("course_students", studentcourseService.listStudentCoursesByTeaNumber(teaNum));
        return "admin_course_student";
    }

    //学生开课计划增加
    @GetMapping("/admin_course_student_add")
    public ModelAndView admin_course_student_add(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        logger.info("进入admin_course_student_add，Student_Course()");
        model.addAttribute("course_student", new StudentCourse());
        return new ModelAndView("admin_course_student_add", "coursestudentmodel", model);
    }

    @PostMapping("/add_course_student")
    public ModelAndView add_course_student(StudentCourse course_student, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        logger.info("提交新增的course_student: [{}]", course_student);
        // todo 向数据库提交新增的course_student
        return new ModelAndView("redirect:/admin_course_student");
    }

//    //修改学生开课计划
//    @GetMapping("/admin_course_student_edit")
//    public ModelAndView admin_course_student_edit(HttpServletRequest request, Model model){
//        logger.info("进入admin_course_student_edit，获取指定名字的Student_Course()");
//        long id = Long.parseLong(request.getParameter("id"));
//        logger.info("id="+id);
//        String user = request.getParameter("username");
//        //查找对应数据
//        model.addAttribute("username", user);
//        model.addAttribute("course_student",new StudentCourse());
//        return new ModelAndView("admin_course_student_edit","coursestudentmodel",model);
//    }
//
//    @PostMapping("/edit_course_student")
//    public ModelAndView edit_course_student(StudentCourse course_student){
//        logger.info("提交修改的course_student: [{}]", course_student);
//        //修改学生开课计划
//        return new ModelAndView("redirect:/admin_course_student");
//    }

    //删除学生开课计划
    @RequestMapping("/admin_course_student_delete")
    public ModelAndView admin_course_student_delete(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        long courseId = Long.parseLong(request.getParameter("id"));
        logger.info("删除 courseId=" + courseId);
        // todo 数据库删除对应数据
        return new ModelAndView("redirect:/admin_course_student", "coursestudentmodel", model);
    }

    //视频学习汇总 仅展示
    @RequestMapping("/admin_video_log")
    public String admin_video_log(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        model.addAttribute("username", teaName);

        // todo 请求数据返回
        //model.addAttribute("video_logs", videoService.listVideoLogs());

        return "admin_video_log";
    }

    //视频学习详情 仅展示
    @RequestMapping("/admin_video_log_details")
    public String admin_video_log_details(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        model.addAttribute("username", teaName);

        // todo 请求数据返回
        //model.addAttribute("video_log_details", videoService.listVideoLogDetails());

        return "admin_video_log_details";
    }

    // 实验报告管理
    @RequestMapping("/admin_report")
    public String admin_report(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        model.addAttribute("username", teaName);
        return "admin_report";
    }

    //实验报告详情
    @RequestMapping("/admin_report_detail")
    public String admin_report_detail(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        model.addAttribute("username", teaName);
        model.addAttribute("pdf", "https://arxiv.org/pdf/1508.01006v1.pdf");
        return "admin_report_detail";
    }

    //提交实验报告评分
    @PostMapping("/report_score")
    public ModelAndView report_score( Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        logger.info("提交报告分数: [{}]", "xxx");
        // todo 向数据库提交报告评分
        return new ModelAndView("redirect:/admin_report");
    }


    //实验操作时长 仅展示
    @RequestMapping("/admin_experiment_log")
    public String admin_experiment_log(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        model.addAttribute("username", teaName);

        // todo 请求数据返回
        //model.addAttribute("experiment_logs", );
        return "admin_experiment_log";
    }

    // 实验机类型管理
    @RequestMapping("/admin_pc_type")
    public String admin_pc_type(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        model.addAttribute("username", teaName);
        return "admin_pc_type";
    }

    // 实验机集群管理
    @RequestMapping("/admin_cluster")
    public String admin_cluster(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        model.addAttribute("username", teaName);
        return "admin_cluster";
    }

    // 学生实验机管理
    @RequestMapping("/admin_student_pc")
    public String admin_student_pc(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        model.addAttribute("username", teaName);
        return "admin_student_pc";
    }

    // 实验报告模板
    @RequestMapping("/admin_report_template")
    public String admin_report_template(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        model.addAttribute("username", teaName);
        return "admin_report_template";
    }

    // 视频管理
    @RequestMapping("/admin_video")
    public String admin_video(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        model.addAttribute("username", teaName);
        System.out.println(videoService.listVideos());
        model.addAttribute("video", videoService.listVideos());
        return "admin_video";
    }

    // 视频分类管理
    @RequestMapping("/admin_video_class")
    public String admin_video_class(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        model.addAttribute("username", teaName);
        return "admin_video_class";
    }

    //数据集管理
    @RequestMapping("/admin_dataset")
    public String admin_dataset(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        model.addAttribute("username", teaName);
        model.addAttribute("datasets", datasetService.listDatasets());
        return "admin_dataset";
    }

    // 获取所有的专业
    @ResponseBody
    @RequestMapping("/get_all_major")
    public Map get_all_major(HttpServletRequest request) {
        Map<String, Object> major = new HashMap<String, Object>();
        List<Major> majors = majorService.listMajors();
        major.put("majors", majors);
        return major;
    }

    // 获取某专业的所有班级
    @ResponseBody
    @RequestMapping("/get_class_by_major")
    public Map get_class_by_major(HttpServletRequest request) {
        Map<String, Object> classes = new HashMap<String, Object>();
        Long majorId = Long.parseLong(request.getParameter("major"));
        // todo classes.put(classes.put(classService.getClassByMajor()));
        return classes;
    }

    //上传图片
    @ResponseBody
    @RequestMapping("/upload_img")
    public Map imageUpload(MultipartFile img, HttpServletRequest request) {
        String result_msg = "";  //上传结果信息
        Map<String, Object> root = new HashMap<String, Object>();

        if (img.getSize() > 1024 * 1024 * 5) {
            result_msg = "图片大小不能超过5M";
        } else {

//            Map dict = new HashMap();
//            dict.put("admin_course_add", "\\course");
//            dict.put("admin_userinfo", "\\course");
            String target_dir = "";
            String url = request.getHeader("Referer");
            //logger.info("提交post请求的url:" + url);
            if (url.contains("admin_course")) {
                target_dir = "course";
            } else if (url.contains("admin_userinfo")) {
                target_dir = "userinfo";
            }

            //判断上传文件格式
            String fileType = img.getContentType();
            if (fileType.equals("image/jpeg") || fileType.equals("image/png") || fileType.equals("image/jpg")) {
                // 要上传的目标文件存放的绝对路径
                final String localPath = "static\\images\\upload\\" + target_dir;
                //上传后保存的文件名(需要防止图片重名导致的文件覆盖)
                //获取文件名
                String fileName = img.getOriginalFilename();
                //获取文件后缀名
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                //重新生成文件名
                fileName = UUID.randomUUID() + suffixName;
                if (UploadFileUtils.upload(img, localPath, fileName)) {
                    //文件存放的相对路径(一般存放在数据库用于img标签的src)
                    String relativePath = "static/images/upload/" + target_dir + "/" + fileName;
                    root.put("relativePath", relativePath);//前端根据是否存在该字段来判断上传是否成功
                    result_msg = "图片上传成功";
                } else {
                    result_msg = "图片上传失败!";
                }
            } else {
                result_msg = "图片格式不正确!";
            }
        }

        root.put("result_msg", result_msg);

//        JSON.toJSONString(root,SerializerFeature.DisableCircularReferenceDetect);
        String root_json = JSON.toJSONString(root);
        logger.info(root_json);
        return root;
    }



    //上传文件
    @ResponseBody
    @RequestMapping("/upload_file")
    public Map fileUpload(MultipartFile file, HttpServletRequest request) {
        String result_msg = "";  //上传结果信息
        Map<String, Object> root = new HashMap<String, Object>();

        //判断上传文件格式
        String fileType = file.getContentType();
        System.out.println(fileType);
        if (fileType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document") ||
                fileType.equals("application/pdf") ||
                fileType.equals("application/msword")) {
            // 要上传的目标文件存放的绝对路径
            final String localPath = "static\\reportsTemp";
            //上传后保存的文件名(需要防止图片重名导致的文件覆盖)
            //获取文件名
            String fileName = file.getOriginalFilename();
//            //获取文件后缀名
//            String suffixName = fileName.substring(fileName.lastIndexOf("."));
//            //重新生成文件名
//            fileName = UUID.randomUUID()+suffixName;
            if (UploadFileUtils.upload(file, localPath, fileName)) {
                //文件存放的相对路径(一般存放在数据库用于img标签的src)
                String relativePath = "static/reportsTemp/" + fileName;
                root.put("relativePath", relativePath);//前端根据是否存在该字段来判断上传是否成功
                result_msg = "文件上传成功";
            } else {
                result_msg = "文件上传失败";
            }
        } else {
            result_msg = "文件格式不正确";
        }


        root.put("result_msg", result_msg);

//        JSON.toJSONString(root,SerializerFeature.DisableCircularReferenceDetect);
        String root_json = JSON.toJSONString(root);
        logger.info(root_json);
        return root;
    }

}

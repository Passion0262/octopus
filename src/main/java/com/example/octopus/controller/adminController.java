package com.example.octopus.controller;

import com.alibaba.fastjson.JSON;
import com.example.octopus.entity.VOs.SubExperimentOperateTimeVO;
import com.example.octopus.entity.dataset.Dataset;
import com.example.octopus.entity.experiment.SubExperimentProgress;
import com.example.octopus.entity.experiment.SubExperimentReportSubmit;
import com.example.octopus.entity.experiment.Video;
import com.example.octopus.entity.project.Project;
import com.example.octopus.entity.project.SubProject;
import com.example.octopus.entity.user.*;
import com.example.octopus.service.*;
import com.example.octopus.utils.CookieTokenUtils;
import com.example.octopus.utils.TokenCheckUtils;
import com.example.octopus.utils.UploadFileUtils;
//import com.example.octopus.utils.TimeTransUtils;
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
import java.util.*;


@Controller
public class adminController {

    private Logger logger = LoggerFactory.getLogger(adminController.class);

    @Autowired
    SysUserRoleService sysUserRoleService;

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
    SubExperimentService subExperimentService;

    @Autowired
    SubExperimentProgressService subExperimentProgressService;

    @Autowired
    ProjectService projectService;

    @Autowired
    DatasetService datasetService;

    @Autowired
    VideoService videoService;

    @Autowired
    VideoProgressService videoProgressService;

    @Autowired
    SubExperimentReportSubmitService subExperimentReportSubmitService;

    @Autowired
    DockerService dockerService;

    private final static String COOKIE_NAME = "cookietea";

    private CookieTokenUtils cookieThings = new CookieTokenUtils();


    private boolean cookieCheck(Model model, HttpServletRequest request) {
        // 检查cookie合法性
        TokenCheckUtils tokenCheck = cookieThings.validateToken(request, COOKIE_NAME);
        if (tokenCheck.isSuccess()) {
            long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
            int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师
            model.addAttribute("username", tokenCheck.getUserName());
            if (role_id == 1){
                model.addAttribute("role", "admin");
                return true;
            }
            else if (role_id == 3){
                model.addAttribute("role", "teacher");
                return true;
            }
            else{
                return false;
            }
        } else {
            logger.info(tokenCheck.getErrorType() + "  需要重新登录!");
            return false;
        }
    }

    //error
    @RequestMapping("/admin_error")
    public String admin_error() {
        return "admin_error";
    }


    //登录
    @RequestMapping("/admin_login")
    public String admin_login() {
        return "redirect:/login";
    }


    //个人资料
    @RequestMapping("/admin_userinfo")
    public String admin_userinfo(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        try {
            logger.info("进入admin_userinfo");
            model.addAttribute("userinfo", teacherService.getTeacherByTeaNumber(teaNum));
            //System.out.println(teacherService.getTeacherByTeaNumber(teaNum));
            return "admin_userinfo";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }

    @PostMapping("/edit_userinfo")
    public ModelAndView edit_userinfo(Teacher teacher, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        //System.out.println(teacher);
        try {
            logger.info("修改个人资料[{}]", teacher);
            teacherService.updateTeacher(teacher);
            return new ModelAndView("redirect:/admin_index");
        }
            catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }


    //首页
    @RequestMapping("/admin_index")
    public String confirmlogin(HttpServletRequest request, Model model) {
        String teaNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!teaNumber.equals(cookieThings.getCookieUserNum(request, COOKIE_NAME))) return "redirect:/";

        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        try {
            model.addAttribute("sizeof_experiments", experimentService.listExperiments().size());
            model.addAttribute("sizeof_projects", experimentService.listExperiments().size());
            model.addAttribute("sizeof_datasets", datasetService.listDatasets().size());
            if (role_id == 1){
                model.addAttribute("sizeof_courses", courseService.listCourses().size());
            }
            else{
                model.addAttribute("sizeof_courses", courseService.listCoursesByTeaNumber(teaNum).size());
            }

            model.addAttribute("sizeof_videos", videoService.listVideos().size());
           // model.addAttribute("sizeof_schools", );
            model.addAttribute("sizeof_teachers", teacherService.getAllTeachers().size());
            model.addAttribute("sizeof_students", userService.listStudents().size());
            model.addAttribute("sizeof_dockers", dockerService.getDockerListByRole(teaNum).size());
            model.addAttribute("sizeof_active_dockers", dockerService.getDockerListByRoleAndAwake(teaNum, true).size());
            model.addAttribute("lastLoginTime", teacherService.getTeacherByTeaNumber(teaNum).getLastLoginTime());
            model.addAttribute("sumExperimentTime", teacherService.getSumExperimentTimeByRole(teaNum));
            model.addAttribute("sumVideoTime", teacherService.getSumVideoTimeByRole(teaNum));
            model.addAttribute("docker_status", dockerService.countDockerByStatus(teaNum));
//            logger.info("实验时间：{}", teacherService.getSumExperimentTimeByRole(teaNum));
//            logger.info("视频时间：{}", teacherService.getSumVideoTimeByRole(teaNum));
            return "admin_index";
        } catch (Exception e) {
            return "redirect:/";
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //专业管理
    @RequestMapping("/admin_major")
    public String admin_major(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        logger.info("进入专业管理");
        try {
            if (role_id == 1) {
                model.addAttribute("majors", majorService.listMajors());
            } else {
                model.addAttribute("majors", majorService.getByTeaNumber(teaNum));
            }
            return "admin_major";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }

    //增加专业
    @GetMapping("/admin_major_add")
    public ModelAndView admin_major_add(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  //获取角色，管理员还是教师

        try {
            // 只有管理员可以增加专业
            if (role_id == 1) {
                logger.info("进入admin_major_add，获取一个新Major()");
                model.addAttribute("major", new Major());
            }
            return new ModelAndView("admin_major_add", "majormodel", model);
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    @PostMapping("/add_major")
    public ModelAndView add_major(Major major, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, COOKIE_NAME);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        try{
            if (role_id == 1){
                logger.info("提交新增的major: [{}]", major);
                majorService.insertMajor(major);
            }
            else{
                logger.info("[{}]没有新增专业权限！", teaName);
            }
            return new ModelAndView("redirect:/admin_major");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    //修改专业
    @GetMapping("/admin_major_edit")
    public ModelAndView admin_major_edit(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        try{
            // 只有管理员可以修改专业
            if (role_id == 1) {
                long id = Long.parseLong(request.getParameter("id"));
                logger.info("进入admin_major_edit，获取指定编号的Major(),id=[{}]", id);
                model.addAttribute("major", majorService.getById(id));
            }
            return new ModelAndView("admin_major_edit", "majormodel", model);
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    @PostMapping("/edit_major")
    public ModelAndView edit_major(Major major, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, COOKIE_NAME);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        try {
            if (role_id == 1) {
                logger.info("提交修改的major: [{}]", major);
                majorService.updateMajor(major);
            } else {
                logger.info("[{}]没有修改专业权限！", teaName);
            }
            return new ModelAndView("redirect:/admin_major");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    //删除专业
    @RequestMapping("/admin_major_delete")
    public ModelAndView admin_major_delete(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, COOKIE_NAME);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        try {
            // todo 删除专业涉及到相关班级、学生、教师
            long id = Long.parseLong(request.getParameter("id"));
            logger.info("删除专业 id=[{}]", id);
            if (role_id == 1) {
                majorService.deleteById(id);
            } else {
                logger.info("[{}]没有删除专业权限！", teaName);
            }
            return new ModelAndView("redirect:/admin_major");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //班级管理
    @RequestMapping("/admin_class")
    public String admin_class(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        try {
            logger.info("进入班级管理");
            if (role_id == 1) {
                model.addAttribute("classes", classService.listClass_s());
                return "admin_class";
            }
            else if (role_id == 3) {
                model.addAttribute("classes", classService.listSchoolClassesByUserId(teaNum));
                return "admin_class";
            }
            return "redirect:/admin_error";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }

    //增加班级
    @GetMapping("/admin_class_add")
    public ModelAndView admin_class_add(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        try {
            logger.info("进入admin_major_add，获取一个新Class_()");
            model.addAttribute("class", new Class_());
            return new ModelAndView("admin_class_add", "classmodel", model);
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    @PostMapping("/add_class")
    public ModelAndView add_class(Class_ class_, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        try {
            logger.info("提交新增的class_: [{}]", class_);
            if (role_id == 3){
                // 如果是教师账号，学校只能是该教师所在学校
                class_.setSchool(teacherService.getTeacherByTeaNumber(teaNum).getSchool());
            }
            classService.insertClass(class_);
            return new ModelAndView("redirect:/admin_class");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    //修改班级
    @GetMapping("/admin_class_edit")
    public ModelAndView admin_class_edit(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        long id = Long.parseLong(request.getParameter("id"));
        logger.info("admin_class_edit，获取指定id的Class_(), class.id=[{}]", id);
        try {
            model.addAttribute("class", classService.getClass_Byid(id));
            return new ModelAndView("admin_class_edit", "classmodel", model);
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    @PostMapping("/edit_class")
    public ModelAndView edit_class(Class_ class_, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        logger.info("提交修改的class_: [{}]", class_);
        try {
            classService.updateClass(class_);
            return new ModelAndView("redirect:/admin_class");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    //删除班级
    @RequestMapping("/admin_class_delete")
    public ModelAndView admin_class_delete(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, COOKIE_NAME);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        long id = Long.parseLong(request.getParameter("id"));
        logger.info("删除 class.id=[{}]", id);
        try {
            // todo 删除班级涉及到相关学生
            classService.deleteByClassId(id);
            return new ModelAndView("redirect:/admin_class");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //学生管理
    @RequestMapping("/admin_student")
    public String admin_student(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        logger.info("进入学生管理");
        try {
            if (role_id == 1) {
                model.addAttribute("students", userService.listStudents());
            } else {
                model.addAttribute("students", userService.listStudentsByTeaNumber(teaNum));
            }
            return "admin_student";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }

    //增加学生
    @GetMapping("/admin_student_add")
    public ModelAndView admin_student_add(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        logger.info("进入admin_student_add，获取一个新Student()");

        try {
            model.addAttribute("student", new Student());
            return new ModelAndView("admin_student_add", "stumodel", model);
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    @PostMapping("/add_student")
    public ModelAndView add_student(Student student, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        student.setPassword("123");
        logger.info("提交新增的student: [{}]", student);
        try {
            userService.insertStudent(student, teaNum);
            return new ModelAndView("redirect:/admin_student");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    //修改学生
    @GetMapping("/admin_student_edit")
    public ModelAndView admin_student_edit(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long stuNumber = Long.parseLong(request.getParameter("stuNumber"));
        logger.info("admin_student_edit，获取指定名字的Student(),stuNumber=[{}]", stuNumber);
        try {
            Student student = userService.getStudentByStuNumber(stuNumber);
            model.addAttribute("student", student);
            return new ModelAndView("admin_student_edit", "stumodel", model);
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    @PostMapping("/edit_student")
    public ModelAndView edit_student(Student student, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        logger.info("提交修改的student: [{}]", student);
//        try {
            userService.updateStudent(student);
            return new ModelAndView("redirect:/admin_student");
//        }
//        catch (Exception e){
//            return new ModelAndView("redirect:/admin_error");
//        }
    }

    //删除学生
//    @RequestMapping("/admin_student_delete")
//    public ModelAndView admin_student_delete(HttpServletRequest request, Model model) {
//        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
//        long stuNumber = Long.parseLong(request.getParameter("stuNumber"));
//        logger.info("删除 stuNumber=[{}]", stuNumber);
//        return new ModelAndView("redirect:/admin_student");
//
//    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //学校管理
//    @RequestMapping("/admin_school")
//    public String admin_school(HttpServletRequest request, Model model) {
//        if (!cookieCheck(model, request)) return "redirect:/login";
//
//        logger.info("进入学校管理");
//        //model.addAttribute("schools", );
//        return "admin_school";
//    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    //教师管理
    @RequestMapping("/admin_teacher")
    public String admin_teacher(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师
        // 只有管理员能够进入教师管理
        if (role_id == 1) {
            logger.info("进入教师管理");
            try {
                model.addAttribute("teachers", teacherService.getAllTeachers());
                //System.out.println(teacherService.getAllTeachers());
                return "admin_teacher";
            }
            catch (Exception e){
                return "redirect:/admin_error";
            }
        }
        else{
            return "redirect:/login";
        }
    }

    //增加教师
    @GetMapping("/admin_teacher_add")
    public ModelAndView admin_teacher_add(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师
        // 只有管理员能够增加教师
        if (role_id == 1) {
            logger.info("进入admin_teacher_add，获取一个新Teacher()");
            try {
                model.addAttribute("teacher", new Teacher());
                return new ModelAndView("admin_teacher_add", "teamodel", model);
            }
            catch (Exception e){
                return new ModelAndView("redirect:/admin_error");
            }
        }
        else{
            return new ModelAndView("redirect:/login");
        }
    }

    @PostMapping("/add_teacher")
    public ModelAndView add_teacher(Teacher teacher, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师
        // 只有管理员能够增加教师
        if (role_id == 1) {
            logger.info("提交新增的teacher: [{}]", teacher);
            try {
                teacherService.addTeacher(teacher);
                return new ModelAndView("redirect:/admin_teacher");
            }
            catch (Exception e){
                return new ModelAndView("redirect:/admin_error");
            }
        }
        else{
            return new ModelAndView("redirect:/login");
        }
    }

    //修改教师
    @GetMapping("/admin_teacher_edit")
    public ModelAndView admin_teacher_edit(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        if (role_id == 1) {
            long teaNumber = Long.parseLong(request.getParameter("teaNumber"));
            logger.info("admin_teacher_edit，获取指定名字的Teacher(),teaNumber=[{}]", teaNumber);
            try {
                Teacher teacher = teacherService.getTeacherByTeaNumber(teaNumber);
                model.addAttribute("teacher", teacher);
                return new ModelAndView("admin_teacher_edit", "teamodel", model);
            }
            catch (Exception e){
                return new ModelAndView("redirect:/admin_error");
            }
        }
        else{
            return new ModelAndView("redirect:/login");
        }
    }

    @PostMapping("/edit_teacher")
    public ModelAndView edit_teacher(Teacher teacher, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        if (role_id == 1) {
            logger.info("提交修改的teacher: [{}]", teacher);
            try {
                teacherService.updateTeacher(teacher);
                return new ModelAndView("redirect:/admin_teacher");
            }
            catch (Exception e){
                return new ModelAndView("redirect:/admin_error");
            }
        }
        else{
            return new ModelAndView("redirect:/login");
        }

    }

    //删除教师
//    @RequestMapping("/admin_teacher_delete")
//    public ModelAndView admin_teacher_delete(HttpServletRequest request, Model model) {
//        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
//        long teaNum = Long.parseLong(request.getParameter("teaNumber"));
//        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师
//
//        if (role_id == 1) {
//            logger.info("删除 teaNumber=[{}]", teaNum);
//            try {
//                teacherService.deleteTeacher(teaNum);
//                return new ModelAndView("redirect:/admin_teacher");
//            }
//            catch (Exception e){
//                return new ModelAndView("redirect:/admin_error");
//            }
//        }
//        else{
//            return new ModelAndView("redirect:/login");
//        }
//
//    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    //开课计划管理
    @RequestMapping("/admin_course")
    public String admin_course(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        logger.info("进入开课计划管理");
        try {
            if (role_id == 1) {
                model.addAttribute("courses", courseService.listCourses());
            } else {
                model.addAttribute("courses", courseService.listCoursesByTeaNumber(teaNum));
            }
            return "admin_course";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }

    //课程增加
    @GetMapping("/admin_course_add")
    public ModelAndView admin_course_add(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, COOKIE_NAME);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        logger.info("进入admin_course_add，获取一个新Course()");
        try {
            if (role_id == 1) {
                model.addAttribute("course", new Course());
            } else {
                // 教师账号只能给自己增加开课计划
                Course course = new Course();
                course.setTeaNumber(teaNum);
                course.setTeaName(teaName);
                model.addAttribute("course", course);
            }

            return new ModelAndView("admin_course_add", "coursemodel", model);
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    @PostMapping("/add_course")
    public ModelAndView add_course(Course course, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        logger.info("提交新增的course: [{}]", course);
        try {
            if (course.getTeaName() == null) {
                // 如果只有账号没有姓名 则补充姓名
                course.setTeaName(teacherService.getTeacherByTeaNumber(course.getTeaNumber()).getTeaName());
            }
            if (role_id == 3) {
                long id = Long.parseLong(course.getCourseName());
                Course c = courseService.getCourseById(id);
                course.setCourseName(c.getCourseName());
                course.setImagePath(c.getImagePath());
            }

            courseService.insertCourse(course);
            return new ModelAndView("redirect:/admin_course");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    //修改课程
    @GetMapping("/admin_course_edit")
    public ModelAndView admin_course_edit(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long courseId = Long.parseLong(request.getParameter("courseId"));
        logger.info("进入admin_course_edit，获取指定名字的Course(), courseId=" + courseId);
        try {
            model.addAttribute("course", courseService.getCourseById(courseId));
            return new ModelAndView("admin_course_edit", "coursemodel", model);
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    @PostMapping("/edit_course")
    public ModelAndView edit_course(Course course, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        logger.info("提交修改的course: [{}]", course);
        try {
            if (course.getTeaName() == null) {
                // 如果只有账号没有姓名 则补充姓名
                course.setTeaName(teacherService.getTeacherByTeaNumber(course.getTeaNumber()).getTeaName());
            }
            courseService.updateCourse(course);
            return new ModelAndView("redirect:/admin_course");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    //删除课程
    @RequestMapping("/admin_course_delete")
    public ModelAndView admin_course_delete(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        long courseId = Long.parseLong(request.getParameter("courseId"));
        logger.info("删除 courseId=" + courseId);
        try {
            courseService.deleteCourseById(courseId);
            return new ModelAndView("redirect:/admin_course");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    //学生开课计划管理
    @RequestMapping("/admin_course_student")
    public String admin_course_student(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        logger.info("进入学生开课计划管理");
        try {
            if (role_id == 1) {
                model.addAttribute("course_students", studentcourseService.listStudentCourses());
            } else {
                model.addAttribute("course_students", studentcourseService.listStudentCoursesByTeaNumber(teaNum));
            }
            return "admin_course_student";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }

    //学生开课计划增加
    @GetMapping("/admin_course_student_add")
    public ModelAndView admin_course_student_add(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        logger.info("进入admin_course_student_add，Student_Course()");
        try {
            model.addAttribute("course_student", new StudentCourse());
            return new ModelAndView("admin_course_student_add", "coursestudentmodel", model);
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    @PostMapping("/add_course_student")
    public ModelAndView add_course_student(StudentCourse course_student, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        // 补充信息
//        Course course = courseService.getCourseById(course_student.getCourseId());
//        Student stu = userService.getStudentByStuNumber(course_student.getStuNumber());
//
//        course_student.setCourseName(course.getCourseName());
//        course_student.setTeaNumber(course.getTeaNumber());
//        course_student.setTeaName(course.getTeaName());
//        course_student.setStuName(stu.getName());
//        course_student.setStuMajor(stu.getMajorName());
//        course_student.setStuClass(stu.getClassName());

        logger.info("提交新增的course_student: [{}]", course_student);
        try {
            //studentcourseService.insertStudentCourse(course_student);
            studentcourseService.insertStudentCourse(course_student.getStuNumber(), course_student.getCourseId());
            return new ModelAndView("redirect:/admin_course_student");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    //修改学生开课计划
    @GetMapping("/admin_course_student_edit")
    public ModelAndView admin_course_student_edit(HttpServletRequest request, Model model){
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        long id = Long.parseLong(request.getParameter("id"));
        logger.info("进入admin_course_student_edit，获取指定名字的Student_Course(),id=[{}]", id);
        try {
            model.addAttribute("course_student", studentcourseService.getById(id));

            return new ModelAndView("admin_course_student_edit", "coursestudentmodel", model);
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    @PostMapping("/edit_course_student")
    public ModelAndView edit_course_student(StudentCourse course_student){

        // 补充信息
//        Course course = courseService.getCourseById(course_student.getCourseId());
//        Student stu = userService.getStudentByStuNumber(course_student.getStuNumber());
//
//        course_student.setCourseName(course.getCourseName());
//        course_student.setTeaNumber(course.getTeaNumber());
//        course_student.setTeaName(course.getTeaName());
//        course_student.setStuName(stu.getName());
//        course_student.setStuMajor(stu.getMajorName());
//        course_student.setStuClass(stu.getClassName());

        logger.info("提交修改的course_student: [{}]", course_student);
        try {
            studentcourseService.insertStudentCourse(course_student.getStuNumber(), course_student.getCourseId());
            return new ModelAndView("redirect:/admin_course_student");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    //删除学生开课计划
    @RequestMapping("/admin_course_student_delete")
    public ModelAndView admin_course_student_delete(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        long id = Long.parseLong(request.getParameter("id"));
        logger.info("删除 id=[{}]", id);
        try {
            studentcourseService.deleteStudentCourse(id);
            return new ModelAndView("redirect:/admin_course_student");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    //视频学习汇总 仅展示
    @RequestMapping("/admin_video_log")
    public String admin_video_log(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        try {
            model.addAttribute("video_logs", videoService.getVideoStudySummaryByRole(teaNum));
            return "admin_video_log";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    //视频学习详情 仅展示
    @RequestMapping("/admin_video_log_details")
    public String admin_video_log_details(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        //System.out.println(videoProgressService.getVideoProgressDetailByRole(teaNum));
//        try {
            model.addAttribute("video_log_details", videoProgressService.getVideoProgressDetailByRole(teaNum));
            return "admin_video_log_details";
//        }
//        catch (Exception e){
//            return "redirect:/admin_error";
//        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    // 实验报告管理
    @RequestMapping("/admin_report")
    public String admin_report(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师
        //System.out.println(subExperimentReportSubmitService.listByTeaNumber(3));
        try {
            if (role_id == 1){
                model.addAttribute("reports", subExperimentReportSubmitService.listAll());
            }
            else if (role_id == 3){
                model.addAttribute("reports", subExperimentReportSubmitService.listByTeaNumber(teaNum));
            }
            return "admin_report";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }

    //实验报告详情
    @RequestMapping("/admin_report_detail")
    public String admin_report_detail(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
//        try {
            long id = Long.parseLong(request.getParameter("id"));
            SubExperimentReportSubmit expSub = subExperimentReportSubmitService.getById(id);
            model.addAttribute("expSub", expSub);
            return "admin_report_detail";
//        }
//        catch (Exception e){
//            return "redirect:/admin_error";
//        }
    }

    //提交实验报告评分
    @PostMapping ("/report_score")
    public ModelAndView report_score(Model model, SubExperimentReportSubmit subExperimentReportSubmit,HttpServletRequest request) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        try {
            long subExperimentId = subExperimentReportSubmit.getSubExperimentId();
            long stuNumber = subExperimentReportSubmit.getStuNumber();
            long teaNumber = subExperimentReportSubmit.getTeaNumber();
            int score = subExperimentReportSubmit.getScore();
            logger.info("提交报告分数: subExperimentId="+subExperimentId+
                    ", stuNumber:"+stuNumber+
                    ", teaNumber:"+teaNumber+
                    ", score:"+score);
            subExperimentReportSubmitService.updateByExamine(subExperimentId, stuNumber, teaNumber, score);
            return new ModelAndView("redirect:/admin_report");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    //实验操作时长 仅展示
    @RequestMapping("/admin_experiment_log")
    public String admin_experiment_log(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        try {
            model.addAttribute("experiment_logs", subExperimentProgressService.getOperateTimeByRole(teaNum));
            return "admin_experiment_log";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // 实验详情
    @RequestMapping("/admin_experiment_detail")
    public String admin_experiment_detail(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，
        try{
//            System.out.println();
            model.addAttribute("subExperiments", subExperimentService.listSubExperimentByRole(teaNum));
            return "admin_experiment_detail";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }

    // 实验机类型管理
    @RequestMapping("/admin_pc_type")
    public String admin_pc_type(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        try {
            if (role_id == 1) {

                //model.addAttribute("pc_types", );
            } else {

                //model.addAttribute("pc_types", );
            }
            return "admin_pc_type";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    // 实验机集群管理
    @RequestMapping("/admin_cluster")
    public String admin_cluster(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        try {
            if (role_id == 1) {
                //model.addAttribute("clusters", );
            } else {
                //model.addAttribute("clusters", );
            }
            return "admin_cluster";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    // 学生实验机管理
    @RequestMapping("/admin_student_pc")
    public String admin_student_pc(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师
        try {
            model.addAttribute(dockerService.getDockerListByRole(teaNum));

//            if (role_id == 1) {
//            } else {
//
//                //model.addAttribute("student_pc", );
//            }
            return "admin_student_pc";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    // 实验报告模板
//    @RequestMapping("/admin_report_template")
//    public String admin_report_template(HttpServletRequest request, Model model) {
//        if (!cookieCheck(model, request)) return "redirect:/login";
//
//        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
//        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师
//
//        if(role_id == 1){
//
//            //model.addAttribute("report_template", );
//        }
//        else{
//
//            //model.addAttribute("report_template", );
//        }
//        return "admin_report_template";
//    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // 实战项目管理
    @RequestMapping("/admin_project")
    public String admin_project(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师
//        System.out.println(projectService.listProjects());
        try {
            model.addAttribute("projects", projectService.listProjects());
            return "admin_project";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // 实战项目详情
    @RequestMapping("/admin_project_detail")
    public String admin_project_detail(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，
        try{
//            System.out.println();
//            model.addAttribute("subprojects", );
            return "admin_project_detail";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    // 视频管理
    @RequestMapping("/admin_video")
    public String admin_video(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        try {
            if (role_id == 1) {
                model.addAttribute("video", videoService.getAllVideoManageInfo());
            } else {
                model.addAttribute("video", videoService.getAllVideoManageInfo());
            }
            return "admin_video";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }

    //视频增加
    @GetMapping("/admin_video_add")
    public ModelAndView admin_video_add(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        logger.info("进入admin_video_add，获取一个新Video()");
        try {
            if (role_id == 1) {
                model.addAttribute("video", new Video());
            } else {
                return new ModelAndView("redirect:/admin_video");
            }
            return new ModelAndView("admin_video_add", "videomodel", model);
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    @PostMapping("/add_video")
    public ModelAndView add_video(Video video, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        logger.info("提交新增的dataset: [{}]", video);
        try {
            videoService.addVideo(video);
            return new ModelAndView("redirect:/admin_video");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    //修改视频
    @GetMapping("/admin_video_edit")
    public ModelAndView admin_video_edit(HttpServletRequest request, Model model){
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        long id = Long.parseLong(request.getParameter("id"));
        logger.info("进入admin_video_edit，获取指定id的Video(),id=[{}]", id);

        try {
            model.addAttribute("video", videoService.getById(id));
            return new ModelAndView("admin_video_edit", "videomodel", model);
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    @PostMapping("/edit_video")
    public ModelAndView edit_video(Video video, Model model, HttpServletRequest request){
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        logger.info("提交修改的video: [{}]", video);
        try {
            videoService.updateVideo(video);
            return new ModelAndView("redirect:/admin_video");
        }catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    //删除视频
    @RequestMapping("/admin_video_delete")
    public ModelAndView admin_video_delete(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        long id = Long.parseLong(request.getParameter("id"));
        logger.info("删除 video.id=[{}]", id);
        try {
            videoService.deleteVideoById(id);
            return new ModelAndView("redirect:/admin_video");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // 视频分类管理
    @RequestMapping("/admin_video_class")
    public String admin_video_class(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        try {
            if (role_id == 1) {

                //model.addAttribute("video", videoService.listVideos());
            } else {

                //model.addAttribute("video", videoService.listVideos());
            }
            return "admin_video_class";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    //数据集管理
    @RequestMapping("/admin_dataset")
    public String admin_dataset(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        try {
            model.addAttribute("datasets", datasetService.listDatasets());
            return "admin_dataset";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }

    //数据集增加
    @GetMapping("/admin_dataset_add")
    public ModelAndView admin_dataset_add(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        logger.info("进入admin_dataset_add，获取一个新的Dataset()");
        try {
            model.addAttribute("dataset", new Dataset());
            return new ModelAndView("admin_dataset_add", "datasetmodel", model);
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    @PostMapping("/add_dataset")
    public ModelAndView add_dataset(Dataset dataset, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        logger.info("提交新增的dataset: [{}]", dataset);
        try {
            datasetService.addDataset(dataset);
            return new ModelAndView("redirect:/admin_dataset");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    //修改数据集
    @GetMapping("/admin_dataset_edit")
    public ModelAndView admin_dataset_edit(HttpServletRequest request, Model model){
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        long id = Long.parseLong(request.getParameter("id"));
        logger.info("进入admin_dataset_edit，获取指定id的Dataset(),id=[{}]", id);
        try {
            model.addAttribute("dataset", datasetService.getDatasetById(id));
            return new ModelAndView("admin_dataset_edit", "datasetmodel", model);
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    @PostMapping("/edit_dataset")
    public ModelAndView edit_dataset(Dataset dataset){
        logger.info("提交修改的dataset: [{}]", dataset);
        try {
            datasetService.updateDatasetById(dataset);
            return new ModelAndView("redirect:/admin_dataset");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    //删除数据集
    @RequestMapping("/admin_dataset_delete")
    public ModelAndView admin_dataset_delete(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        long id = Long.parseLong(request.getParameter("id"));
        logger.info("删除 dataset.id=[{}]", id);
        try {
            datasetService.deleteDataset(id);
            return new ModelAndView("redirect:/admin_dataset");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
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
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师
        Map<String, Object> classes = new HashMap<String, Object>();
        Long majorId = Long.parseLong(request.getParameter("major"));
        logger.info("获取专业id=[{}]下的班级", majorId);
        if (role_id == 1){
            // 管理员返回所有班级
            classes.put("class_", classService.listClassesByMajorId(majorId));
        }
        else{
            // 教师只返回自己学校的班级
            classes.put("class_", classService.listSchoolMajorClassesByUserId(teaNum, majorId));
        }
        return classes;
    }

    // 获取所有的开课计划
    @ResponseBody
    @RequestMapping("/get_all_course")
    public Map get_all_course(HttpServletRequest request) {
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        Map<String, Object> course = new HashMap<String, Object>();
        List<Course> courses = new ArrayList<Course>();
        if (role_id == 1) {
            courses = courseService.listCourses();
        }
        else{
            courses = courseService.listCoursesByTeaNumber(teaNum);
        }
        course.put("courses", courses);
        return course;
    }

    // 获取所有的课程（目前是管理员开通的课程）
    @ResponseBody
    @RequestMapping("/get_course")
    public Map get_course(HttpServletRequest request) {
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        //int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        Map<String, Object> course = new HashMap<String, Object>();
        List<Course> courses = courseService.listCourses();
        List<Course> selected_courses = new ArrayList<>();
        for (int i=0; i<courses.size(); i++){
            Course c = courses.get(i);
            if(sysUserRoleService.getRoleIdByUserId(c.getTeaNumber()) == 1){
                selected_courses.add(c);
            }
        }
        course.put("courses", selected_courses);
        return course;
    }

    // 下载数据集，下载次数+1
    @ResponseBody
    @RequestMapping("/download_dataset")
    public String download_dataset(HttpServletRequest request) {

        Long id = Long.parseLong(request.getParameter("id"));
        logger.info("数据集下载，id=[{}]", id);
        datasetService.increaseDownloadNum(id);
        return "";
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
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
            } else if (url.contains("admin_dataset")) {
                target_dir = "dataset";
            }

            //判断上传文件格式
            String fileType = img.getContentType();
            if (fileType.equals("image/jpeg") || fileType.equals("image/png") || fileType.equals("image/jpg")) {
                // 要上传的目标文件存放的绝对路径
                final String localPath = "static\\images\\" + target_dir;
                //上传后保存的文件名(需要防止图片重名导致的文件覆盖)
                //获取文件名
                String fileName = img.getOriginalFilename();
                //获取文件后缀名
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                //重新生成文件名
                fileName = UUID.randomUUID() + suffixName;
                String result = UploadFileUtils.upload(img, localPath, fileName, false);
                while (result.equals("exists")){
                    //重新生成文件名
                    fileName = UUID.randomUUID() + suffixName;
                    result = UploadFileUtils.upload(img, localPath, fileName, false);
                }
                if (result.equals("true")) {
                    //文件存放的相对路径(一般存放在数据库用于img标签的src)
                    String relativePath = "static/images/" + target_dir + "/" + fileName;
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
//    @ResponseBody
//    @RequestMapping("/upload_file")
//    public Map fileUpload(MultipartFile file, HttpServletRequest request) {
//        String result_msg = "";  //上传结果信息
//        Map<String, Object> root = new HashMap<String, Object>();
//
//        //判断上传文件格式
//        String fileType = file.getContentType();
//        if (fileType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document") ||
//                fileType.equals("application/pdf") ||
//                fileType.equals("application/msword")) {
//            // 要上传的目标文件存放的绝对路径
//            final String localPath = "static\\reportsTemp";
//            //上传后保存的文件名(需要防止图片重名导致的文件覆盖)
//            //获取文件名
//            String fileName = file.getOriginalFilename();
//            //获取文件后缀名
//            String suffixName = fileName.substring(fileName.lastIndexOf("."));
//            String result = UploadFileUtils.upload(file, localPath, fileName, false);
//            while (result.equals("exists")){
//                //重新生成文件名
//                fileName = UUID.randomUUID() + suffixName;
//                result = UploadFileUtils.upload(file, localPath, fileName, false);
//            }
//            if (result.equals("true")) {
//                //文件存放的相对路径(一般存放在数据库用于img标签的src)
//                String relativePath = "static/reportsTemp/" + fileName;
//                root.put("relativePath", relativePath);//前端根据是否存在该字段来判断上传是否成功
//                result_msg = "文件上传成功";
//            } else {
//                result_msg = "文件上传失败";
//            }
//        } else {
//            result_msg = "文件格式不正确";
//        }


//        root.put("result_msg", result_msg);
////        JSON.toJSONString(root,SerializerFeature.DisableCircularReferenceDetect);
//        String root_json = JSON.toJSONString(root);
//        logger.info(root_json);
//        return root;
//    }


    //上传数据集
    @ResponseBody
    @RequestMapping("/upload_dataset")
    public Map datasetUpload(MultipartFile file, HttpServletRequest request) {
        String result_msg = "";  //上传结果信息
        Map<String, Object> root = new HashMap<String, Object>();

        //判断上传文件格式
        String fileType = file.getContentType();
        //if (fileType.equals("text/plain") || fileType.equals("application/json")) {
        if (true) {
            // 要上传的目标文件存放的绝对路径
            final String localPath = "static\\dataset";
            //获取文件名
            String fileName = file.getOriginalFilename();
            //获取文件后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String result = UploadFileUtils.upload(file, localPath, fileName, false);
            while (result.equals("exists")){
                //重新生成文件名
                fileName = UUID.randomUUID() + suffixName;
                result = UploadFileUtils.upload(file, localPath, fileName, false);
            }
            if (result.equals("true")) {
                String relativePath = "static/dataset/" + fileName;
                root.put("relativePath", relativePath);//前端根据是否存在该字段来判断上传是否成功
                result_msg = "文件上传成功";
                root.put("fileFormat", suffixName);
                root.put("size", Math.round(file.getSize()/1024));
            } else {
                result_msg = "文件上传失败";
            }
        } else {
            result_msg = "文件格式不正确";
        }
        root.put("result_msg", result_msg);

        String root_json = JSON.toJSONString(root);
        logger.info(root_json);
        return root;
    }

    //上传视频
    @ResponseBody
    @RequestMapping("/upload_video")
    public Map videoUpload(MultipartFile file, HttpServletRequest request) {
        String result_msg = "";  //上传结果信息
        Map<String, Object> root = new HashMap<String, Object>();

        //判断上传文件格式
        String fileType = file.getContentType();
        if (fileType.equals("video/mp4") ||
                fileType.equals("video/mpeg4") ||
                fileType.equals("video/avi") ||
                fileType.equals("video/x-ms-wmv")) {
//        if (true) {
            // 要上传的目标文件存放的绝对路径
            final String localPath = "static\\video";
            //获取文件名
            String fileName = file.getOriginalFilename();
            //获取文件后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String result = UploadFileUtils.upload(file, localPath, fileName, false);
            while (result.equals("exists")){
                //重新生成文件名
                fileName = UUID.randomUUID() + suffixName;
                result = UploadFileUtils.upload(file, localPath, fileName, false);
            }
            if (result.equals("true")) {
                String relativePath = "static/video/" + fileName;
                root.put("relativePath", relativePath);//前端根据是否存在该字段来判断上传是否成功
                result_msg = "文件上传成功";
            } else {
                result_msg = "文件上传失败";
            }
        } else {
            result_msg = "文件格式不正确";
        }
        root.put("result_msg", result_msg);

        String root_json = JSON.toJSONString(root);
        logger.info(root_json);
        return root;
    }


}

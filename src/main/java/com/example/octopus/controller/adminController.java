package com.example.octopus.controller;

import com.alibaba.fastjson.JSON;
import com.example.octopus.entity.VOs.experiment.ReportAnalysisVO;
import com.example.octopus.entity.dataset.Dataset;
import com.example.octopus.entity.experiment.SubExperimentReportSubmit;
import com.example.octopus.entity.experiment.Video;
import com.example.octopus.entity.personal.PersonalUser;
import com.example.octopus.entity.user.*;
import com.example.octopus.service.*;
import com.example.octopus.service.personal.*;
import com.example.octopus.utils.CookieTokenUtils;
import com.example.octopus.utils.PropertiesUtil;
import com.example.octopus.utils.TokenCheckUtils;
import com.example.octopus.utils.UploadFileUtils;
import com.example.octopus.utils.parseExcelUtils;
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

    private PropertiesUtil propertiesUtil = new PropertiesUtil();
    private String WEB_BASE_PATH = propertiesUtil.getFileSavePath();
    private String WEB_HOST = "/static/";



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
    CourseStaticService courseStaticService;

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
    SubProjectService subProjectService;

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

    @Autowired
    PlanService planService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    PersonalUserService personalUserService;

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
            logger.info("管理员/教师{}进入admin_userinfo", teaNum);
            model.addAttribute("userinfo", teacherService.getTeacherByTeaNumber(teaNum));
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
            logger.info("管理员/教师{}修改个人资料: [{}]", teacher.getTeaNumber(), teacher);
            teacherService.updateTeacher(teacher);
            return new ModelAndView("redirect:/admin_index");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }


    //首页 -- 个人用户
    @RequestMapping("/admin_index_for_personal")
    public String admin_personal_index(HttpServletRequest request, Model model) {
        String teaNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!teaNumber.equals(cookieThings.getCookieUserNum(request, COOKIE_NAME))) return "redirect:/login";

        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        try{
            model.addAttribute("number_of_personal_user", 200);
            model.addAttribute("number_of_active_user", 200);
            model.addAttribute("number_of_plan", planService.listAllPlan().size());
            model.addAttribute("number_of_category", categoryService.listAllCategory().size());
            return "admin_index_for_personal";
        }catch (Exception e) {
            return "redirect:/admin_error";
        }
    }

    //用户管理 -- 个人用户
    @RequestMapping("/admin_personal_info")
    public String admin_personal_info(HttpServletRequest request, Model model) {
        String teaNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!teaNumber.equals(cookieThings.getCookieUserNum(request, COOKIE_NAME))) return "redirect:/login";

        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        try{
            return "admin_personal_info";
        }catch (Exception e) {
            return "redirect:/admin_error";
        }
    }

    // 增加个人用户
    @GetMapping("/admin_personal_add")
    public ModelAndView admin_personal_add(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师
        // 只有管理员能够增加用户
        if (role_id == 1) {
            logger.info("管理员{}进入admin_personal_add，获取一个新Personal()", teaNum);
            try {
                model.addAttribute("personal", new PersonalUser());
                return new ModelAndView("admin_personal_add", "permodel", model);
            }
            catch (Exception e){
                return new ModelAndView("redirect:/admin_error");
            }
        }
        else{
            return new ModelAndView("redirect:/login");
        }
    }

    @PostMapping("/add_personal")
    public ModelAndView add_personal(PersonalUser personalUser, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师
        // 只有管理员能够增加
        if (role_id == 1) {
            logger.info("管理员{}提交新增的个人用户: [{}]", teaNum, personalUser);
            try {
                personalUserService.insertPersonalUser(personalUser);
                return new ModelAndView("redirect:/admin_personal_info");
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
    @GetMapping("/admin_personal_edit")
    public ModelAndView admin_personal_edit(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        if (role_id == 1) {
            long personalTel = Long.parseLong(request.getParameter("personalTel"));
            logger.info("管理员{}进入admin_personal_edit，获取PersonalUser, personalTel={}", teaNum, personalTel);
            try {
                PersonalUser personalUser = personalUserService.getPersonalUser(personalTel);
                model.addAttribute("personalUser", personalUser);
                return new ModelAndView("admin_personal_edit", "permodel", model);
            }
            catch (Exception e){
                return new ModelAndView("redirect:/admin_error");
            }
        }
        else{
            return new ModelAndView("redirect:/login");
        }
    }

    @PostMapping("/edit_personal")
    public ModelAndView edit_personal(PersonalUser personalUser, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        if (role_id == 1) {
            logger.info("管理员{}提交修改的personal: [{}]", teaNum, personalUser);
            try {
                personalUserService.updatePersonalUser(personalUser);
                return new ModelAndView("redirect:/admin_personal_info");
            }
            catch (Exception e){
                return new ModelAndView("redirect:/admin_error");
            }
        }
        else{
            return new ModelAndView("redirect:/login");
        }
    }

    //套餐管理 -- 个人用户
    @RequestMapping("/admin_plan_for_personal")
    public String admin_plan_for_personal(HttpServletRequest request, Model model) {
        String teaNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!teaNumber.equals(cookieThings.getCookieUserNum(request, COOKIE_NAME))) return "redirect:/login";

        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        try{
            model.addAttribute("plans", planService.listAllPlan());
            return "admin_plan_for_personal";
        }catch (Exception e) {
            return "redirect:/admin_error";
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //首页
    @RequestMapping("/admin_index")
    public String confirmlogin(HttpServletRequest request, Model model) {
        String teaNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!teaNumber.equals(cookieThings.getCookieUserNum(request, COOKIE_NAME))) return "redirect:/login";

        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        logger.info("管理员/教师{}进入admin_index", teaNum);
        try {
            model.addAttribute("sizeof_experiments", subExperimentService.listSubExperimentByRole(teaNum).size());
            model.addAttribute("sizeof_projects", projectService.listProjects().size());
            model.addAttribute("sizeof_datasets", datasetService.listDatasets().size());
            model.addAttribute("sizeof_course_static", courseStaticService.listAllCourseStatic().size());
            if (role_id == 1){
                model.addAttribute("sizeof_courses", courseService.listCourses().size());
                model.addAttribute("sizeof_students", userService.listStudents().size());
            }
            else{
                model.addAttribute("sizeof_courses", courseService.listCoursesByTeaNumber(teaNum).size());
                model.addAttribute("sizeof_students", userService.listStudentsByTeaNumber(teaNum));
            }
            model.addAttribute("sizeof_videos", videoService.listVideos().size());
            model.addAttribute("sizeof_teachers", teacherService.getAllTeachers().size());
            model.addAttribute("sizeof_students", userService.listStudents().size());
            model.addAttribute("sizeof_dockers", dockerService.listDockerByRole(teaNum).size());
            model.addAttribute("sizeof_active_dockers", dockerService.listDockerByRoleAndAwake(teaNum, true).size());
            model.addAttribute("lastLoginTime", teacherService.getTeacherByTeaNumber(teaNum).getLastLoginTime());
            model.addAttribute("sumExperimentTime", teacherService.getSumExperimentTimeByRole(teaNum));
            model.addAttribute("sumVideoTime", teacherService.getSumVideoTimeByRole(teaNum));
            model.addAttribute("docker_status", dockerService.countDockerByStatus(teaNum));
//            logger.info("实验时间：{}", teacherService.getSumExperimentTimeByRole(teaNum));
//            logger.info("视频时间：{}", teacherService.getSumVideoTimeByRole(teaNum));
            return "admin_index";
        } catch (Exception e) {
            return "redirect:/admin_error";
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //专业管理
    @RequestMapping("/admin_major")
    public String admin_major(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        logger.info("管理员/教师{}进入专业管理admin_major", teaNum);
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
                logger.info("管理员/教师{}进入admin_major_add，获取一个新Major()", teaNum);
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
                logger.info("管理员/教师{}提交新增的major: [{}]", teaNum, major);
                majorService.insertMajor(major);
            }
            else{
                logger.info("管理员/教师{}没有新增专业权限！", teaNum);
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
                logger.info("管理员/教师{}进入admin_major_edit，获取指定编号的Major(),id={}", teaNum, id);
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
                logger.info("管理员/教师{}提交修改的major: [{}]", teaNum,  major);
                majorService.updateMajor(major);
            } else {
                logger.info("管理员/教师{}没有修改专业权限！", teaNum);
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
            logger.info("管理员/教师{}删除专业 id={}", teaNum, id);
            if (role_id == 1) {
                majorService.deleteById(id);
            } else {
                logger.info("管理员/教师{}没有删除专业权限！", teaNum);
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
            logger.info("管理员/教师{}进入班级管理admin_class", teaNum);
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
            logger.info("管理员/教师{}进入admin_major_add，获取一个新Class_()", teaNum);
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
            logger.info("管理员/教师{}提交新增的class_: [{}]", teaNum, class_);
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
        logger.info("管理员/教师{}进入admin_class_edit，获取指定id的Class_(), class.id=[{}]", teaNum, id);
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
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        logger.info("管理员/教师{}提交修改的class_: [{}]", teaNum, class_);
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
        logger.info("管理员/教师{}删除 class.id=[{}]", teaNum, id);
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

        logger.info("管理员/教师{}进入学生管理admin_student", teaNum);
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
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        logger.info("管理员/教师{}进入admin_student_add，获取一个新Student()", teaNum);

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
//        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师
        logger.info("管理员/教师{}提交新增的student: [{}]", teaNum , student);
        try {
            student.setPassword("123");
            userService.insertStudent(student);
            return new ModelAndView("redirect:/admin_student");
        }
        catch (Exception e){
//            return new ModelAndView("redirect:/admin_error");
            return new ModelAndView("redirect:/admin_student");
        }
    }

    //批量增加学生
    @ResponseBody
    @RequestMapping("/add_student_batch")
    public Map add_student_batch(MultipartFile file, HttpServletRequest request) {

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        logger.info("管理员/教师{}批量增加学生", teaNum);

        String result_msg = "";  //上传结果信息
        Map<String, Object> root = new HashMap<String, Object>();

        //判断上传文件格式
        String fileType = file.getContentType();
        //System.out.println(fileType);
        if (fileType.equals("application/vnd.ms-excel") ||
                fileType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            // 要上传的目标文件存放的绝对路径
            final String localPath = WEB_BASE_PATH + "student_batch/";
            //获取文件名
            String fileName = file.getOriginalFilename();
            //获取文件后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID() + suffixName;
            String result = UploadFileUtils.upload(file, localPath, fileName, false);
            while (result.equals("exists")){
                //重新生成文件名
                fileName = UUID.randomUUID() + suffixName;
                result = UploadFileUtils.upload(file, localPath, fileName, false);
            }
            if (result.equals("true")) {
                String relativePath = WEB_HOST + "dataset/" + fileName;
                root.put("success", "success"); //前端根据是否存在该字段来判断上传是否成功
                List<Student> stu_list = parseExcelUtils.parseExcel(localPath+fileName);
//                logger.info("解析文件，获得学生列表：{}", stu_list);
                try{
//                    for (int i=0; i<stu_list.size(); i++){
//                        userService.insertStudent(stu_list.get(i), teaNum);
//                    }
                    userService.batchInsertStudent(stu_list);
                    result_msg = "上传成功";
                }catch(Exception e){
                    result_msg = "上传失败";
                }

            } else {
                result_msg = "上传失败";
            }
        } else {
            result_msg = "格式不正确";
        }
        root.put("result_msg", result_msg);

        String root_json = JSON.toJSONString(root);
//        logger.info(root_json);
        return root;
    }

    //修改学生
    @GetMapping("/admin_student_edit")
    public ModelAndView admin_student_edit(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        long stuNumber = Long.parseLong(request.getParameter("stuNumber"));
        logger.info("管理员/教师{}进入admin_student_edit，获取指定名字的Student(),stuNumber=[{}]", teaNum, stuNumber);
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
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        logger.info("管理员/教师{}提交修改的student: [{}]", teaNum, student);
        try {
            userService.updateStudent(student);
            return new ModelAndView("redirect:/admin_student");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
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
    //教师管理
    @RequestMapping("/admin_teacher")
    public String admin_teacher(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师
        // 只有管理员能够进入教师管理
        if (role_id == 1) {
            logger.info("管理员{}进入教师管理admin_teacher", teaNum);
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
            logger.info("管理员{}进入admin_teacher_add，获取一个新Teacher()", teaNum);
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
            logger.info("管理员{}提交新增的teacher: [{}]", teaNum, teacher);
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
            logger.info("管理员{}admin_teacher_edit，获取指定名字的Teacher(),teaNumber={}", teaNum, teaNumber);
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
            logger.info("管理员{}提交修改的teacher: [{}]", teaNum, teacher);
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
    //可选课程展示
    @RequestMapping("/admin_course_static")
    public String admin_course_static(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
//        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        logger.info("管理员/教师{}进入可选课程展示admin_course_static", teaNum);
        try {
            model.addAttribute("courses", courseStaticService.listAllCourseStatic());
            return "admin_course_static";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }


    //开课计划管理
    @RequestMapping("/admin_course")
    public String admin_course(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        logger.info("管理员/教师{}进入开课计划管理admin_course", teaNum);
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

    // 增加开课计划
    @GetMapping("/admin_course_add")
    public ModelAndView admin_course_add(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, COOKIE_NAME);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        logger.info("管理员/教师{}进入admin_course_add，获取一个新Course()", teaNum);
        try {
            if (role_id == 1) {
                model.addAttribute("course", new Course());
            } else {
                // 教师账号只能给自己增加开课计划
                Course course = new Course();
                course.setTeaNumber(teaNum);
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
//        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        logger.info("管理员/教师{}提交新增的course: [{}]", teaNum, course);
        try {
            courseService.insertCourse(course);
            return new ModelAndView("redirect:/admin_course");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    //修改开课计划
    @GetMapping("/admin_course_edit")
    public ModelAndView admin_course_edit(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        long courseId = Long.parseLong(request.getParameter("courseId"));
        logger.info("管理员/教师{}进入admin_course_edit，获取指定名字的Course(), courseId={}", teaNum, courseId);
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
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        logger.info("管理员/教师{}提交修改的course: [{}]", teaNum, course);
        try {
//            if (course.getTeaName() == null) {
//                // 如果只有账号没有姓名 则补充姓名
//                course.setTeaName(teacherService.getTeacherByTeaNumber(course.getTeaNumber()).getTeaName());
//            }
            courseService.updateCourse(course);
            return new ModelAndView("redirect:/admin_course");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    //删除开课计划
    @RequestMapping("/admin_course_delete")
    public ModelAndView admin_course_delete(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        long courseId = Long.parseLong(request.getParameter("courseId"));
        logger.info("管理员/教师{}删除开课计划 courseId={}", teaNum, courseId);
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

        logger.info("管理员/教师{}进入学生开课计划管理admin_course_student", teaNum);

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
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        logger.info("管理员/教师{}进入admin_course_student_add，获取一个新的Student_Course()", teaNum);
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
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
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

        logger.info("管理员/教师{}提交新增的course_student: [{}]", teaNum, course_student);
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
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        long id = Long.parseLong(request.getParameter("id"));
        logger.info("管理员/教师{}进入admin_course_student_edit，获取指定名字的Student_Course(),id={}", teaNum, id);
        try {
            model.addAttribute("course_student", studentcourseService.getById(id));

            return new ModelAndView("admin_course_student_edit", "coursestudentmodel", model);
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    @PostMapping("/edit_course_student")
    public ModelAndView edit_course_student(HttpServletRequest request, StudentCourse course_student, Model model){
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
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

        logger.info("管理员/教师{}提交修改的course_student: [{}]", teaNum ,course_student);
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
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        long id = Long.parseLong(request.getParameter("id"));
        logger.info("管理员/教师{}删除学生开课计划 id={}", teaNum, id);
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
        logger.info("管理员/教师{}进入admin_video_log", teaNum);
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
        logger.info("管理员/教师{}进入视频学习详情admin_video_log_details", teaNum);
        try {
            model.addAttribute("video_log_details", videoProgressService.getVideoProgressDetailByRole(teaNum));
            return "admin_video_log_details";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    // 实验报告管理——汇总（按照 实验）
    @RequestMapping("/admin_report")
    public String admin_report(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));

        logger.info("管理员/教师{}进入实验报告管理--汇总admin_report", teaNum);
        try {
            model.addAttribute("reports", subExperimentReportSubmitService.listReportSummaryByRole(teaNum));
            return "admin_report";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }

    // 实验报告管理——汇总(按照 实验-班级)
    @RequestMapping("/admin_report_by_class")
    public String admin_report_by_class(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));

        long id = Long.parseLong(request.getParameter("id"));
        logger.info("管理员/教师{}进入实验-班级报告汇总admin_report_by_class，实验id={}", teaNum, id);

        try {
            model.addAttribute("reports", subExperimentReportSubmitService.listClassReportSummaryByRoleAndSubExpId(teaNum, id));
            model.addAttribute("sub_exp_name", subExperimentService.getById(id).getSubExperimentName());
            model.addAttribute("experimentName", experimentService.getExperimentById(subExperimentService.getById(id).getExperimentId()).getName());

            return "admin_report_by_class";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }

    // 实验报告成绩下载——某一实验
//    @GetMapping("/admin_report_score")
//    public ModelAndView admin_report_score(HttpServletRequest request, Model model) {
//        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
//        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
//
//        try {
//            long id = Long.parseLong(request.getParameter("id"));
//            model.addAttribute("reports", subExperimentReportSubmitService.listReportScoreByRoleAndSubExpId(teaNum, id));
//            model.addAttribute("sub_exp_name", subExperimentService.getById(id).getSubExperimentName());
//            model.addAttribute("experimentName", experimentService.getExperimentById(subExperimentService.getById(id).getExperimentId()).getName());
//            return new ModelAndView("admin_report_score");
//        }
//        catch (Exception e){
//            return new ModelAndView("redirect:/admin_error");
//        }
//    }

    // 实验报告分析——某个实验的成绩分析
    @GetMapping("/admin_report_analysis")
    public ModelAndView admin_report_analysis(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));

        long subExperimentId = Long.parseLong(request.getParameter("subExperimentId"));
        logger.info("管理员/教师{}进入实验报告分析admin_report_analysis，实验id={}", teaNum ,subExperimentId);

        try {
            if(request.getParameter("classId").equals("none")){
                // 该实验的报告成绩分析
                List<ReportAnalysisVO> reportAnalysis = subExperimentReportSubmitService.listReportAnalysisByRoleAndSubExpId(teaNum, subExperimentId);
                // 该实验的某个班级各分数段人数统计
                model.addAttribute("analysis_list", reportAnalysis);
                model.addAttribute("class", "all");
                int[] analysis_total = new int[]{0,0,0,0,0,0};
                for(int i=0; i<reportAnalysis.size(); i++){
                    for(int j=0; j<=5; j++){
                        analysis_total[j] += reportAnalysis.get(i).getScores()[j];
                    }
                }
                // 该实验的各分数段所有人数统计
                model.addAttribute("analysis", analysis_total);
            }
            else{
                // 该实验的某个班级的报告成绩分析
                Long classId = Long.parseLong(request.getParameter("classId"));
                model.addAttribute("analysis", subExperimentReportSubmitService.getReportAnalysisByRoleAndSubExpIdAndClassId(teaNum, subExperimentId, classId).getScores());
                model.addAttribute("class", "class");
                model.addAttribute("className", classService.getClass_Byid(classId).getClassName());
                model.addAttribute("sub_exp_id", subExperimentId);
            }
            model.addAttribute("sub_exp_name", subExperimentService.getById(subExperimentId).getSubExperimentName());
            model.addAttribute("experimentName", experimentService.getExperimentById(subExperimentService.getById(subExperimentId).getExperimentId()).getName());
            return new ModelAndView("admin_report_analysis");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    // 实验报告管理——某个实验某一班级的报告列表
    @GetMapping("/admin_report_list")
    public ModelAndView admin_report_list(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));

        long classId = Long.parseLong(request.getParameter("classId"));
        long subExperimentId = Long.parseLong(request.getParameter("subExperimentId"));
        logger.info("管理员/教师{}进入实验-班级报告列表admin_report_list，实验id={}，班级id={}", teaNum, subExperimentId, classId);

        try {
            model.addAttribute("reports", subExperimentReportSubmitService.listClassReportByRoleAndSubExpIdAndClassId(teaNum, subExperimentId, classId));
            model.addAttribute("sub_exp_name", subExperimentService.getById(subExperimentId).getSubExperimentName());
            model.addAttribute("sub_exp_id", subExperimentId);
            model.addAttribute("experimentName", experimentService.getExperimentById(subExperimentService.getById(subExperimentId).getExperimentId()).getName());
            model.addAttribute("className", classService.getClass_Byid(classId).getClassName());
            return new ModelAndView("admin_report_list");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    // 实验报告成绩下载——某一实验、某一班级
    @GetMapping("/admin_report_score")
    public ModelAndView admin_report_score(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));

        long classId = Long.parseLong(request.getParameter("classId"));
        long subExperimentId = Long.parseLong(request.getParameter("subExperimentId"));
        logger.info("管理员/教师{}进入实验-班级报告成绩下载admin_report_score，实验id={}，班级id={}", teaNum, subExperimentId, classId);

        try {
            model.addAttribute("reports", subExperimentReportSubmitService.listClassReportByRoleAndSubExpIdAndClassId(teaNum, subExperimentId, classId));
            model.addAttribute("sub_exp_name", subExperimentService.getById(subExperimentId).getSubExperimentName());
            model.addAttribute("sub_exp_id", subExperimentId);
            model.addAttribute("experimentName", experimentService.getExperimentById(subExperimentService.getById(subExperimentId).getExperimentId()).getName());
            model.addAttribute("className", classService.getClass_Byid(classId).getClassName());
            return new ModelAndView("admin_report_score");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    //实验报告详情——具体某个报告批阅
    @RequestMapping("/admin_report_detail")
    public String admin_report_detail(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));

        long id = Long.parseLong(request.getParameter("id"));
        logger.info("管理员/教师{}进入实验报告预览admin_report_detail，id={}", teaNum, id);

        try {
            SubExperimentReportSubmit expSub = subExperimentReportSubmitService.getById(id);
            model.addAttribute("expSub", expSub);
            model.addAttribute("experimentName", experimentService.getExperimentById(subExperimentService.getById(expSub.getSubExperimentId()).getExperimentId()).getName());

            // 获取下一个报告的id
            SubExperimentReportSubmit next = subExperimentReportSubmitService.getNextReportByTeaIdAndSubExpIdAndClassId(
                    id, teaNum, expSub.getSubExperimentId(),expSub.getClassId());
            if(next != null){
                model.addAttribute("next", next.getId());
            }
            else{
                model.addAttribute("next", null);
            }
            return "admin_report_detail";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }

    // 提交报告分数
    @ResponseBody
    @RequestMapping("/report_score")
    public Map report_score(HttpServletRequest request) {
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        Map<String, Object> result = new HashMap<String, Object>();

        long subExperimentId = Long.parseLong(request.getParameter("subExperimentId"));
        long stuNumber = Long.parseLong(request.getParameter("stuNumber"));
        int score = Integer.parseInt(request.getParameter("score"));
        try{
            logger.info("管理员/教师{}提交报告分数: subExperimentId={}, stuNumber={}, teaNumber={}, score={}",
                    teaNum, subExperimentId, stuNumber, teaNum, score);
            subExperimentReportSubmitService.updateByExamine(subExperimentId, stuNumber, teaNum, score);
        }catch (Exception e){
            result.put("error", "提交报告分数失败！");
        }
        return result;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    //实验操作时长 仅展示
    @RequestMapping("/admin_experiment_log")
    public String admin_experiment_log(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));

        logger.info("管理员/教师{}进入实验操作时长admin_experiment_log", teaNum);
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
//        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，

        logger.info("管理员/教师{}进入实验详情admin_experiment_detail", teaNum);
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

        logger.info("管理员/教师{}进入实验机类型admin_pc_type", teaNum);

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

        logger.info("管理员/教师{}进入实验机集群管理admin_cluster", teaNum);

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

        logger.info("管理员/教师{}进入学生实验机管理admin_student_pc", teaNum);
        try {
            // todo 教师账号dockerService.listDockerByRole(teaNum)返回的信息有误
            model.addAttribute("dockers", dockerService.listDockerByRole(teaNum));
            //logger.info("docker:{}", dockerService.listDockerByRole(teaNum));
            return "admin_student_pc";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    // 实战项目管理
    @RequestMapping("/admin_project")
    public String admin_project(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
//        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        logger.info("管理员/教师{}进入实战项目管理admin_project", teaNum);

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
//        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);
        logger.info("管理员/教师{}进入实战项目详情admin_project_detail", teaNum);

        try{
//            System.out.println();
            model.addAttribute("subprojects", subProjectService.listSubProjectDetail());
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
//        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        logger.info("管理员/教师{}进入视频管理admin_video", teaNum);

        try {
            model.addAttribute("video", videoService.getVideoManageInfoByRole(teaNum));
            return "admin_video";
        }
        catch (Exception e){
            return "redirect:/admin_error";
        }
    }

    //视频增加
//    @GetMapping("/admin_video_add")
//    public ModelAndView admin_video_add(HttpServletRequest request, Model model) {
//        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
//        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
//        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师
//
//        logger.info("管理员/教师{}进入admin_video_add，获取一个新Video()", teaNum);
//        try {
//            if (role_id == 1) {
//                model.addAttribute("video", new Video());
//            } else {
//                return new ModelAndView("redirect:/admin_video");
//            }
//            return new ModelAndView("admin_video_add", "videomodel", model);
//        }
//        catch (Exception e){
//            return new ModelAndView("redirect:/admin_error");
//        }
//    }
//
//    @PostMapping("/add_video")
//    public ModelAndView add_video(Video video, Model model, HttpServletRequest request) {
//        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
//        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
//        logger.info("管理员/教师{}提交新增的dataset: [{}]", teaNum, video);
//        try {
//            videoService.addVideo(video);
//            return new ModelAndView("redirect:/admin_video");
//        }
//        catch (Exception e){
//            return new ModelAndView("redirect:/admin_error");
//        }
//    }
//
//    //修改视频
//    @GetMapping("/admin_video_edit")
//    public ModelAndView admin_video_edit(HttpServletRequest request, Model model){
//        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
//        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
//        long id = Long.parseLong(request.getParameter("id"));
//        logger.info("管理员/教师{}进入admin_video_edit，获取指定id的Video(),id={}", teaNum, id);
//
//        try {
//            model.addAttribute("video", videoService.getById(id));
//            return new ModelAndView("admin_video_edit", "videomodel", model);
//        }
//        catch (Exception e){
//            return new ModelAndView("redirect:/admin_error");
//        }
//    }
//
//    @PostMapping("/edit_video")
//    public ModelAndView edit_video(Video video, Model model, HttpServletRequest request){
//        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
//        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
//        logger.info("管理员/教师{}提交修改的video: [{}]", teaNum, video);
//        try {
//            videoService.updateVideo(video);
//            return new ModelAndView("redirect:/admin_video");
//        }catch (Exception e){
//            return new ModelAndView("redirect:/admin_error");
//        }
//    }
//
//    //删除视频
//    @RequestMapping("/admin_video_delete")
//    public ModelAndView admin_video_delete(HttpServletRequest request, Model model) {
//        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
//        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
//        long id = Long.parseLong(request.getParameter("id"));
//        logger.info("管理员/教师{}删除 video.id={}", teaNum, id);
//        try {
//            videoService.deleteVideoById(id);
//            return new ModelAndView("redirect:/admin_video");
//        }
//        catch (Exception e){
//            return new ModelAndView("redirect:/admin_error");
//        }
//    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // 视频分类管理
//    @RequestMapping("/admin_video_class")
//    public String admin_video_class(HttpServletRequest request, Model model) {
//        if (!cookieCheck(model, request)) return "redirect:/login";
//
//        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
//        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师
//
//        logger.info("进入实战项目管理");
//
//        try {
//            if (role_id == 1) {
//
//                //model.addAttribute("video", videoService.listVideos());
//            } else {
//
//                //model.addAttribute("video", videoService.listVideos());
//            }
//            return "admin_video_class";
//        }
//        catch (Exception e){
//            return "redirect:/admin_error";
//        }
//    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    //数据集管理
    @RequestMapping("/admin_dataset")
    public String admin_dataset(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        logger.info("管理员/教师{}进入数据集管理", teaNum);
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
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        logger.info("管理员/教师{}进入admin_dataset_add，获取一个新的Dataset()", teaNum);
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
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        logger.info("管理员/教师{}提交新增的dataset: [{}]", teaNum, dataset);
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
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        long id = Long.parseLong(request.getParameter("id"));
        logger.info("管理员/教师{}进入admin_dataset_edit，获取指定id的Dataset(),id={}", teaNum, id);
        try {
            model.addAttribute("dataset", datasetService.getDatasetById(id));
            return new ModelAndView("admin_dataset_edit", "datasetmodel", model);
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }

    @PostMapping("/edit_dataset")
    public ModelAndView edit_dataset(HttpServletRequest request, Dataset dataset, Model model){
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        logger.info("管理员/教师{}提交修改的dataset: [{}]", teaNum, dataset);
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
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        long id = Long.parseLong(request.getParameter("id"));
        logger.info("管理员/教师{}删除 dataset.id={}", teaNum, id);
        try {
            datasetService.deleteDataset(id);
            return new ModelAndView("redirect:/admin_dataset");
        }
        catch (Exception e){
            return new ModelAndView("redirect:/admin_error");
        }
    }






    //////////////////////////////////////////////////////////////////////////////////////////////
    // 其他GET/POST接口
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
//        logger.info("获取专业id=[{}]下的班级", majorId);
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

    // 获取所有的课程
    @ResponseBody
    @RequestMapping("/get_course")
    public Map get_course(HttpServletRequest request) {
//        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
        //int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师
        Map<String, Object> course = new HashMap<String, Object>();
        // 返回所有静态课程
        course.put("courses", courseStaticService.listAllCourseStatic());
//        logger.info("{}", course);
        return course;
    }

    // 下载数据集，下载次数+1
    @ResponseBody
    @RequestMapping("/download_dataset")
    public String download_dataset(HttpServletRequest request) {

        Long id = Long.parseLong(request.getParameter("id"));
//        logger.info("数据集下载，id=[{}]", id);
        datasetService.increaseDownloadNum(id);
        return "";
    }



}

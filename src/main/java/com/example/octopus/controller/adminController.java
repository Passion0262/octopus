package com.example.octopus.controller;

import com.alibaba.fastjson.JSON;
import com.example.octopus.entity.dataset.Dataset;
import com.example.octopus.entity.user.*;
import com.example.octopus.service.*;
import com.example.octopus.utils.CookieTokenUtils;
import com.example.octopus.utils.TokenCheckUtils;
import com.example.octopus.utils.UploadFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    DockerService dockerService;

    private final static String cookieName = "cookietea";

    private CookieTokenUtils cookieThings = new CookieTokenUtils();


    private boolean cookieCheck(Model model, HttpServletRequest request) {
        // 检查cookie合法性
        TokenCheckUtils tokenCheck = cookieThings.validateToken(request, cookieName);
        if (tokenCheck.isSuccess()) {
            long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
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


    //登录
    @RequestMapping("/admin_login")
    public String admin_login() {
        return "redirect:/login";
    }


    //个人资料
    @RequestMapping("/admin_userinfo")
    public String admin_userinfo(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        model.addAttribute("userinfo", teacherService.getTeacherByTeaNumber(teaNum));
        //System.out.println(teacherService.getTeacherByTeaNumber(teaNum));
        return "admin_userinfo";
    }

    @PostMapping("/edit_userinfo")
    public ModelAndView edit_userinfo(Teacher teacher, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        System.out.println(teacher);
        teacherService.updateTeacher(teacher);
        return new ModelAndView("redirect:/admin_userinfo");
    }


    //首页
    @RequestMapping("/admin_index")
    public String confirmlogin(HttpServletRequest request, Model model) {
//        String teaNumber = SecurityContextHolder.getContext().getAuthentication().getName();
//        if (!teaNumber.equals(cookieThings.getCookieUserNum(request, cookieName))) return "redirect:/";

        if (!cookieCheck(model, request)) return "redirect:/login";

        // 获取用户名及用户id的方法使用如下语句
        //String teaName = cookieThings.getCookieUserName(request, cookieName);
        //long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));

        try {
            model.addAttribute("sizeof_experiments", experimentService.listExperiments().size());
            model.addAttribute("sizeof_projects", experimentService.listExperiments().size());
            model.addAttribute("sizeof_datasets", datasetService.listDatasets().size());
            return "admin_index";
        } catch (Exception e) {
            return "";
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //专业管理
    @RequestMapping("/admin_major")
    public String admin_major(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        logger.info("进入专业管理");

        if (role_id == 1) {
            model.addAttribute("majors", majorService.listMajors());
        }
        else {
            model.addAttribute("majors", majorService.getByTeaNumber(teaNum));
        }
        return "admin_major";
    }

    //增加专业
    @GetMapping("/admin_major_add")
    public ModelAndView admin_major_add(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  //获取角色，管理员还是教师

        // 只有管理员可以增加专业
        if (role_id == 1) {
            logger.info("进入admin_major_add，获取一个新Major()");
            model.addAttribute("major", new Major());
        }

        return new ModelAndView("admin_major_add", "majormodel", model);
    }

    @PostMapping("/add_major")
    public ModelAndView add_major(Major major, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        if (role_id == 1){
            logger.info("提交新增的major: [{}]", major);
            majorService.insertMajor(major);
        }
        else{
            logger.info("[{}]没有新增专业权限！", teaName);
        }
        return new ModelAndView("redirect:/admin_major");
    }

    //修改专业
    @GetMapping("/admin_major_edit")
    public ModelAndView admin_major_edit(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        // 只有管理员可以修改专业
        if (role_id == 1) {
            long id = Long.parseLong(request.getParameter("id"));
            logger.info("进入admin_major_edit，获取指定编号的Major(),id=[{}]", id);
            model.addAttribute("major", majorService.getById(id));
        }
        return new ModelAndView("admin_major_edit", "majormodel", model);
    }

    @PostMapping("/edit_major")
    public ModelAndView edit_major(Major major, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        if (role_id == 1){
            logger.info("提交修改的major: [{}]", major);
            majorService.updateMajor(major);
        }
        else{
            logger.info("[{}]没有修改专业权限！", teaName);
        }
        return new ModelAndView("redirect:/admin_major");
    }

    //删除专业
    @RequestMapping("/admin_major_delete")
    public ModelAndView admin_major_delete(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        // todo 删除专业涉及到相关班级、学生、教师
        long id = Long.parseLong(request.getParameter("id"));
        logger.info("删除专业 id=[{}]", id);
        if (role_id == 1){
            majorService.deleteById(id);
        }
        else{
            logger.info("[{}]没有删除专业权限！", teaName);
        }
        return new ModelAndView("redirect:/admin_major");
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //班级管理
    @RequestMapping("/admin_class")
    public String admin_class(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        if (role_id == 1) {
            logger.info("进入班级管理");
            model.addAttribute("classes", classService.listClass_s());
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
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        if (role_id == 1) {
            logger.info("进入admin_major_add，获取一个新Class_()");
            model.addAttribute("class", new Class_());
        }
        return new ModelAndView("admin_class_add", "classmodel", model);
    }

    @PostMapping("/add_class")
    public ModelAndView add_class(Class_ class_, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));

        logger.info("提交新增的class_: [{}]", class_);
        classService.insertClass(class_, teaNum);

        return new ModelAndView("redirect:/admin_class");
    }

    //修改班级
    @GetMapping("/admin_class_edit")
    public ModelAndView admin_class_edit(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        long id = Long.parseLong(request.getParameter("id"));
        logger.info("admin_class_edit，获取指定id的Class_(), class.id=[{}]", id);

        model.addAttribute("class", classService.getClass_Byid(id));

        return new ModelAndView("admin_class_edit", "classmodel", model);
    }

    @PostMapping("/edit_class")
    public ModelAndView edit_class(Class_ class_, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        logger.info("提交修改的class_: [{}]", class_);
        classService.updateClass(class_);
        return new ModelAndView("redirect:/admin_class");
    }

    //删除班级
    @RequestMapping("/admin_class_delete")
    public ModelAndView admin_class_delete(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        long id = Long.parseLong(request.getParameter("id"));
        logger.info("删除 class.id=[{}]", id);
        // todo 删除班级涉及到相关学生
        classService.deleteByClassId(id);

        return new ModelAndView("redirect:/admin_class");
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    //学生管理
    @RequestMapping("/admin_student")
    public String admin_student(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        logger.info("进入学生管理");
        if (role_id == 1) {
            model.addAttribute("students", userService.listStudents());
        }
        else{
            model.addAttribute("students", userService.listStudentsByTeaNumber(teaNum));
        }

        return "admin_student";
    }

    //增加学生
    @GetMapping("/admin_student_add")
    public ModelAndView admin_student_add(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        logger.info("进入admin_student_add，获取一个新Student()");

        model.addAttribute("student", new Student());
        return new ModelAndView("admin_student_add", "stumodel", model);
    }

    @PostMapping("/add_student")
    public ModelAndView add_student(Student student, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        logger.info("提交新增的student: [{}]", student);
        userService.insertStudent(student, teaNum);
        return new ModelAndView("redirect:/admin_student");
    }

    //修改学生
    @GetMapping("/admin_student_edit")
    public ModelAndView admin_student_edit(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long stuNumber = Long.parseLong(request.getParameter("stuNumber"));
        logger.info("admin_student_edit，获取指定名字的Student(),stuNumber=[{}]", stuNumber);
        Student student = userService.getStudentByStuNumber(stuNumber);
        model.addAttribute("student", student);

        return new ModelAndView("admin_student_edit", "stumodel", model);
    }

    @PostMapping("/edit_student")
    public ModelAndView edit_student(Student student, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        logger.info("提交修改的student: [{}]", student);
        userService.updateStudent(student);

        return new ModelAndView("redirect:/admin_student");
    }

    //删除学生
    @RequestMapping("/admin_student_delete")
    public ModelAndView admin_student_delete(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long stuNumber = Long.parseLong(request.getParameter("stuNumber"));
        logger.info("删除 stuNumber=[{}]", stuNumber);

        return new ModelAndView("redirect:/admin_student");
    }

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
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师
        // 只有管理员能够进入教师管理
        if (role_id == 1) {
            logger.info("进入教师管理");
            model.addAttribute("teachers", teacherService.getAllTeachers());
            //System.out.println(teacherService.getAllTeachers());
        }
        else{
            return "redirect:/login";
        }
        return "admin_teacher";
    }

    //增加教师
    @GetMapping("/admin_teacher_add")
    public ModelAndView admin_teacher_add(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师
        // 只有管理员能够增加教师
        if (role_id == 1) {
            logger.info("进入admin_teacher_add，获取一个新Teacher()");
            model.addAttribute("teacher", new Teacher());
        }
        else{
            new ModelAndView("redirect:/login");
        }
        return new ModelAndView("admin_teacher_add", "teamodel", model);
    }

    @PostMapping("/add_teacher")
    public ModelAndView add_teacher(Teacher teacher, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师
        // 只有管理员能够增加教师
        if (role_id == 1) {
            logger.info("提交新增的teacher: [{}]", teacher);
            teacherService.addTeacher(teacher);
        }
        return new ModelAndView("redirect:/admin_teacher");
    }

    //修改教师
    @GetMapping("/admin_teacher_edit")
    public ModelAndView admin_teacher_edit(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        if (role_id == 1) {
            long teaNumber = Long.parseLong(request.getParameter("teaNumber"));
            logger.info("admin_teacher_edit，获取指定名字的Teacher(),teaNumber=[{}]", teaNumber);
            Teacher teacher = teacherService.getTeacherByTeaNumber(teaNumber);
            model.addAttribute("teacher", teacher);
        }
        else{
            new ModelAndView("redirect:/login");
        }

        return new ModelAndView("admin_teacher_edit", "teamodel", model);
    }

    @PostMapping("/edit_teacher")
    public ModelAndView edit_teacher(Teacher teacher, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        if (role_id == 1) {
            logger.info("提交修改的teacher: [{}]", teacher);
            teacherService.updateTeacher(teacher);
        }
        return new ModelAndView("redirect:/admin_teacher");
    }

    //删除教师
    @RequestMapping("/admin_teacher_delete")
    public ModelAndView admin_teacher_delete(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long teaNum = Long.parseLong(request.getParameter("teaNumber"));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        if (role_id == 1) {
            logger.info("删除 teaNumber=[{}]", teaNum);
            teacherService.deleteTeacher(teaNum);
        }
        return new ModelAndView("redirect:/admin_teacher");
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    //开课计划管理
    @RequestMapping("/admin_course")
    public String admin_course(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        logger.info("进入开课计划管理");
        if(role_id == 1){
            model.addAttribute("courses", courseService.listCourses());
        }
        else{
            model.addAttribute("courses", courseService.listCoursesByTeaNumber(teaNum));
        }
        return "admin_course";
    }

    //课程增加
    @GetMapping("/admin_course_add")
    public ModelAndView admin_course_add(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        String teaName = cookieThings.getCookieUserName(request, cookieName);
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        logger.info("进入admin_course_add，获取一个新Course()");
        if (role_id == 1){
            model.addAttribute("course", new Course());
        }
        else{
            // 教师账号只能给自己增加开课计划
            Course course = new Course();
            course.setTeaNumber(teaNum);
            course.setTeaName(teaName);
            model.addAttribute("course", course);
        }

        return new ModelAndView("admin_course_add", "coursemodel", model);
    }

    @PostMapping("/add_course")
    public ModelAndView add_course(Course course, HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        logger.info("提交新增的course: [{}]", course);
        if (course.getTeaName() == null){
            // 如果只有账号没有姓名 则补充姓名
            course.setTeaName(teacherService.getTeacherByTeaNumber(course.getTeaNumber()).getTeaName());
        }
        courseService.insertCourse(course);
        return new ModelAndView("redirect:/admin_course");
    }

    //修改课程
    @GetMapping("/admin_course_edit")
    public ModelAndView admin_course_edit(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        long courseId = Long.parseLong(request.getParameter("courseId"));
        logger.info("进入admin_course_edit，获取指定名字的Course(), courseId=" + courseId);
        model.addAttribute("course", courseService.getCourseById(courseId));
        return new ModelAndView("admin_course_edit", "coursemodel", model);
    }

    @PostMapping("/edit_course")
    public ModelAndView edit_course(Course course, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        logger.info("提交修改的course: [{}]", course);
        if (course.getTeaName() == null){
            // 如果只有账号没有姓名 则补充姓名
            course.setTeaName(teacherService.getTeacherByTeaNumber(course.getTeaNumber()).getTeaName());
        }
        courseService.updateCourse(course);
        return new ModelAndView("redirect:/admin_course");
    }

    //删除课程
    @RequestMapping("/admin_course_delete")
    public ModelAndView admin_course_delete(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        long courseId = Long.parseLong(request.getParameter("courseId"));
        logger.info("删除 courseId=" + courseId);
        courseService.deleteCourseById(courseId);
        return new ModelAndView("redirect:/admin_course");
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    //学生开课计划管理
    @RequestMapping("/admin_course_student")
    public String admin_course_student(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        logger.info("进入学生开课计划管理");
        if(role_id == 1){
            model.addAttribute("course_students", studentcourseService.listStudentCourses());
        }
        else{
            // todo 目前无法返回list
            model.addAttribute("course_students", studentcourseService.listStudentCoursesByTeaNumber(teaNum));
        }
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
        //studentcourseService.insertStudentCourse(course_student);
        studentcourseService.insertStudentCourse(course_student.getStuNumber(), course_student.getCourseId());
        return new ModelAndView("redirect:/admin_course_student");
    }

    //修改学生开课计划
    @GetMapping("/admin_course_student_edit")
    public ModelAndView admin_course_student_edit(HttpServletRequest request, Model model){
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        long id = Long.parseLong(request.getParameter("id"));
        logger.info("进入admin_course_student_edit，获取指定名字的Student_Course(),id=[{}]", id);
        model.addAttribute("course_student",studentcourseService.getById(id));

        return new ModelAndView("admin_course_student_edit","coursestudentmodel",model);
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
        studentcourseService.insertStudentCourse(course_student.getStuNumber(), course_student.getCourseId());
        return new ModelAndView("redirect:/admin_course_student");
    }

    //删除学生开课计划
    @RequestMapping("/admin_course_student_delete")
    public ModelAndView admin_course_student_delete(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        long id = Long.parseLong(request.getParameter("id"));
        logger.info("删除 id=[{}]", id);
        studentcourseService.deleteStudentCourse(id);
        return new ModelAndView("redirect:/admin_course_student");
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    //视频学习汇总 仅展示
    @RequestMapping("/admin_video_log")
    public String admin_video_log(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        model.addAttribute("video_logs", videoProgressService.getVideoStudySummaryByRole(teaNum));
        return "admin_video_log";
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    //视频学习详情 仅展示
    @RequestMapping("/admin_video_log_details")
    public String admin_video_log_details(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        if(role_id == 1){
            //model.addAttribute("video_log_details", videoService.listVideoLogDetails());
        }
        else{
            //model.addAttribute("video_log_details", videoService.listVideoLogDetailsByTeaNumber(teaNum));
        }
        return "admin_video_log_details";
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    // 实验报告管理
    @RequestMapping("/admin_report")
    public String admin_report(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        if(role_id == 1){
            // todo 展示所有报告
        }
        else{
            // todo 根据老师展示报告
        }
        return "admin_report";
    }

    //实验报告详情
    @RequestMapping("/admin_report_detail")
    public String admin_report_detail(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        // todo 根据报告id返回报告
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


    //////////////////////////////////////////////////////////////////////////////////////////////
    //实验操作时长 仅展示
    @RequestMapping("/admin_experiment_log")
    public String admin_experiment_log(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        model.addAttribute("experiment_logs", subExperimentProgressService.getOperateTimeByRole(teaNum));
        return "admin_experiment_log";
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // 实验机类型管理
    @RequestMapping("/admin_pc_type")
    public String admin_pc_type(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        if(role_id == 1){

            //model.addAttribute("pc_types", );
        }
        else{

            //model.addAttribute("pc_types", );
        }
        return "admin_pc_type";
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    // 实验机集群管理
    @RequestMapping("/admin_cluster")
    public String admin_cluster(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        if(role_id == 1){
            //model.addAttribute("clusters", );
        }
        else{
            //model.addAttribute("clusters", );
        }
        return "admin_cluster";
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    // 学生实验机管理
    @RequestMapping("/admin_student_pc")
    public String admin_student_pc(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师
        if(role_id == 1){

            //model.addAttribute("student_pc", );
        }
        else{

            //model.addAttribute("student_pc", );
        }
        return "admin_student_pc";
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
    // 视频管理
    @RequestMapping("/admin_video")
    public String admin_video(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        if(role_id == 1){
            model.addAttribute("video", videoService.listVideos());
        }
        else{
            model.addAttribute("video", videoService.listVideos());
        }
        return "admin_video";
    }

    //////////////////////////////////////////////////////////////////////////////////////////////
    // 视频分类管理
    @RequestMapping("/admin_video_class")
    public String admin_video_class(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";

        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
        int role_id = sysUserRoleService.getRoleIdByUserId(teaNum);  // 获取角色，管理员还是教师

        if(role_id == 1){

            //model.addAttribute("video", videoService.listVideos());
        }
        else{

            //model.addAttribute("video", videoService.listVideos());
        }
        return "admin_video_class";
    }


    //////////////////////////////////////////////////////////////////////////////////////////////
    //数据集管理
    @RequestMapping("/admin_dataset")
    public String admin_dataset(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return "redirect:/login";
        model.addAttribute("datasets", datasetService.listDatasets());
        return "admin_dataset";
    }

    //数据集增加
    @GetMapping("/admin_dataset_add")
    public ModelAndView admin_dataset_add(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");
        logger.info("进入admin_dataset_add，获取一个新的Dataset()");
        model.addAttribute("dataset", new Dataset());
        return new ModelAndView("admin_dataset_add", "datasetmodel", model);
    }

    @PostMapping("/add_dataset")
    public ModelAndView add_dataset(Dataset dataset, Model model, HttpServletRequest request) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        logger.info("提交新增的dataset: [{}]", dataset);
        // todo 向数据库提交新增的dataset
        return new ModelAndView("redirect:/admin_dataset");
    }

    //修改数据集
    @GetMapping("/admin_dataset_edit")
    public ModelAndView admin_dataset_edit(HttpServletRequest request, Model model){
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        long id = Long.parseLong(request.getParameter("id"));
        logger.info("进入admin_dataset_edit，获取指定id的Dataset(),id=[{}]", id);

        model.addAttribute("dataset", datasetService.getDatasetById(id));

        return new ModelAndView("admin_dataset_edit","datasetmodel",model);
    }

    @PostMapping("/edit_dataset")
    public ModelAndView edit_dataset(Dataset dataset){
        logger.info("提交修改的dataset: [{}]", dataset);
        // todo 提交修改
        return new ModelAndView("redirect:/admin_dataset");
    }

    //删除数据集
    @RequestMapping("/admin_dataset_delete")
    public ModelAndView admin_dataset_delete(HttpServletRequest request, Model model) {
        if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

        long id = Long.parseLong(request.getParameter("id"));
        logger.info("删除 dataset.id=[{}]", id);
        // todo 数据库删除对应数据
        return new ModelAndView("redirect:/admin_dataset");
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
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
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

    // 获取所有的课程
    @ResponseBody
    @RequestMapping("/get_all_course")
    public Map get_all_course(HttpServletRequest request) {
        long teaNum = Long.parseLong(cookieThings.getCookieUserNum(request, cookieName));
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
                final String localPath = "static\\images\\upload\\" + target_dir;
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
        if (fileType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document") ||
                fileType.equals("application/pdf") ||
                fileType.equals("application/msword")) {
            // 要上传的目标文件存放的绝对路径
            final String localPath = "static\\reportsTemp";
            //上传后保存的文件名(需要防止图片重名导致的文件覆盖)
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

            if (UploadFileUtils.upload(file, localPath, fileName, true) == "true") {
                //文件存放的相对路径(一般存放在数据库用于img标签的src)
                String relativePath = "static/dataset/" + fileName;
                root.put("relativePath", relativePath);//前端根据是否存在该字段来判断上传是否成功
                result_msg = "文件上传成功";
                root.put("fileFormat", suffixName);
                root.put("size", file.getSize());
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

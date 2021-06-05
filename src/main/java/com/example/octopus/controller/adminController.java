package com.example.octopus.controller;

import com.example.octopus.entity.user.*;
import com.example.octopus.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
public class adminController {

    @Autowired
    MajorService majorService;

    @Autowired
    UserService userService;

    @Autowired
    CourseService courseService;

//    @Autowired
//    DatasetService datasetService;


    //登录
    @RequestMapping("/admin_login")
    public String admin_login(HttpSession session) {
        session.setAttribute("user", "null");
        return "admin_login";
    }

    //登录验证 跳转到首页
    @PostMapping("/admin_confirmlogin")
    public String admin_confirmlogin(@RequestParam("username") String username, @RequestParam("userpwd") String userpwd, HttpSession session) {
        System.out.println("用户名：" + username);
        System.out.println("密码：" + userpwd);
        session.setAttribute("user",username);
        return "redirect:/admin_index";
    }

    //重置密码
    @PostMapping("/admin_reset_pwd_confirm")
    public String admin_reset_pwd_confirm(HttpServletRequest request) {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phonenumber = request.getParameter("phonenumber");
        String new_pwd = request.getParameter("new_pwd");
        System.out.println("用户名：" + username);
        System.out.println("邮箱：" + email);
        System.out.println("手机号码：" + phonenumber);
        System.out.println("新密码：" + new_pwd);
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
        try{
            HttpSession session = request.getSession();
            //System.out.println(session);
            String username = (String)session.getAttribute("user");
            //System.out.println("用户名：" + username);
            model.addAttribute("username", username);
            return "admin_index";
        }
        catch (Exception e){
            return "";
        }
    }

    //个人资料
    @RequestMapping("/admin_userinfo")
    public String admin_userinfo(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("user");
        model.addAttribute("username", username);
        //查询个人资料
        return "admin_userinfo";
    }

    //专业管理
    @RequestMapping("/admin_major")
    public String admin_major(HttpServletRequest request, Model model) {
        System.out.println("进入专业管理");
        //判断用户是管理员还是老师
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("user");
        //根据身份显示信息
        //管理员显示全部专业
        //老师显示自己教的专业
        //System.out.println(majorService.findAllMajor());
        model.addAttribute("majors", majorService.findAllMajor());
        model.addAttribute("username", username);
        return "admin_major";
    }

    //增加专业
    @GetMapping("/admin_major_add")
    public ModelAndView admin_major_add(HttpServletRequest request, Model model){
        System.out.println("进入admin_major_add，获取一个新Major()");
        //Major major = new Major();
        //major.creator = request.getParameter("username");
        String user = request.getParameter("username");
        model.addAttribute("username", user);
        model.addAttribute("major",new Major());
        return new ModelAndView("admin_major_add","majormodel",model);
    }

    @PostMapping("/add_major")
    public ModelAndView add_major(Major major){
        System.out.println("提交新增的major");
        System.out.println(major);
        //major.creator=""
        //MajorService.Add(major);
        return new ModelAndView("redirect:/admin_major");
    }

    //修改专业
    @GetMapping("/admin_major_edit")
    public ModelAndView admin_major_edit(HttpServletRequest request, Model model){
        System.out.println("进入admin_major_edit，获取指定编号的Major()");
        String majorCode = request.getParameter("majorCode");
        System.out.println("majorCode="+majorCode);
        String user = request.getParameter("username");
        model.addAttribute("username", user);
        //Major major = 根据专业代码获取专业信息
        model.addAttribute("major",new Major());
        return new ModelAndView("admin_major_edit","majormodel", model);
    }

    @PostMapping("/edit_major")
    public ModelAndView edit_major(Major major){
        System.out.println("提交修改的major");
        System.out.println(major);
        //更新major
        return new ModelAndView("redirect:/admin_major");
    }

    //删除专业
    @RequestMapping("/admin_major_delete")
    public ModelAndView admin_major_delete(HttpServletRequest request){
        String majorCode = request.getParameter("majorCode");
        System.out.println(majorCode);
        System.out.println("删除 majorCode="+majorCode);
        //根据majorCode删除major
        return new ModelAndView("redirect:/admin_major");
    }

    //班级管理
    @RequestMapping("/admin_class")
    public String admin_class(HttpServletRequest request, Model model) {
        System.out.println("进入班级管理");
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("user");
        model.addAttribute("username", username);
        //判断身份
        //管理员返回所有班级
        //老师只返回自己教的班级
        return "admin_class";
    }

    //增加班级
    @GetMapping("/admin_class_add")
    public ModelAndView admin_class_add(HttpServletRequest request, Model model){
        System.out.println("进入admin_major_add，获取一个新Class_()");
        String user = request.getParameter("username");
        model.addAttribute("username", user);
        model.addAttribute("class",new Class_());
        return new ModelAndView("admin_class_add","classmodel",model);
    }

    @PostMapping("/add_class")
    public ModelAndView add_class(Class_ class_){
        System.out.println("提交新增的class_");
        System.out.println(class_);
        //studentService.增加班级
        return new ModelAndView("redirect:/admin_class");
    }

    //修改班级
    @GetMapping("/admin_class_edit")
    public ModelAndView admin_class_edit(HttpServletRequest request, Model model){
        System.out.println("admin_class_edit，获取指定名字的Class_()");
        String className = request.getParameter("className");
        System.out.println("className="+className);
        String user = request.getParameter("username");
        //根据className查询班级信息
        model.addAttribute("username", user);
        model.addAttribute("class",new Class_());
        return new ModelAndView("admin_class_edit","classmodel",model);
    }

    @PostMapping("/edit_class")
    public ModelAndView edit_class(Class_ class_){
        System.out.println("提交修改的class");
        System.out.println(class_);
        //修改班级信息
        return new ModelAndView("redirect:/admin_class");
    }

    //删除班级
    @RequestMapping("/admin_class_delete")
    public ModelAndView admin_class_delete(HttpServletRequest request){
        String className = request.getParameter("className");
        System.out.println("删除 className="+className);
        //删除班级
        return new ModelAndView("redirect:/admin_class");
    }

    //学生管理
    @RequestMapping("/admin_student")
    public String admin_student(HttpServletRequest request, Model model) {
        System.out.println("进入学生管理");
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("user");
        model.addAttribute("username", username);
        model.addAttribute("students", userService.getStudentList());
        //判断身份
        //管理员返回所有学生
        //老师只返回自己教的学生
        return "admin_student";
    }

    //增加学生
    @GetMapping("/admin_student_add")
    public ModelAndView admin_student_add(HttpServletRequest request, Model model){
        System.out.println("进入admin_student_add，获取一个新Student()");
        String user = request.getParameter("username");
        model.addAttribute("username", user);
        model.addAttribute("student",new Student());
        return new ModelAndView("admin_student_add","stumodel",model);
    }

    @PostMapping("/add_student")
    public ModelAndView add_student(Student student){
        System.out.println("提交新增的student");
        System.out.println(student);
        //补全最近登录时间 登录总时长信息
        //userService.增加学生
        return new ModelAndView("redirect:/admin_student");
    }

    //修改学生
    @GetMapping("/admin_student_edit")
    public ModelAndView admin_student_edit(HttpServletRequest request, Model model){
        System.out.println("admin_student_edit，获取指定名字的Student()");
        long stuNumber = Long.parseLong(request.getParameter("stuNumber"));
        System.out.println("stuNumber="+stuNumber);
        String user = request.getParameter("username");
        Student student = userService.findStudentByStuNumber(stuNumber);
        model.addAttribute("username", user);
        model.addAttribute("student",student);
        return new ModelAndView("admin_student_edit","stumodel",model);
    }

    @PostMapping("/edit_student")
    public ModelAndView edit_student(Student student){
        System.out.println("提交修改的student");
        System.out.println(student);
        //修改学生信息
        return new ModelAndView("redirect:/admin_student");
    }

    //删除学生
    @RequestMapping("/admin_student_delete")
    public ModelAndView admin_student_delete(HttpServletRequest request){
        String stuNumber = request.getParameter("stuNumber");
        System.out.println("删除 stuNumber="+stuNumber);
        //删除学生
        return new ModelAndView("redirect:/admin_student");
    }

    //开课计划管理
    @RequestMapping("/admin_course")
    public String admin_course(HttpServletRequest request,Model model) {
        System.out.println("进入开课计划管理");
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("user");
        //model.addAttribute("username", "李四");
        model.addAttribute("courses", courseService.listCourses());
        return "admin_course";
    }

    //课程增加
    @GetMapping("/admin_course_add")
    public ModelAndView admin_course_add(HttpServletRequest request, Model model){
        System.out.println("进入admin_course_add，获取一个新Course()");
        String user = request.getParameter("username");
        model.addAttribute("username", user);
        model.addAttribute("course",new Course());
        return new ModelAndView("admin_course_add","coursemodel",model);
    }

    @PostMapping("/add_course")
    public ModelAndView add_course(Course course){
        System.out.println("提交新增的course");
        System.out.println(course);
        courseService.insertCourse(course);
        return new ModelAndView("redirect:/admin_course");
    }

    //修改课程
    @GetMapping("/admin_course_edit")
    public ModelAndView admin_course_edit(HttpServletRequest request, Model model){
        System.out.println("进入admin_course_edit，获取指定名字的Course()");
        long courseId = Long.parseLong(request.getParameter("courseId"));
        System.out.println("courseId="+courseId);
        String user = request.getParameter("username");
        model.addAttribute("username", user);
        model.addAttribute("course", courseService.findCourseById(courseId));
        return new ModelAndView("admin_course_edit","coursemodel",model);
    }

    @PostMapping("/edit_course")
    public ModelAndView edit_course(Course course){
        System.out.println("提交修改的course");
        System.out.println(course);
        courseService.updateCourse(course);
        return new ModelAndView("redirect:/admin_course");
    }

    //删除课程
    @RequestMapping("/admin_course_delete")
    public ModelAndView admin_course_delete(HttpServletRequest request){
        long courseId = Long.parseLong(request.getParameter("courseId"));
        System.out.println("删除 courseId="+courseId);
        courseService.deleteCourseById(courseId);
        return new ModelAndView("redirect:/admin_course");
    }

    //学生开课计划管理
    @RequestMapping("/admin_course_student")
    public String admin_course_student(Model model) {
        System.out.println("进入学生开课计划管理");
        //model.addAttribute("username", "李四");
        //返回
        //model.addAttribute("course_students", );
        return "admin_course_student";
    }

    //学生开课计划增加
    @GetMapping("/admin_course_student_add")
    public ModelAndView admin_course_student_add(HttpServletRequest request, Model model){
        System.out.println("进入admin_course_student_add，Student_Course()");
        String user = request.getParameter("username");
        model.addAttribute("username", user);
        model.addAttribute("course_student",new StudentCourse());
        return new ModelAndView("admin_course_student_add","coursestudentmodel",model);
    }

    @PostMapping("/add_course_student")
    public ModelAndView add_course_student(StudentCourse course_student){
        System.out.println("提交新增的course_student");
        System.out.println(course_student);
        //增加
        return new ModelAndView("redirect:/admin_course_student");
    }

    //修改学生开课计划
    @GetMapping("/admin_course_student_edit")
    public ModelAndView admin_course_student_edit(HttpServletRequest request, Model model){
        System.out.println("进入admin_course_student_edit，获取指定名字的Student_Course()");
        long id = Long.parseLong(request.getParameter("id"));
        System.out.println("id="+id);
        String user = request.getParameter("username");
        //查找对应数据
        model.addAttribute("username", user);
        model.addAttribute("course_student",new StudentCourse());
        return new ModelAndView("admin_course_student_edit","coursestudentmodel",model);
    }

    @PostMapping("/edit_course_student")
    public ModelAndView edit_course_student(StudentCourse course_student){
        System.out.println("提交修改的course_student");
        System.out.println(course_student);
        //修改学生开课计划
        return new ModelAndView("redirect:/admin_course_student");
    }

    //删除学生开课计划
    @RequestMapping("/admin_course_student_delete")
    public ModelAndView admin_course_student_delete(HttpServletRequest request){
        long courseId = Long.parseLong(request.getParameter("id"));
        System.out.println("删除 courseId="+courseId);
        //删除
        return new ModelAndView("redirect:/admin_course_student");
    }

    @RequestMapping("/admin_video_log")
    public String admin_video_log(Model model) {
        model.addAttribute("username", "李四");
        return "admin_video_log";
    }

    @RequestMapping("/admin_video_log_details")
    public String admin_video_log_details(Model model) {
        model.addAttribute("username", "李四");
        return "admin_video_log_details";
    }

    @RequestMapping("/admin_report")
    public String admin_report(Model model) {
        model.addAttribute("username", "李四");
        return "admin_report";
    }

    @RequestMapping("/admin_experiment_log")
    public String admin_experiment_log(Model model) {
        model.addAttribute("username", "李四");
        return "admin_experiment_log";
    }

    @RequestMapping("/admin_pc_type")
    public String admin_pc_type(Model model) {
        model.addAttribute("username", "李四");
        return "admin_pc_type";
    }

    @RequestMapping("/admin_cluster")
    public String admin_cluster(Model model) {
        model.addAttribute("username", "李四");
        return "admin_cluster";
    }

    @RequestMapping("/admin_student_pc")
    public String admin_student_pc(Model model) {
        model.addAttribute("username", "李四");
        return "admin_student_pc";
    }

    @RequestMapping("/admin_report_template")
    public String admin_report_template(Model model) {
        model.addAttribute("username", "李四");
        return "admin_report_template";
    }

    @RequestMapping("/admin_video")
    public String admin_video(Model model) {
        model.addAttribute("username", "李四");
        return "admin_video";
    }

    @RequestMapping("/admin_video_class")
    public String admin_video_class(Model model) {
        model.addAttribute("username", "李四");
        return "admin_video_class";
    }

}

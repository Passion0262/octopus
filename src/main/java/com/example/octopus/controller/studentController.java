package com.example.octopus.controller;

import com.example.octopus.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.octopus.entity.user.Student;

import javax.servlet.http.HttpSession;

@Controller
public class studentController {

    private Logger logger = LoggerFactory.getLogger(studentController.class);

    @Autowired
    UserService userService;

    @RequestMapping("/login")
    public String login(Model model) {
        return "auth-login";
    }

    @RequestMapping("/")
    public String showHome(Model model, HttpSession session) {
        String stuNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        Student stu = userService.findStudentByStuNumber(stuNumber);
        logger.info("当前登陆用户：" + stuNumber+":"+stu.getName());
//        model.addAttribute("stu", stu);

        session.setAttribute("stuname",stu.getName());
        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index(Model model,HttpSession session) {
        String stuname = (String)session.getAttribute("stuname");
        model.addAttribute("stuname", stuname);
        return "index";
    }

    @RequestMapping("/userinfo")
    public String userinfo(Model model,HttpSession session) {
        String stuname = (String)session.getAttribute("stuname");
        model.addAttribute("stuname", stuname);

        String stuNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        Student stu = userService.findStudentByStuNumber(stuNumber);
        model.addAttribute("stu", stu);

        return "userinfo";
    }


    @RequestMapping("/applycourse")
    public String applycourse(Model model,HttpSession session) {
        String stuname = (String)session.getAttribute("stuname");
        model.addAttribute("stuname", stuname);
        return "applycourse";
    }


    @RequestMapping("/apply_detail")
    public String apply_detail(Model model,HttpSession session) {
        String stuname = (String)session.getAttribute("stuname");
        model.addAttribute("stuname", stuname);
        return "apply_detail";
    }

    @RequestMapping("/mycourse")
    public String mycourse(Model model,HttpSession session) {
        String stuname = (String)session.getAttribute("stuname");
        model.addAttribute("stuname", stuname);
        return "mycourse";
    }

    @RequestMapping("/course_video")
    public String course_video(Model model) {
        return "course_video";
    }

    @RequestMapping("/experiment_task")
    public String experiment_task(Model model,HttpSession session) {
        String stuname = (String)session.getAttribute("stuname");
        model.addAttribute("stuname", stuname);
        return "experiment_task";
    }

    @RequestMapping("/experiment_task_detail")
    public String experiment_task_detail(Model model,HttpSession session) {
        String stuname = (String)session.getAttribute("stuname");
        model.addAttribute("stuname", stuname);
        return "experiment_task_detail";
    }

    @RequestMapping("/projects")
    public String projects(Model model,HttpSession session) {
        String stuname = (String)session.getAttribute("stuname");
        model.addAttribute("stuname", stuname);
        return "projects";
    }

    @RequestMapping("/project_detail")
    public String project_detail(Model model,HttpSession session) {
        String stuname = (String)session.getAttribute("stuname");
        model.addAttribute("stuname", stuname);
        return "project_detail";
    }

    @RequestMapping("/studylog")
    public String studylog(Model model,HttpSession session) {
        String stuname = (String)session.getAttribute("stuname");
        model.addAttribute("stuname", stuname);
        return "studylog";
    }

    @RequestMapping("/studylog_detail")
    public String studylog_detail(Model model,HttpSession session) {
        String stuname = (String)session.getAttribute("stuname");
        model.addAttribute("stuname", stuname);
        return "studylog_detail";
    }

    @RequestMapping("/datasets")
    public String datasets(Model model,HttpSession session) {
        String stuname = (String)session.getAttribute("stuname");
        model.addAttribute("stuname", stuname);
        return "datasets";
    }

    @RequestMapping("/dataset_detail")
    public String dataset_detail(Model model,HttpSession session) {
        String stuname = (String)session.getAttribute("stuname");
        model.addAttribute("stuname", stuname);
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

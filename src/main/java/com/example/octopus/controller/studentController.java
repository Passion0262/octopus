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

@Controller
public class studentController {

    private Logger logger = LoggerFactory.getLogger(studentController.class);

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String showHome() {
        String stuNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("当前登陆用户：" + stuNumber);
        return "index";
    }

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("username", "李四");
        return "index";
    }


    @RequestMapping("/applycourse")
    public String applycourse(Model model) {
        model.addAttribute("username", "李四");
        return "applycourse";
    }


    @RequestMapping("/apply_detail")
    public String apply_detail(Model model) {
        return "apply_detail";
    }

    @RequestMapping("/mycourse")
    public String mycourse(Model model) {
        model.addAttribute("username", "李四");
        return "mycourse";
    }

    @RequestMapping("/course_video")
    public String course_video(Model model) {
        return "course_video";
    }

    @RequestMapping("/experiment_task")
    public String experiment_task(Model model) {
        model.addAttribute("username", "李四");
        return "experiment_task";
    }

    @RequestMapping("/experiment_task_detail")
    public String experiment_task_detail(Model model) {

        return "experiment_task_detail";
    }

    @RequestMapping("/projects")
    public String projects(Model model) {
        model.addAttribute("username", "李四");
        return "projects";
    }

    @RequestMapping("/project_detail")
    public String project_detail(Model model) {

        return "project_detail";
    }

    @RequestMapping("/studylog")
    public String studylog(Model model) {
        model.addAttribute("username", "李四");
        return "studylog";
    }

    @RequestMapping("/studylog_detail")
    public String studylog_detail(Model model) {
        model.addAttribute("username", "李四");
        return "studylog_detail";
    }

    @RequestMapping("/datasets")
    public String datasets(Model model) {
        model.addAttribute("username", "李四");
        return "datasets";
    }

    @RequestMapping("/dataset_detail")
    public String dataset_detail(Model model) {

        return "dataset_detail";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("username", "李四");
        return "auth-login";
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

    @RequestMapping("/userinfo")
    public String userinfo(Model model) {

        return "userinfo";
    }

}

package com.example.octopus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class adminController {

    @RequestMapping("/admin_login")
    public String admin_login(Model model) {
        model.addAttribute("username", "李四");
        return "admin_login";
    }

    @RequestMapping("/admin_userinfo")
    public String admin_userinfo(Model model) {
        model.addAttribute("username", "李四");
        return "admin_userinfo";
    }

    @RequestMapping("/admin_reset_pwd")
    public String admin_reset_pwd(Model model) {
        model.addAttribute("username", "李四");
        return "admin_reset_pwd";
    }

    @RequestMapping("/admin_index")
    public String admin_index(Model model) {
        model.addAttribute("username", "李四");
        return "admin_index";
    }

    @RequestMapping("/admin_major")
    public String admin_major(Model model) {
        model.addAttribute("username", "李四");
        return "admin_major";
    }

    @RequestMapping("/admin_class")
    public String admin_class(Model model) {
        model.addAttribute("username", "李四");
        return "admin_class";
    }

    @RequestMapping("/admin_student")
    public String admin_student(Model model) {
        model.addAttribute("username", "李四");
        return "admin_student";
    }

    @RequestMapping("/admin_course")
    public String admin_course(Model model) {
        model.addAttribute("username", "李四");
        return "admin_course";
    }

    @RequestMapping("/admin_course_student")
    public String admin_course_student(Model model) {
        model.addAttribute("username", "李四");
        return "admin_course_student";
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

package com.example.octopus.controller;

import com.example.octopus.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class studentController {

    @Autowired
    StudentService studentService;

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

    @RequestMapping("/mycourse")
    public String mycourse(Model model) {
        model.addAttribute("username", "李四");
        return "mycourse";
    }

    @RequestMapping("/experiment_task")
    public String experiment_task(Model model) {
        model.addAttribute("username", "李四");
        return "experiment_task";
    }

    @RequestMapping("/projects")
    public String projects(Model model) {
        model.addAttribute("username", "李四");
        return "projects";
    }

    @RequestMapping("/studylog")
    public String studylog(Model model) {
        model.addAttribute("username", "李四");
        return "studylog";
    }

    @RequestMapping("/datasets")
    public String datasets(Model model) {
        model.addAttribute("username", "李四");
        return "datasets";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("username", "李四");
        return "auth-login";
    }

}

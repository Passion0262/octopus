package com.example.octopus.controller;

import com.example.octopus.entity.Student;
import com.example.octopus.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}

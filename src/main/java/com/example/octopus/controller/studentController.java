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

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("username", "李四");
        return "index";
    }

    @RequestMapping("/auth-login")
    public String login(Model model) {
        model.addAttribute("username", "李四");
        return "auth-login";
    }
}

package com.example.octopus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class adminController {

    @RequestMapping("/admin_index")
    public String index(Model model) {
        model.addAttribute("username", "李四");
        return "admin_index";
    }
}

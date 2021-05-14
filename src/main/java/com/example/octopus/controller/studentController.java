package com.example.octopus.controller;

import com.example.octopus.entity.Student;
import com.example.octopus.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class studentController {

    @Autowired
    StudentService studentService;

    @RequestMapping("/student")
    public Student showStudent() {
        return studentService.findStudentByStuNumber("6201924124");
    }

}

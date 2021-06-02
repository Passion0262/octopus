package com.example.octopus.controller;

import com.example.octopus.entity.dataset.Dataset;
import com.example.octopus.entity.experiment.Experiment;
import com.example.octopus.entity.experiment.ExperimentMission;
import com.example.octopus.entity.project.Project;
import com.example.octopus.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.octopus.entity.user.Student;
import com.example.octopus.entity.user.Course;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class studentController {

    private Logger logger = LoggerFactory.getLogger(studentController.class);

    @Autowired
    UserService userService;
    @Autowired
    CourseService courseService;
    @Autowired
    ExperimentService experimentService;
    @Autowired
    ProjectService projectService;
    @Autowired
    DatasetService datasetService;


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
        String stuNumber = SecurityContextHolder.getContext().getAuthentication().getName();
        Student stu = userService.findStudentByStuNumber(stuNumber);
        session.setAttribute("stuname",stu.getName());


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

    @RequestMapping("/update_phoneNumber")
    public String update_phoneNumber(@RequestParam("phoneNumber")String phoneNumber,Model model,HttpSession session) {
        logger.info("phoneNumber:" + phoneNumber);

//        这里需要一个手机号码的更改操作

        return "redirect:userinfo";
    }











    @RequestMapping("/applycourse")
    public String applycourse(Model model,HttpSession session) {
        String stuname = (String)session.getAttribute("stuname");
        model.addAttribute("stuname", stuname);

        List<Course> allcourses = courseService.findAllCourses();
//        logger.info("allcourses:" + allcourses);
        model.addAttribute("allcourses", allcourses);


        return "applycourse";
    }


    @RequestMapping("/apply_detail/{id}")
    public String apply_detail(@PathVariable(value = "id") String id, Model model, HttpSession session) {
        String stuname = (String)session.getAttribute("stuname");
        model.addAttribute("stuname", stuname);

        logger.info("coursedetail_id:" + id);

        //        这里需要一个根据课程的id 返回course操作
        Course course = courseService.findAllCourses().get(0);

        logger.info("course——detail:" + course);
        model.addAttribute("course", course);

        //        这里需要一个根据课程的id 用户id 判断是否选课
        model.addAttribute("isapplied", 1);

        return "apply_detail";
    }

    @RequestMapping("/apply_detail/sure_apply")
    public String sure_apply( String id, Model model, HttpSession session) {
        String stuname = (String)session.getAttribute("stuname");

        logger.info("确认_id:" + id);

        //        这里需要一个根据用户的id 课程id 添加申请课程

        //        这里需要一个根据课程的id 用户id 判断是否选课
        model.addAttribute("isapplied", 1);

        return "redirect:/apply_detail/id="+ id;
    }

    @RequestMapping("/apply_detail/cancel_apply")
    public String cancel_apply( String id, Model model, HttpSession session) {
        String stuname = (String)session.getAttribute("stuname");

        logger.info("取消申请_id:" + id);

        //        这里需要一个根据用户的id 课程id 取消申请课程

        //        这里需要一个根据课程的id 用户id 判断是否选课
        model.addAttribute("isapplied", 0);

        return "redirect:/apply_detail/id="+ id;
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

        List<ExperimentMission> allexperiments = experimentService.findAllExperimentMission();
//        logger.info("allExperiment:" + allexperiments);
        model.addAttribute("allexperiments", allexperiments);

        return "experiment_task";
    }

    @RequestMapping("/experiment_task_detail/{id}")
    public String experiment_task_detail(String id,Model model,HttpSession session) {
        String stuname = (String)session.getAttribute("stuname");
        model.addAttribute("stuname", stuname);

        logger.info("experimentMission_id:" + id);

        //        这里需要一个根据实验的id 返回course操作
        ExperimentMission experimentMission = experimentService.findAllExperimentMission().get(0);

        logger.info("experimentMission——detail:" + experimentMission);
        model.addAttribute("experimentMission", experimentMission);


        return "experiment_task_detail";
    }











    @RequestMapping("/projects")
    public String projects(Model model,HttpSession session) {
        String stuname = (String)session.getAttribute("stuname");
        model.addAttribute("stuname", stuname);

        List<Project> allprojects= projectService.findAllProject();
//        logger.info("allProject:" + allprojects);
        model.addAttribute("allprojects", allprojects);

        return "projects";
    }

    @RequestMapping("/project_detail/{id}")
    public String project_detail(String id,Model model,HttpSession session) {
        String stuname = (String)session.getAttribute("stuname");
        model.addAttribute("stuname", stuname);

        logger.info("project_id:" + id);

        //        这里需要一个根据实验的id 返回course操作
        Project project = projectService.findAllProject().get(0);
        logger.info("project——detail:" + project);
        model.addAttribute("project", project);

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

        List<Dataset> alldatasets = datasetService.findAllDataset();
        logger.info("alldatasets" + alldatasets);
        model.addAttribute("alldatasets", alldatasets);

        return "datasets";
    }

    @RequestMapping("/dataset_detail/{id}")
    public String dataset_detail(String id,Model model,HttpSession session) {
        String stuname = (String)session.getAttribute("stuname");
        model.addAttribute("stuname", stuname);

        logger.info("dataset_id:" + id);

        //        这里需要一个根据数据集的id 返回dataset操作
        Dataset dataset = datasetService.findAllDataset().get(0);

        logger.info("dataset——detail:" + dataset);
        model.addAttribute("dataset",dataset);

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

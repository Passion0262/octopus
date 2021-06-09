package com.example.octopus.controller;

import com.alibaba.fastjson.JSON;
import com.example.octopus.entity.user.*;
import com.example.octopus.service.*;
import com.example.octopus.utils.UploadFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Controller
public class adminController {
    
    private Logger logger = LoggerFactory.getLogger(adminController.class);

    @Autowired
    UserService userService;

    @Autowired
    MajorService majorService;

    @Autowired
    ClassService classService;

    @Autowired
    CourseService courseService;

    @Autowired
    DatasetService datasetService;
    
    


    //登录
    @RequestMapping("/admin_login")
    public String admin_login(HttpSession session) {
        session.setAttribute("user", "null");
        return "admin_login";
    }

    //登录验证 跳转到首页
    @PostMapping("/admin_confirmlogin")
    public String admin_confirmlogin(@RequestParam("username") String username, @RequestParam("userpwd") String userpwd, HttpSession session) {
        logger.info("用户名：" + username);
        logger.info("密码：" + userpwd);
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
        logger.info("用户名：" + username);
        logger.info("邮箱：" + email);
        logger.info("手机号码：" + phonenumber);
        logger.info("新密码：" + new_pwd);
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
            //logger.info(session);
            String username = (String)session.getAttribute("user");
            //logger.info("用户名：" + username);
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
        logger.info("进入专业管理");
        //判断用户是管理员还是老师
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("user");
        model.addAttribute("majors", majorService.listMajors());
        model.addAttribute("username", username);
        return "admin_major";
    }

    //增加专业
    @GetMapping("/admin_major_add")
    public ModelAndView admin_major_add(HttpServletRequest request, Model model){
        logger.info("进入admin_major_add，获取一个新Major()");
        String user = request.getParameter("username");
        model.addAttribute("username", user);
        model.addAttribute("major",new Major());
        return new ModelAndView("admin_major_add","majormodel",model);
    }

    @PostMapping("/add_major")
    public ModelAndView add_major(Major major){
        logger.info("提交新增的major: [{}]", major);
        majorService.insertMajor(major);
        return new ModelAndView("redirect:/admin_major");
    }

    //修改专业
    @GetMapping("/admin_major_edit")
    public ModelAndView admin_major_edit(HttpServletRequest request, Model model){
        logger.info("进入admin_major_edit，获取指定编号的Major()");
        long id = Long.parseLong(request.getParameter("id"));
        logger.info("id="+id);
        String user = request.getParameter("username");
        model.addAttribute("username", user);
        model.addAttribute("major", majorService.getById(id));
        return new ModelAndView("admin_major_edit","majormodel", model);
    }

    @PostMapping("/edit_major")
    public ModelAndView edit_major(Major major){
        logger.info("提交修改的major: [{}]", major);
        majorService.updateMajor(major);
        return new ModelAndView("redirect:/admin_major");
    }

    //删除专业
    @RequestMapping("/admin_major_delete")
    public ModelAndView admin_major_delete(HttpServletRequest request){
        long id = Long.parseLong(request.getParameter("id"));
        logger.info("删除专业 id=[{}]", id);
        majorService.deleteById(id);
        return new ModelAndView("redirect:/admin_major");
    }

    //班级管理
    @RequestMapping("/admin_class")
    public String admin_class(HttpServletRequest request, Model model) {
        logger.info("进入班级管理");
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("user");
        model.addAttribute("username", username);
        model.addAttribute("classes", classService.listClass_s());
        return "admin_class";
    }

    //增加班级
    @GetMapping("/admin_class_add")
    public ModelAndView admin_class_add(HttpServletRequest request, Model model){
        logger.info("进入admin_major_add，获取一个新Class_()");
        String user = request.getParameter("username");
        model.addAttribute("username", user);
        model.addAttribute("class",new Class_());
        return new ModelAndView("admin_class_add","classmodel",model);
    }

    @PostMapping("/add_class")
    public ModelAndView add_class(Class_ class_){
        logger.info("提交新增的class_: [{}]", class_);
        classService.insertClass(class_);
        return new ModelAndView("redirect:/admin_class");
    }

    //修改班级
    @GetMapping("/admin_class_edit")
    public ModelAndView admin_class_edit(HttpServletRequest request, Model model){
        logger.info("admin_class_edit，获取指定id的Class_()");
        long id = Long.parseLong(request.getParameter("id"));
        logger.info("class id="+id);
        String user = request.getParameter("username");
        model.addAttribute("username", user);
        model.addAttribute("class", classService.getClass_Byid(id));
        return new ModelAndView("admin_class_edit","classmodel",model);
    }

    @PostMapping("/edit_class")
    public ModelAndView edit_class(Class_ class_){
        logger.info("提交修改的class_: [{}]", class_);
        //修改
        return new ModelAndView("redirect:/admin_class");
    }

    //删除班级
    @RequestMapping("/admin_class_delete")
    public ModelAndView admin_class_delete(HttpServletRequest request){
        String className = request.getParameter("className");
        logger.info("删除 className="+className);
        classService.deleteByClassName(className);
        return new ModelAndView("redirect:/admin_class");
    }

    //学生管理
    @RequestMapping("/admin_student")
    public String admin_student(HttpServletRequest request, Model model) {
        logger.info("进入学生管理");
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("user");
        model.addAttribute("username", username);
        model.addAttribute("students", userService.listStudents());
        //判断身份
        //管理员返回所有学生
        //老师只返回自己教的学生
        return "admin_student";
    }

    //增加学生
    @GetMapping("/admin_student_add")
    public ModelAndView admin_student_add(HttpServletRequest request, Model model){
        logger.info("进入admin_student_add，获取一个新Student()");
        String user = request.getParameter("username");
        model.addAttribute("username", user);
        model.addAttribute("student",new Student());
        return new ModelAndView("admin_student_add","stumodel",model);
    }

    @PostMapping("/add_student")
    public ModelAndView add_student(Student student){
        logger.info("提交新增的student: [{}]", student);
        //补全最近登录时间 登录总时长信息
        //userService.增加学生
        return new ModelAndView("redirect:/admin_student");
    }

    //修改学生
    @GetMapping("/admin_student_edit")
    public ModelAndView admin_student_edit(HttpServletRequest request, Model model){
        logger.info("admin_student_edit，获取指定名字的Student()");
        long stuNumber = Long.parseLong(request.getParameter("stuNumber"));
        logger.info("stuNumber="+stuNumber);
        String user = request.getParameter("username");
        Student student = userService.getStudentByStuNumber(stuNumber);
        model.addAttribute("username", user);
        model.addAttribute("student",student);
        return new ModelAndView("admin_student_edit","stumodel",model);
    }

    @PostMapping("/edit_student")
    public ModelAndView edit_student(Student student){
        logger.info("提交修改的student: [{}]", student);
        //修改学生信息
        return new ModelAndView("redirect:/admin_student");
    }

    //删除学生
    @RequestMapping("/admin_student_delete")
    public ModelAndView admin_student_delete(HttpServletRequest request){
        String stuNumber = request.getParameter("stuNumber");
        logger.info("删除 stuNumber="+stuNumber);
        //删除学生
        return new ModelAndView("redirect:/admin_student");
    }

    //开课计划管理
    @RequestMapping("/admin_course")
    public String admin_course(HttpServletRequest request,Model model) {
        logger.info("进入开课计划管理");
        HttpSession session = request.getSession();
        String username = (String)session.getAttribute("user");
        //model.addAttribute("username", "李四");
        model.addAttribute("courses", courseService.listCourses());
        return "admin_course";
    }

    //课程增加
    @GetMapping("/admin_course_add")
    public ModelAndView admin_course_add(HttpServletRequest request, Model model){
        logger.info("进入admin_course_add，获取一个新Course()");
        String user = request.getParameter("username");
        model.addAttribute("username", user);
        model.addAttribute("course",new Course());
        return new ModelAndView("admin_course_add","coursemodel",model);
    }

    @PostMapping("/add_course")
    public ModelAndView add_course(Course course){
        logger.info("提交新增的course: [{}]", course);
        courseService.insertCourse(course);
        return new ModelAndView("redirect:/admin_course");
    }

    //修改课程
    @GetMapping("/admin_course_edit")
    public ModelAndView admin_course_edit(HttpServletRequest request, Model model){
        logger.info("进入admin_course_edit，获取指定名字的Course()");
        long courseId = Long.parseLong(request.getParameter("courseId"));
        logger.info("courseId="+courseId);
        String user = request.getParameter("username");
        model.addAttribute("username", user);
        model.addAttribute("course", courseService.getCourseById(courseId));
        return new ModelAndView("admin_course_edit","coursemodel",model);
    }

    @PostMapping("/edit_course")
    public ModelAndView edit_course(Course course){
        logger.info("提交修改的course: [{}]", course);
        courseService.updateCourse(course);
        return new ModelAndView("redirect:/admin_course");
    }

    //删除课程
    @RequestMapping("/admin_course_delete")
    public ModelAndView admin_course_delete(HttpServletRequest request){
        long courseId = Long.parseLong(request.getParameter("courseId"));
        logger.info("删除 courseId="+courseId);
        courseService.deleteCourseById(courseId);
        return new ModelAndView("redirect:/admin_course");
    }

    //学生开课计划管理
    @RequestMapping("/admin_course_student")
    public String admin_course_student(Model model) {
        logger.info("进入学生开课计划管理");
        //model.addAttribute("username", "李四");
        //返回
        //model.addAttribute("course_students", );
        return "admin_course_student";
    }

    //学生开课计划增加
    @GetMapping("/admin_course_student_add")
    public ModelAndView admin_course_student_add(HttpServletRequest request, Model model){
        logger.info("进入admin_course_student_add，Student_Course()");
        String user = request.getParameter("username");
        model.addAttribute("username", user);
        model.addAttribute("course_student",new StudentCourse());
        return new ModelAndView("admin_course_student_add","coursestudentmodel",model);
    }

    @PostMapping("/add_course_student")
    public ModelAndView add_course_student(StudentCourse course_student){
        logger.info("提交新增的course_student: [{}]", course_student);
        //增加
        return new ModelAndView("redirect:/admin_course_student");
    }

    //修改学生开课计划
    @GetMapping("/admin_course_student_edit")
    public ModelAndView admin_course_student_edit(HttpServletRequest request, Model model){
        logger.info("进入admin_course_student_edit，获取指定名字的Student_Course()");
        long id = Long.parseLong(request.getParameter("id"));
        logger.info("id="+id);
        String user = request.getParameter("username");
        //查找对应数据
        model.addAttribute("username", user);
        model.addAttribute("course_student",new StudentCourse());
        return new ModelAndView("admin_course_student_edit","coursestudentmodel",model);
    }

    @PostMapping("/edit_course_student")
    public ModelAndView edit_course_student(StudentCourse course_student){
        logger.info("提交修改的course_student: [{}]", course_student);
        //修改学生开课计划
        return new ModelAndView("redirect:/admin_course_student");
    }

    //删除学生开课计划
    @RequestMapping("/admin_course_student_delete")
    public ModelAndView admin_course_student_delete(HttpServletRequest request){
        long courseId = Long.parseLong(request.getParameter("id"));
        logger.info("删除 courseId="+courseId);
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

    @RequestMapping("/admin_report_detail")
    public String admin_report_detail(Model model) {
        model.addAttribute("username", "李四");
        model.addAttribute("pdf", "https://arxiv.org/pdf/1508.01006v1.pdf");
        return "admin_report_detail";
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

    //数据集管理
    @RequestMapping("/admin_dataset")
    public String admin_dataset(Model model) {
        model.addAttribute("username", "李四");
        model.addAttribute("datasets", datasetService.listDatasets());
        return "admin_dataset";
    }

    @ResponseBody
    @RequestMapping("/upload_img")
    public Map imageUpload(MultipartFile img, HttpServletRequest request){
        String result_msg = "";  //上传结果信息
        Map<String,Object> root = new HashMap<String, Object>();

        if (img.getSize() > 1024 * 1024 * 5){
            result_msg="图片大小不能超过5M";
        }
        else{

//            Map dict = new HashMap();
//            dict.put("admin_course_add", "\\course");
//            dict.put("admin_userinfo", "\\course");
            String target_dir = "";
            String url = request.getHeader("Referer");
            //logger.info("提交post请求的url:" + url);
            if(url.contains("admin_course")){
                target_dir = "course";
            }
            else if(url.contains("admin_userinfo")){
                target_dir = "userinfo";
            }

            //判断上传文件格式
            String fileType = img.getContentType();
            if (fileType.equals("image/jpeg") || fileType.equals("image/png") || fileType.equals("image/jpg")) {
                // 要上传的目标文件存放的绝对路径
                final String localPath="static\\images\\upload\\"+target_dir;
                //上传后保存的文件名(需要防止图片重名导致的文件覆盖)
                //获取文件名
                String fileName = img.getOriginalFilename();
                //获取文件后缀名
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                //重新生成文件名
                fileName = UUID.randomUUID()+suffixName;
                if (UploadFileUtils.upload(img, localPath, fileName)) {
                    //文件存放的相对路径(一般存放在数据库用于img标签的src)
                    String relativePath="static/images/upload/"+target_dir+"/"+fileName;
                    root.put("relativePath",relativePath);//前端根据是否存在该字段来判断上传是否成功
                    result_msg="图片上传成功";
                }
                else{
                    result_msg="图片上传失败";
                }
            }
            else{
                result_msg="图片格式不正确";
            }
        }

        root.put("result_msg",result_msg);

//        JSON.toJSONString(root,SerializerFeature.DisableCircularReferenceDetect);
        String root_json= JSON.toJSONString(root);
        logger.info(root_json);
        return root;
    }


}

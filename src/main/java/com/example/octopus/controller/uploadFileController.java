package com.example.octopus.controller;

import com.alibaba.fastjson.JSON;
import com.example.octopus.utils.PropertiesUtil;
import com.example.octopus.utils.UploadFileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class uploadFileController {

    private Logger logger = LoggerFactory.getLogger(uploadFileController.class);

    private PropertiesUtil propertiesUtil = new PropertiesUtil();
    private String WEB_BASE_PATH = propertiesUtil.getFileSavePath();
    private String WEB_HOST = "/static/";

    //上传图片
    @ResponseBody
    @RequestMapping("/upload_img")
    public Map imageUpload(MultipartFile img, HttpServletRequest request) {
        String result_msg = "";  //上传结果信息
        Map<String, Object> root = new HashMap<String, Object>();

        if (img.getSize() > 1024 * 1024 * 5) {
            result_msg = "图片大小不能超过5M";
        } else {

//            Map dict = new HashMap();
//            dict.put("admin_course_add", "\\course");
//            dict.put("admin_userinfo", "\\course");
            String target_dir = "";
            String url = request.getHeader("Referer");
            //logger.info("提交post请求的url:" + url);
            if (url.contains("admin_course")) {
                target_dir = "course/";
            } else if (url.contains("admin_userinfo")) {
                target_dir = "userinfo/";
            } else if (url.contains("admin_dataset")) {
                target_dir = "dataset/";
            }

            //判断上传文件格式
            String fileType = img.getContentType();
            if (fileType.equals("image/jpeg") || fileType.equals("image/png") || fileType.equals("image/jpg")) {
                // 要上传的目标文件存放的绝对路径
                final String localPath = WEB_BASE_PATH + "images/" + target_dir;
                //上传后保存的文件名(需要防止图片重名导致的文件覆盖)
                //获取文件名
                String fileName = img.getOriginalFilename();
                //获取文件后缀名
                String suffixName = fileName.substring(fileName.lastIndexOf("."));
                //重新生成文件名
                fileName = UUID.randomUUID() + suffixName;
                String result = UploadFileUtils.upload(img, localPath, fileName, false);
                while (result.equals("exists")){
                    //重新生成文件名
                    fileName = UUID.randomUUID() + suffixName;
                    result = UploadFileUtils.upload(img, localPath, fileName, false);
                }
                if (result.equals("true")) {
                    //文件存放的相对路径(一般存放在数据库用于img标签的src)
                    String relativePath = WEB_HOST + "images/" + target_dir + fileName;
                    root.put("relativePath", relativePath);//前端根据是否存在该字段来判断上传是否成功
                    result_msg = "图片上传成功";
                } else {
                    result_msg = "图片上传失败!";
                }
            } else {
                result_msg = "图片格式不正确!";
            }
        }

        root.put("result_msg", result_msg);

//        JSON.toJSONString(root,SerializerFeature.DisableCircularReferenceDetect);
        String root_json = JSON.toJSONString(root);
        logger.info(root_json);
        return root;
    }

    //上传数据集
    @ResponseBody
    @RequestMapping("/upload_dataset")
    public Map datasetUpload(MultipartFile file, HttpServletRequest request) {
        String result_msg = "";  //上传结果信息
        Map<String, Object> root = new HashMap<String, Object>();

        //判断上传文件格式
        String fileType = file.getContentType();
        //if (fileType.equals("text/plain") || fileType.equals("application/json")) {
        if (true) {
            // 要上传的目标文件存放的绝对路径
            final String localPath = WEB_BASE_PATH + "dataset/";
            //获取文件名
            String fileName = file.getOriginalFilename();
            //获取文件后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            fileName = UUID.randomUUID() + suffixName;
            String result = UploadFileUtils.upload(file, localPath, fileName, false);
            while (result.equals("exists")){
                //重新生成文件名
                fileName = UUID.randomUUID() + suffixName;
                result = UploadFileUtils.upload(file, localPath, fileName, false);
            }
            if (result.equals("true")) {
                String relativePath = WEB_HOST + "dataset/" + fileName;
                root.put("relativePath", relativePath);//前端根据是否存在该字段来判断上传是否成功
                result_msg = "文件上传成功";
                root.put("fileFormat", suffixName);
                root.put("size", Math.round(file.getSize()/1024));
            } else {
                result_msg = "文件上传失败";
            }
        } else {
            result_msg = "文件格式不正确";
        }
        root.put("result_msg", result_msg);

        String root_json = JSON.toJSONString(root);
        logger.info(root_json);
        return root;
    }

    //上传视频
    @ResponseBody
    @RequestMapping("/upload_video")
    public Map videoUpload(MultipartFile file, HttpServletRequest request) {
        String result_msg = "";  //上传结果信息
        Map<String, Object> root = new HashMap<String, Object>();

        //判断上传文件格式
        String fileType = file.getContentType();
        if (fileType.equals("video/mp4") ||
                fileType.equals("video/mpeg4") ||
                fileType.equals("video/avi") ||
                fileType.equals("video/x-ms-wmv")) {
//        if (true) {
            // 要上传的目标文件存放的绝对路径
            final String localPath = WEB_BASE_PATH + "video/";
            //获取文件名
            String fileName = file.getOriginalFilename();
            //获取文件后缀名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            String result = UploadFileUtils.upload(file, localPath, fileName, false);
            while (result.equals("exists")){
                //重新生成文件名
                fileName = UUID.randomUUID() + suffixName;
                result = UploadFileUtils.upload(file, localPath, fileName, false);
            }
            if (result.equals("true")) {
                String relativePath = WEB_HOST + "video/" + fileName;
                root.put("relativePath", relativePath);//前端根据是否存在该字段来判断上传是否成功
                result_msg = "文件上传成功";
            } else {
                result_msg = "文件上传失败";
            }
        } else {
            result_msg = "文件格式不正确";
        }
        root.put("result_msg", result_msg);

        String root_json = JSON.toJSONString(root);
        logger.info(root_json);
        return root;
    }

    //上传文件
//    @ResponseBody
//    @RequestMapping("/upload_file")
//    public Map fileUpload(MultipartFile file, HttpServletRequest request) {
//        String result_msg = "";  //上传结果信息
//        Map<String, Object> root = new HashMap<String, Object>();
//
//        //判断上传文件格式
//        String fileType = file.getContentType();
//        if (fileType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document") ||
//                fileType.equals("application/pdf") ||
//                fileType.equals("application/msword")) {
//            // 要上传的目标文件存放的绝对路径
//            final String localPath = "static\\reportsTemp";
//            //上传后保存的文件名(需要防止图片重名导致的文件覆盖)
//            //获取文件名
//            String fileName = file.getOriginalFilename();
//            //获取文件后缀名
//            String suffixName = fileName.substring(fileName.lastIndexOf("."));
//            String result = UploadFileUtils.upload(file, localPath, fileName, false);
//            while (result.equals("exists")){
//                //重新生成文件名
//                fileName = UUID.randomUUID() + suffixName;
//                result = UploadFileUtils.upload(file, localPath, fileName, false);
//            }
//            if (result.equals("true")) {
//                //文件存放的相对路径(一般存放在数据库用于img标签的src)
//                String relativePath = "static/reportsTemp/" + fileName;
//                root.put("relativePath", relativePath);//前端根据是否存在该字段来判断上传是否成功
//                result_msg = "文件上传成功";
//            } else {
//                result_msg = "文件上传失败";
//            }
//        } else {
//            result_msg = "文件格式不正确";
//        }


//        root.put("result_msg", result_msg);
////        JSON.toJSONString(root,SerializerFeature.DisableCircularReferenceDetect);
//        String root_json = JSON.toJSONString(root);
//        logger.info(root_json);
//        return root;
//    }
}

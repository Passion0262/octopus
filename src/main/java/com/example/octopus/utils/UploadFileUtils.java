package com.example.octopus.utils;

import com.alibaba.fastjson.parser.JSONLexer;
import com.example.octopus.controller.adminController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author: Xu
 * @date: 2021/7/13 12:29
 * 上传文件到指定目录
 */

@Controller
public class UploadFileUtils {

    private Logger logger = LoggerFactory.getLogger(UploadFileUtils.class);


    public static String upload(MultipartFile file, String path, String fileName, boolean cover) {

        //String rootPath = System.getProperty("user.dir");
        //String realPath = rootPath + "\\src\\main\\resources\\" + path + "\\" + fileName;

        String realPath = path + fileName;
        File dest = new File(realPath);

        //判断文件父目录是否存在覆盖
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdir();
        }

        if(!cover){
            if (dest.exists()) {
                return "exists";
            }
        }

        try {
            //保存文件
            file.transferTo(dest);
            return "true";
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "false";
        }

    }


}
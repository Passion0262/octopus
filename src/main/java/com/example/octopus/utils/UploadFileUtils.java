package com.example.octopus.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author: Xu
 * @date: 2021/6/6 11:00
 * 上传文件到指定目录
 */

public class UploadFileUtils {

    public static String upload(MultipartFile file, String path, String fileName, boolean cover) {
        String rootPath = System.getProperty("user.dir");

        //确定上传的文件名
        String realPath = rootPath + "\\src\\main\\resources\\" + path + "\\" + fileName;
        //System.out.println("上传文件：" + realPath);

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
package com.example.octopus.utils;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author: Xu
 * @date: 2021/7/8 21:51
 * 将时间（单位：秒）转成时分秒格式
 */

public class TimeTransUtils {
    public static String timeTrans(int s){

        String ss = "";
        if(s>=3600){
            ss = ss + Math.round(s/3600) +"时";
            s = s%3600;
        }
        if(s>=60){
            ss = ss + Math.round(s/60) +"分";
            s = s%60;
        }
        if(s<60){
            ss = ss + s +"秒";
        }
        return ss;

    }

}

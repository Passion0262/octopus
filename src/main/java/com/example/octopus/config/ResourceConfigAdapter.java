//package com.example.octopus.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
///**
// * @author: Xu
// * @date: 2021/6/22 16:45
// * 虚拟路径映射访问
// */
//
//@Configuration
//public class ResourceConfigAdapter extends WebMvcConfigurerAdapter {
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //在windows环境下的存放资源路径
//        //String winPath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images\\";
//        //String winPath = "D:\\platform\\";
//        //在Linux环境下的图片存放资源路径
////        String linuxPath = "/usr/local/my_project/images/";
//        String os = System.getProperty("os.name");
//        if (os.toLowerCase().startsWith("win")) {  //windows系统
//            //第一个方法设置访问路径前缀，第二个方法设置资源路径
//            registry.addResourceHandler("/img/**").
//                    addResourceLocations("file:D:/platform/img/");
//        }else{//linux系统
////            registry.addResourceHandler("/images/**").
////                    addResourceLocations("file:"+linuxPath);
//        }
//        super.addResourceHandlers(registry);
//    }
//}
//

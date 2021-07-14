package com.example.octopus;

import com.example.octopus.servlet.VerifyServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement  //开始事务管理器（数据库）
public class OctopusApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(OctopusApplication.class, args);
    }

    /**
     * 注入验证码servlet，如果不要验证码则注释。
     */
    @Bean
    public ServletRegistrationBean indexServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new VerifyServlet());
        registration.addUrlMappings("/getVerifyCode");
        return registration;
    }

    @Override
    protected SpringApplicationBuilder configure (SpringApplicationBuilder builder){
        return builder.sources(OctopusApplication.class);
    }

}

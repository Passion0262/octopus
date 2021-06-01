package com.example.octopus;

import com.example.octopus.servlet.VerifyServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OctopusApplication {

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
}

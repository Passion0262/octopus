package com.example.octopus.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ：shadow
 * @date ：Created in 2021/6/1 1:46 下午
 * @modified By：
 */
@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);
//    @RequestMapping("/login/error")
//    public void loginError(HttpServletRequest request, HttpServletResponse response) {
//        response.setContentType("text/html;charset=utf-8");
//        AuthenticationException exception =
//                (AuthenticationException)request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
//        try {
//            response.getWriter().write(exception.toString());
//        }catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
        //重置密码
        @PostMapping("/reset_pwd_confirm")
        public String reset_pwd_confirm(HttpServletRequest request) {
            String username = request.getParameter("username");
            String phone = request.getParameter("phone");
            String new_pwd = request.getParameter("new_pwd");
            logger.info("用户名：" + username);
            logger.info("手机号码：" + phone);
            logger.info("新密码：" + new_pwd);
            //重置密码
            return "reset_pwd";
        }

        @RequestMapping("/reset_pwd")
        public String reset_pwd() {

            return "reset_pwd";
        }

}

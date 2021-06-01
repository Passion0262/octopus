package com.example.octopus.controller;

import org.springframework.stereotype.Controller;
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

}

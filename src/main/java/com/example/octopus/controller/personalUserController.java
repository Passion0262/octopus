package com.example.octopus.controller;

import com.example.octopus.service.SysUserRoleService;
import com.example.octopus.service.personal.CategoryService;
import com.example.octopus.service.personal.PersonalPlanService;
import com.example.octopus.service.personal.PersonalUserService;
import com.example.octopus.service.personal.PlanService;
import com.example.octopus.utils.CookieTokenUtils;
import com.example.octopus.utils.PropertiesUtil;
import com.example.octopus.utils.TokenCheckUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

/**
 * 个人用户前台controller
 * @author: Hao
 * @date: 2022/1/11 20:18
 */
@Controller
public class personalUserController {
	private Logger logger = LoggerFactory.getLogger(adminController.class);

	private PropertiesUtil propertiesUtil = new PropertiesUtil();
	private String WEB_BASE_PATH = propertiesUtil.getFileSavePath();
	private String WEB_HOST = "/static/";


	@Autowired
	SysUserRoleService sysUserRoleService;

	@Autowired
	PlanService planService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	PersonalUserService personalUserService;

	@Autowired
	PersonalPlanService personalPlanService;

	private final static String COOKIE_NAME = "cookiePersonalUser";

	private CookieTokenUtils cookieThings = new CookieTokenUtils();


	private boolean cookieCheck(Model model, HttpServletRequest request) {
		// 检查cookie合法性
		TokenCheckUtils tokenCheck = cookieThings.validateToken(request, COOKIE_NAME);
		if (tokenCheck.isSuccess()) {
			long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
			int role_id = sysUserRoleService.getRoleIdByUserId(user);
			model.addAttribute("username", tokenCheck.getUserName());
			if (role_id == 4) {
				model.addAttribute("role", "personal_user");
				return true;
			} else
				return false;
		} else {
			logger.info(tokenCheck.getErrorType() + "  需要重新登录!");
			return false;
		}
	}


}

package com.example.octopus.controller;

import com.alibaba.fastjson.JSON;
import com.example.octopus.entity.personal.PersonalUser;
import com.example.octopus.service.*;
import com.example.octopus.service.personal.*;
import com.example.octopus.utils.CookieTokenUtils;
import com.example.octopus.utils.PropertiesUtil;
import com.example.octopus.utils.TokenCheckUtils;
//import com.example.octopus.utils.TimeTransUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
public class personalAdminController {

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

	private final static String COOKIE_NAME = "cookiePersonalAdmin";

	private CookieTokenUtils cookieThings = new CookieTokenUtils();


	private boolean cookieCheck(Model model, HttpServletRequest request) {
		// 检查cookie合法性
		TokenCheckUtils tokenCheck = cookieThings.validateToken(request, COOKIE_NAME);
		if (tokenCheck.isSuccess()) {
			long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
			int role_id = sysUserRoleService.getRoleIdByUserId(user);  // 获取角色，管理员还是教师
			model.addAttribute("username", tokenCheck.getUserName());
			if (role_id == 5) {
				model.addAttribute("role", "personal_admin");
				return true;
			} else
				return false;
		} else {
			logger.info(tokenCheck.getErrorType() + "  需要重新登录!");
			return false;
		}
	}

	//首页 -- 个人用户
	@RequestMapping("/admin_index_for_personal")
	public String admin_personal_index(HttpServletRequest request, Model model) {
		String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!user_id.equals(cookieThings.getCookieUserNum(request, COOKIE_NAME))) return "redirect:/login";

		if (!cookieCheck(model, request)) return "redirect:/login";

		long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		int role_id = sysUserRoleService.getRoleIdByUserId(user);  // 获取角色，管理员还是教师

		if (role_id == 5) {
			try {
				model.addAttribute("number_of_personal_user", personalUserService.countAllPersonalUser());
				model.addAttribute("number_of_active_user", personalUserService.countActivatePersonalUser());
				model.addAttribute("number_of_plan", planService.listAllPlan().size());
				model.addAttribute("number_of_category", categoryService.listAllCategory().size());
				return "admin_index_for_personal";
			} catch (Exception e) {
				return "redirect:/admin_error";
			}
		} else {
			return "admin_login";
		}
	}

	//用户管理 -- 个人用户
	@RequestMapping("/admin_personal_info")
	public String admin_personal_info(HttpServletRequest request, Model model) {
		String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!user_id.equals(cookieThings.getCookieUserNum(request, COOKIE_NAME))) return "redirect:/login";

		if (!cookieCheck(model, request)) return "redirect:/login";

		long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		int role_id = sysUserRoleService.getRoleIdByUserId(user);  // 获取角色，管理员还是教师

		if (role_id == 5) {
			try {
				model.addAttribute("user", personalUserService.listAllPersonalUserInfo());
				return "admin_personal_info";
			} catch (Exception e) {
				return "redirect:/admin_error";
			}
		} else {
			return "admin_login";
		}
	}

	// 增加个人用户
	@GetMapping("/admin_personal_add")
	public ModelAndView admin_personal_add(HttpServletRequest request, Model model) {
		if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

		long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		int role_id = sysUserRoleService.getRoleIdByUserId(user);  // 获取角色

		if (role_id == 5) {
			logger.info("管理员{}进入admin_personal_add，获取一个新Personal()", user);
			try {
				model.addAttribute("personal", new PersonalUser());
				return new ModelAndView("admin_personal_add", "permodel", model);
			} catch (Exception e) {
				return new ModelAndView("redirect:/admin_error");
			}
		} else {
			return new ModelAndView("redirect:/login");
		}
	}

	@PostMapping("/add_personal")
	public ModelAndView add_personal(PersonalUser personalUser, HttpServletRequest request, Model model) {
		if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

		long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		int role_id = sysUserRoleService.getRoleIdByUserId(user);  // 获取角色

		if (role_id == 5) {
			logger.info("管理员{}提交新增的个人用户: [{}]", user, personalUser);
			try {
				personalUserService.insertPersonalUser(personalUser);
				return new ModelAndView("redirect:/admin_personal_info");
			} catch (Exception e) {
				return new ModelAndView("redirect:/admin_error");
			}
		} else {
			return new ModelAndView("redirect:/login");
		}
	}

	// 修改个人用户
	@GetMapping("/admin_personal_edit")
	public ModelAndView admin_personal_edit(HttpServletRequest request, Model model) {
		if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

		long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		int role_id = sysUserRoleService.getRoleIdByUserId(user);  // 获取角色

		if (role_id == 5) {
			long personalTel = Long.parseLong(request.getParameter("personalTel"));
			logger.info("管理员{}进入admin_personal_edit，获取PersonalUser, personalTel={}", user, personalTel);
			try {
				PersonalUser personalUser = personalUserService.getPersonalUser(personalTel);
				model.addAttribute("personalUser", personalUser);
				return new ModelAndView("admin_personal_edit", "permodel", model);
			} catch (Exception e) {
				return new ModelAndView("redirect:/admin_error");
			}
		} else {
			return new ModelAndView("redirect:/login");
		}
	}

	@PostMapping("/edit_personal")
	public ModelAndView edit_personal(PersonalUser personalUser, HttpServletRequest request, Model model) {
		if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

		long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		int role_id = sysUserRoleService.getRoleIdByUserId(user);  // 获取角色

		if (role_id == 5) {
			logger.info("管理员{}提交修改的personal: [{}]", user, personalUser);
			try {
				personalUserService.updatePersonalUser(personalUser);
				return new ModelAndView("redirect:/admin_personal_info");
			} catch (Exception e) {
				return new ModelAndView("redirect:/admin_error");
			}
		} else {
			return new ModelAndView("redirect:/login");
		}
	}

	// 个人用户套餐详情
	@RequestMapping("/admin_plan_for_personal/{personalTel}")
	public String admin_plan_for_personal(@PathVariable(value = "personalTel") Long personalTel, HttpServletRequest request, Model model) {
		String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!user_id.equals(cookieThings.getCookieUserNum(request, COOKIE_NAME))) return "redirect:/login";

		if (!cookieCheck(model, request)) return "redirect:/login";

		long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		int role_id = sysUserRoleService.getRoleIdByUserId(user);  // 获取角色，管理员还是教师

		if (role_id == 5) {
			try {
				model.addAttribute("user", personalUserService.getPersonalUser(personalTel));
				//			model.addAttribute("plans", personalPlanService.getPersonalPlanByPersonal(personalTel));
				return "admin_plan_for_personal";
			} catch (Exception e) {
				return "redirect:/admin_error";
			}
		} else {
			return "admin_login";
		}
	}

	//套餐管理 -- 个人用户
	@RequestMapping("/admin_plan")
	public String admin_plan(HttpServletRequest request, Model model) {
		String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!user_id.equals(cookieThings.getCookieUserNum(request, COOKIE_NAME))) return "redirect:/login";

		if (!cookieCheck(model, request)) return "redirect:/login";

		long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		int role_id = sysUserRoleService.getRoleIdByUserId(user);  // 获取角色，管理员还是教师

		if (role_id == 5) {
			try {
				model.addAttribute("plans", planService.listAllPlan());
				return "admin_plan";
			} catch (Exception e) {
				return "redirect:/admin_error";
			}
		} else {
			return "admin_login";
		}
	}

}
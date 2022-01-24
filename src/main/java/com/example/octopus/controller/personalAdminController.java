package com.example.octopus.controller;

import com.alibaba.fastjson.JSON;
import com.example.octopus.entity.user.*;
import com.example.octopus.entity.personal.*;
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
import java.lang.reflect.Array;
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

	@Autowired
	PlanCategoryService planCategoryService;

	@Autowired
	CourseStaticService courseStaticService;

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
		int role_id = sysUserRoleService.getRoleIdByUserId(user);

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
			return "redirect:/login";
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
			return "redirect:/login";
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
				PersonalUser personalUser = new PersonalUser();
				//System.out.println(personalUser);
				model.addAttribute("personal", personalUser);
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
			String personalTel = request.getParameter("personalTel");
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
	@RequestMapping("/admin_plan_for_personal")
	public ModelAndView admin_plan_for_personal(HttpServletRequest request, Model model) {
		String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!user_id.equals(cookieThings.getCookieUserNum(request, COOKIE_NAME))) return new ModelAndView("redirect:/login");

		if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

		long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		int role_id = sysUserRoleService.getRoleIdByUserId(user);  // 获取角色，管理员还是教师

		if (role_id == 5) {
			try {
				String personalTel = request.getParameter("personalTel");
				model.addAttribute("user", personalUserService.getPersonalUser(personalTel));
				model.addAttribute("plans", personalPlanService.listPersonalPlanByTel(personalTel));
				return new ModelAndView("admin_plan_for_personal", "model", model);
			} catch (Exception e) {
				return new ModelAndView("redirect:/admin_error");
			}
		} else {
			return new ModelAndView("redirect:/login");
		}
	}

	//套餐管理
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
			return "redirect:/login";
		}
	}

	// 获取套餐详情
	@GetMapping("/admin_plan_detail")
	public ModelAndView admin_plan_detail(HttpServletRequest request, Model model) {
		if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

		long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		int role_id = sysUserRoleService.getRoleIdByUserId(user);  // 获取角色

		if (role_id == 5) {
			long id = Long.parseLong(request.getParameter("id"));
			logger.info("管理员{}进入admin_plan_detail，获取Plan, id={}", user, id);
			try {
				Plan plan = planService.getPlanById(id);
//				plan.setCategories(categoryService.listAllCategory());
				model.addAttribute("plan", plan);
//				System.out.println(plan);
				return new ModelAndView("admin_plan_detail", "planmodel", model);
			} catch (Exception e) {
				return new ModelAndView("redirect:/admin_error");
			}
		} else {
			return new ModelAndView("redirect:/login");
		}
	}

	// 增加套餐
	@GetMapping("/admin_plan_add")
	public ModelAndView admin_plan_add(HttpServletRequest request, Model model) {
		if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

		long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		int role_id = sysUserRoleService.getRoleIdByUserId(user);  // 获取角色

		if (role_id == 5) {
			logger.info("管理员{}进入admin_plan_add，获取一个新Plan()", user);
			try {
				model.addAttribute("plan", new Plan());
				model.addAttribute("category", categoryService.listAllCategory());
				return new ModelAndView("admin_plan_add", "planmodel", model);
			} catch (Exception e) {
				return new ModelAndView("redirect:/admin_error");
			}
		} else {
			return new ModelAndView("redirect:/login");
		}
	}

	@PostMapping("/add_plan")
	public ModelAndView add_plan(Plan plan, HttpServletRequest request, Model model) {
		if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

		long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		int role_id = sysUserRoleService.getRoleIdByUserId(user);  // 获取角色

		if (role_id == 5) {
			logger.info("管理员{}提交新增的套餐: [{}]", user, plan);
			try {
				plan.setCategoryIds(plan.getCategoryIds().replace(',', ';'));
				planService.insertPlan(plan);
				return new ModelAndView("redirect:/admin_plan");
			} catch (Exception e) {
				return new ModelAndView("redirect:/admin_error");
			}
		} else {
			return new ModelAndView("redirect:/login");
		}
	}

	// 修改套餐
	@GetMapping("/admin_plan_edit")
	public ModelAndView admin_plan_edit(HttpServletRequest request, Model model) {
		if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

		long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		int role_id = sysUserRoleService.getRoleIdByUserId(user);  // 获取角色

		if (role_id == 5) {
			long id = Long.parseLong(request.getParameter("id"));
			logger.info("管理员{}进入admin_plan_edit，获取Plan, id={}", user, id);
			try {
				Plan plan = planService.getPlanById(id);
				//List<String> categoryNames = Arrays.asList(plan.getCategoryNames().split(";"));
//				String[] arr = new String[] {"算法工程师"};
//				List<String> categoryNames = Arrays.asList(arr);
				model.addAttribute("plan", plan);
				//List<Category> category_list = categoryService.listAllCategory();
//				for (int i=0; i<category_list.size(); i++){
//					if (categoryNames.contains(category_list.get(i).getName())){
//						category_list.get(i).setBrief("true");
//					}else{
//						category_list.get(i).setBrief("false");
//					}
//					System.out.println(category_list.get(i));
//				}
//				model.addAttribute("category", category_list);

				return new ModelAndView("admin_plan_edit", "planmodel", model);
			} catch (Exception e) {
				return new ModelAndView("redirect:/admin_error");
			}
		} else {
			return new ModelAndView("redirect:/login");
		}
	}

	@PostMapping("/edit_plan")
	public ModelAndView edit_plan(Plan plan, HttpServletRequest request, Model model) {
		if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

		long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		int role_id = sysUserRoleService.getRoleIdByUserId(user);  // 获取角色

		if (role_id == 5) {
			logger.info("管理员{}提交修改的plan: [{}]", user, plan);
			try {
				planService.updatePlan(plan);
				return new ModelAndView("redirect:/admin_plan");
			} catch (Exception e) {
				return new ModelAndView("redirect:/admin_error");
			}
		} else {
			return new ModelAndView("redirect:/login");
		}
	}

	//课程类别管理
	@RequestMapping("/admin_category")
	public String admin_category(HttpServletRequest request, Model model) {
		String user_id = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!user_id.equals(cookieThings.getCookieUserNum(request, COOKIE_NAME))) return "redirect:/login";

		if (!cookieCheck(model, request)) return "redirect:/login";

		long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		int role_id = sysUserRoleService.getRoleIdByUserId(user);  // 获取角色，管理员还是教师

		if (role_id == 5) {
			try {
				model.addAttribute("categories", categoryService.listAllCategory());
				return "admin_category";
			} catch (Exception e) {
				return "redirect:/admin_error";
			}
		} else {
			return "redirect:/login";
		}
	}

	// 增加课程类别
	@GetMapping("/admin_category_add")
	public ModelAndView admin_category_add(HttpServletRequest request, Model model) {
		if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

		long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		int role_id = sysUserRoleService.getRoleIdByUserId(user);  // 获取角色

		if (role_id == 5) {
			logger.info("管理员{}进入admin_category_add，获取一个新Category()", user);
			try {
				model.addAttribute("category", new Category());
				model.addAttribute("course", courseStaticService.listAllCourseStatic());
				return new ModelAndView("admin_category_add", "categorymodel", model);
			} catch (Exception e) {
				return new ModelAndView("redirect:/admin_error");
			}
		} else {
			return new ModelAndView("redirect:/login");
		}
	}

	@PostMapping("/add_category")
	public ModelAndView add_category(Category category, HttpServletRequest request, Model model) {
		if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

		long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		int role_id = sysUserRoleService.getRoleIdByUserId(user);  // 获取角色

		if (role_id == 5) {
			logger.info("管理员{}提交新增的课程类别: [{}]", user, category);
			try {
//				System.out.println(category);
				category.setStaticCourseIds(category.getStaticCourseIds().replace(',', ';'));
				categoryService.insertCategory(category);
				return new ModelAndView("redirect:/admin_category");
			} catch (Exception e) {
				return new ModelAndView("redirect:/admin_error");
			}
		} else {
			return new ModelAndView("redirect:/login");
		}
	}

	// 修改课程类别
	@GetMapping("/admin_category_edit")
	public ModelAndView admin_category_edit(HttpServletRequest request, Model model) {
		if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

		long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		int role_id = sysUserRoleService.getRoleIdByUserId(user);  // 获取角色

		if (role_id == 5) {
			long id = Long.parseLong(request.getParameter("id"));
			logger.info("管理员{}进入admin_category_edit，获取category, id={}", user, id);
			try {
				Category category = categoryService.getCategoryById(id);
				model.addAttribute("category", category);
				return new ModelAndView("admin_category_edit", "categorymodel", model);
			} catch (Exception e) {
				return new ModelAndView("redirect:/admin_error");
			}
		} else {
			return new ModelAndView("redirect:/login");
		}
	}

	@PostMapping("/edit_category")
	public ModelAndView edit_category(Category category, HttpServletRequest request, Model model) {
		if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

		long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		int role_id = sysUserRoleService.getRoleIdByUserId(user);  // 获取角色

		if (role_id == 5) {
			logger.info("管理员{}提交修改的category: [{}]", user, category);
			try {
				categoryService.updateCategory(category);
				return new ModelAndView("redirect:/admin_category");
			} catch (Exception e) {
				return new ModelAndView("redirect:/admin_error");
			}
		} else {
			return new ModelAndView("redirect:/login");
		}
	}


	//删除课程类别
	@RequestMapping("/admin_category_delete")
	public ModelAndView admin_category_delete(HttpServletRequest request, Model model) {
		if (!cookieCheck(model, request)) return new ModelAndView("redirect:/login");

		long user = Long.parseLong(cookieThings.getCookieUserNum(request, COOKIE_NAME));
		int role_id = sysUserRoleService.getRoleIdByUserId(user);  // 获取角色

		if (role_id == 5) {
			long id = Long.parseLong(request.getParameter("id"));
			logger.info("管理员{}删除课程类别[{}]", user, id);
			try {
				categoryService.deleteCategoryById(id);
				return new ModelAndView("redirect:/admin_category");
			} catch (Exception e) {
				return new ModelAndView("redirect:/admin_error");
			}
		} else {
			return new ModelAndView("redirect:/login");
		}
	}

}
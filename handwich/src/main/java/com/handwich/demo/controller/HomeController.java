package com.handwich.demo.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	

	@RequestMapping(value = {"/","home"}, method = RequestMethod.GET)
	public String home() {
		
		return "home";
	}
	@RequestMapping(value="information")
	public String infotmationpage() {
		return "information/company_info";
	}
	@RequestMapping(value="business")
	public String businesspage() {
		return "business/business_info";
	}
	@RequestMapping(value="service")
	public String servicepage() {
		return "service/service_page";
	}
	
	
	// 회사소개
	@RequestMapping(value="overview")
	public String overviewpage() {
		return "information/company_overview";
	}
	@RequestMapping(value="history")
	public String historypage() {
		return "information/company_history";
	}
	@RequestMapping(value="rank")
	public String rankpage() {
		return "information/company_rank";
	}
	
	
	/*  유저관리     */
	@RequestMapping(value="signup")
	public String siguppage() {
		return "user/signup";
	}
	@RequestMapping(value="signin")
	public String siginpage() {
		return "user/signin";
	}

}	
package com.topnews.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.topnews.dataLoad.DataLoader;

@Controller
public class ErrorController {
	
	@RequestMapping(value = "/*", method = RequestMethod.GET)
	public String home(Model model, HttpSession httpSession) {
		try {
			DataLoader.LoadSiteData(httpSession, model);
			model.addAttribute("message", "notFoundPage");
			return "forward:/Error";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/";
		}
		
	}

	@RequestMapping(value = "/Error", method = RequestMethod.GET)
	public String error(Model model, HttpSession httpSession) {
		try {
			DataLoader.LoadSiteData(httpSession, model);
			model.addAttribute("message", "notFoundPage");
			return "error-404";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/";		}

	}
}

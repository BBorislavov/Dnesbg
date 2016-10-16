package com.topnews.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LanguageController {
	
	@RequestMapping(value = "/Language", method = RequestMethod.GET)
	public String language(HttpServletRequest request) {
		if (request.getHeader("Referer") != null) {
			String referer = request.getHeader("Referer");
			return "redirect:" + referer;
		} else {
			return "redirect:/";
		}
	}
}

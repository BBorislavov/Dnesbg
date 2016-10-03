package com.topnews.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LogoutController {

	@RequestMapping(value = "/Logout", method = RequestMethod.GET)
	public String login(HttpSession httpSession) {
		try {
				httpSession.invalidate();
					return "redirect:/";
		} catch (Exception e) {
			return "login";
		}
	}
	
}

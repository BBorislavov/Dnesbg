package com.topnews.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.topnews.models.User;
import com.topnews.modelsDAO.UserDAO;

@Controller
public class LoginController {

	@RequestMapping(value = "/Login", method = RequestMethod.POST)
	public String login(@ModelAttribute User user, Model model, HttpSession httpSession) {
		try {
			if (UserDAO.isUserExisting(user)) {
				httpSession.setAttribute("user", user);
				if (UserDAO.isAdmin(user)) {
					return "redirect:/AdminPanel";
				} else {
					return "LoginSuccess";
				}
			}
			model.addAttribute("message", "Invalid username or password");
			return "login";
		} catch (Exception e) {
			model.addAttribute("message", "Invalid username or password");
			return "login";
		}
	}

	@RequestMapping(value = "/Login", method = RequestMethod.GET)
	public String showLogged(Model model) {
		model.addAttribute(new User());
		return "login";
	}

}
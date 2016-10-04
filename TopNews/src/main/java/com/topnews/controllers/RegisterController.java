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
@RequestMapping("/Register")
public class RegisterController {

	@RequestMapping(method = RequestMethod.POST)
	public String register(@ModelAttribute User user, Model model, HttpSession httpSession) {
		try {
			if (UserDAO.isUserExisting(user)) {
				model.addAttribute("message", "This account already exists");
				return "register";
			} else {
				if (UserDAO.registerUser(user)) {
					httpSession.setAttribute("user", user);
					return "LoginSuccess";
				}
			}
			model.addAttribute("message", "Invalid register");
			return "register";
		} catch (Exception e) {
			model.addAttribute("message", "Invalid register");
			return "register";
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showLogged(Model model) {
		model.addAttribute(new User());
		return "register";
	}
}

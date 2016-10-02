package com.topnews.controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.UserException;
import com.topnews.models.User;
import com.topnews.modelsDAO.UserDAO;

@Controller
public class LoginController {

	@RequestMapping(value = "/Login", method = RequestMethod.POST)
	public String login(@Valid @ModelAttribute User user, HttpSession httpSession, BindingResult result) {
		try {
			if (result.hasErrors()) {
				return "login";
			}
			if (UserDAO.isUserExisting(user)) {
				httpSession.setAttribute("user", user);
				if (UserDAO.isAdmin(user)) {
					return "admin_panel";
				} else {
					return "LoginSuccess";
				}
			}
			return "LoginFailed";
		} catch (ConnectionException e) {
			return "redirect:/pages/404.html";
		} catch (UserException e) {
			System.out.println("4");
			return "LoginFailed";
		}
	}

	@RequestMapping(value = "/Login", method = RequestMethod.GET)
	public String showLogged(Model model) {
		model.addAttribute(new User());
		return "login";
	}

}

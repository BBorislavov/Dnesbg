package com.topnews.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.topnews.dataLoad.DataLoader;
import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.UserException;
import com.topnews.models.User;
import com.topnews.modelsDAO.UserDAO;

@Controller
@RequestMapping("/Register")
public class RegisterController {

	@RequestMapping(method = RequestMethod.POST)
	public String register(@ModelAttribute User user, Model model, HttpSession httpSession) {
		try {
			DataLoader.LoadSiteData(httpSession, model);
			if (UserDAO.isUserExisting(user)) {
				model.addAttribute("message", "accountExists");
				return "register";
			} else {
				if (UserDAO.registerUser(user)) {
					httpSession.setAttribute("user", user);
					return "redirect:./";
				}
				model.addAttribute("message", "registerFailed");
				return "register";
			}
		} catch (UserException e) {
			e.printStackTrace();
			model.addAttribute("message", "registerFailed");
			return "forward:/Register";
		} catch (ConnectionException e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showLogged(Model model, HttpSession httpSession) {
		try {
			model.addAttribute(new User());
			DataLoader.LoadSiteData(httpSession, model);
			return "register";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		}

	}
}

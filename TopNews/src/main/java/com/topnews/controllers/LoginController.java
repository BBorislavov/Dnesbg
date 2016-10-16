package com.topnews.controllers;

import javax.servlet.http.HttpServletRequest;
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
public class LoginController {

	private static final String SERVER_LOGIN_PATH = "http://localhost:8080/TopNews/Login";
	private static final int FIFTHEEN_MINUTES = 60 * 15;

	@RequestMapping(value = "/Login", method = RequestMethod.POST)
	public String login(@ModelAttribute User user, Model model, HttpSession httpSession) {
		try {
			DataLoader.LoadSiteData(httpSession, model);
			if (user.getUsername() != null && user.getPassword() != null) {
				if (UserDAO.isUsernameExisting(user)) {
					if (UserDAO.isUserExisting(user)) {
						httpSession.setAttribute("user", user);
						httpSession.setAttribute("username", user.getUsername());
						if (UserDAO.isAdmin(user)) {
							httpSession.setAttribute("isAdmin", UserDAO.isAdmin(user));
						}
						httpSession.setMaxInactiveInterval(FIFTHEEN_MINUTES);
						if ((String) httpSession.getAttribute("referer") != null) {
							String referer = (String) httpSession.getAttribute("referer");
							if (!referer.equals(SERVER_LOGIN_PATH)) {
								return "redirect:" + referer;
							} else {
								return "redirect:/";
							}
						} else {
							return "redirect:/";
						}
					} else {
						model.addAttribute("message", "invalidPassword");
						return "login";
					}
				}
				model.addAttribute("message", "invalidUsername");
				return "login";
			}
			model.addAttribute("message", "emptyFields");
			return "login";
		} catch (UserException e) {
			e.printStackTrace();
			model.addAttribute("message", "invalidLogin");
			return "redirect:/Login";
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

	@RequestMapping(value = "/Login", method = RequestMethod.GET)
	public String showLogged(Model model, HttpServletRequest request, HttpSession httpSession) {
		model.addAttribute(new User());
		String referer = request.getHeader("Referer");
		httpSession.setAttribute("referer", referer);
		try {
			DataLoader.LoadSiteData(httpSession, model);
			return "login";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		}
	}
}

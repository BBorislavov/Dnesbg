package com.topnews.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.NewsException;
import com.topnews.models.INews;
import com.topnews.models.User;
import com.topnews.modelsDAO.CategoryDAO;
import com.topnews.modelsDAO.NewsDAO;
import com.topnews.modelsDAO.UserDAO;

@Controller
public class LoginController {

	private static final String SERVER_LOGIN_PATH = "http://localhost:8080/TopNews/Login";
	private static final int FIFTHEEN_MINUTES = 60 * 15;

	@RequestMapping(value = "/Login", method = RequestMethod.POST)
	public String login(@ModelAttribute User user, Model model, HttpSession httpSession) {
		try {
			List<INews> allNews = NewsDAO.showAllNews();
			List<INews> popularNews = NewsDAO.showAllNews("rating");
			List<INews> latestNews = NewsDAO.showAllNews("date");
			model.addAttribute("latestNews", latestNews);
			model.addAttribute("popularNews", popularNews);
			model.addAttribute("allNews", allNews);
			Map<String, List<String>> allCategories = CategoryDAO.AllCategories();
			model.addAttribute("allCategories", allCategories);
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
						httpSession.setAttribute("message", "invalidPassword");
						return "login";
					}

				}
				httpSession.setAttribute("message", "invalidUsername");
				return "login";
			}
			httpSession.setAttribute("message", "emptyFields");
			return "login";
		} catch (Exception e) {
			e.printStackTrace();
			httpSession.setAttribute("message", "invalidLogin");
			return "redirect:/Login";
		}

	}

	@RequestMapping(value = "/Login", method = RequestMethod.GET)
	public String showLogged(Model model, HttpServletRequest request, HttpSession httpSession) {
		model.addAttribute(new User());
		String referer = request.getHeader("Referer");
		httpSession.setAttribute("referer", referer);
		httpSession.removeAttribute("message");
		try {
			List<INews> allNews = NewsDAO.showAllNews();
			List<INews> popularNews = NewsDAO.showAllNews("rating");
			List<INews> latestNews = NewsDAO.showAllNews("date");
			model.addAttribute("latestNews", latestNews);
			model.addAttribute("popularNews", popularNews);
			model.addAttribute("allNews", allNews);
			Map<String, List<String>> allCategories = CategoryDAO.AllCategories();
			model.addAttribute("allCategories", allCategories);
			return "login";
		} catch (ConnectionException e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "redirect:/Login";
		} catch (NewsException e) {
			e.printStackTrace();
			httpSession.setAttribute("message", "serverMaintenance");
			return "forward:/Error";			
		} catch (Exception e) {
			e.printStackTrace();
			httpSession.setAttribute("message", "serverMaintenance");
			return "forward:/Error";		}

		
	}

	
	
	

}

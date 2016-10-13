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

	private static final int FIFTHEEN_MINUTES = 60 * 15;

	@RequestMapping(value = "/Login", method = RequestMethod.POST)
	public String login(@ModelAttribute User user, Model model, HttpSession httpSession) {
		try {
			if (user.getUsername() != null && user.getPassword() != null) {
				if (UserDAO.isUserExisting(user)) {
					httpSession.setAttribute("user", user);
					httpSession.setAttribute("username", user.getUsername());
					if (UserDAO.isAdmin(user)) {
						httpSession.setAttribute("isAdmin", UserDAO.isAdmin(user));
					}
					httpSession.setMaxInactiveInterval(FIFTHEEN_MINUTES);
					if ((String) httpSession.getAttribute("referer")!=null){
					String referer = (String) httpSession.getAttribute("referer");
					return "redirect:" + referer;
					} else {
						return "/index";
					}
				}
			}
			model.addAttribute("message", "Invalid username or password");
			return "login";
		} catch (Exception e) {
			return "login";
		}
	}

	@RequestMapping(value = "/Login", method = RequestMethod.GET)
	public String showLogged(Model model, HttpServletRequest request, HttpSession session) {
		model.addAttribute(new User());
		String referer = request.getHeader("Referer");
		session.setAttribute("referer", referer);
		return "login";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String homePage(Model model) {
		try {
			List<INews> allNews = NewsDAO.showAllNews();
			List<INews> popularNews = NewsDAO.showAllNews("rating");
			List<INews> latestNews = NewsDAO.showAllNews("date");
			model.addAttribute("latestNews", latestNews);
			model.addAttribute("popularNews", popularNews);
			model.addAttribute("allNews", allNews);
			Map<String, List<String>> allCategories = CategoryDAO.AllCategories();
			model.addAttribute("allCategories", allCategories);
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (NewsException e) {
			e.printStackTrace();
		}
		return "index";
	}

}

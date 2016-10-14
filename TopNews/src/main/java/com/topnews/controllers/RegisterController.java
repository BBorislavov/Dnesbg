package com.topnews.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.NewsException;
import com.topnews.exceptions.UserException;
import com.topnews.models.INews;
import com.topnews.models.User;
import com.topnews.modelsDAO.CategoryDAO;
import com.topnews.modelsDAO.NewsDAO;
import com.topnews.modelsDAO.UserDAO;

@Controller
@RequestMapping("/Register")
public class RegisterController {

	@RequestMapping(method = RequestMethod.POST)
	public String register(@ModelAttribute User user, Model model, HttpSession httpSession) {
		try { 
			List<INews> popularNews = NewsDAO.showAllNews("rating");
			List<INews> latestNews = NewsDAO.showAllNews("date");
			model.addAttribute("latestNews", latestNews);
			model.addAttribute("popularNews", popularNews);
			Map<String, List<String>> allCategories = CategoryDAO.AllCategories();
			model.addAttribute("allCategories", allCategories);
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
			return "forward:/Register";
		} catch (ConnectionException e) {
			e.printStackTrace();
			httpSession.setAttribute("message", "serverMaintenance");
			return "forward:/Error";
		} catch (NewsException e) {
			e.printStackTrace();
			httpSession.setAttribute("message", "serverMaintenance");
			return "forward:/Error";
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showLogged(Model model, HttpSession httpSession) {
		try {
			model.addAttribute(new User());
			List<INews> popularNews = NewsDAO.showAllNews("rating");
			List<INews> latestNews = NewsDAO.showAllNews("date");
			model.addAttribute("latestNews", latestNews);
			model.addAttribute("popularNews", popularNews);
			Map<String, List<String>> allCategories = CategoryDAO.AllCategories();
			model.addAttribute("allCategories", allCategories);
			return "register";
		} catch (ConnectionException e) {
			e.printStackTrace();
			httpSession.setAttribute("message", "serverMaintenance");
			return "forward:/Error";
		} catch (NewsException e) {
			e.printStackTrace();
			httpSession.setAttribute("message", "serverMaintenance");
			return "forward:/Error";
		}
		
	}
}

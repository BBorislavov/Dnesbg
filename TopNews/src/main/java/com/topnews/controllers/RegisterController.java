package com.topnews.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

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
@RequestMapping("/Register")
public class RegisterController {

	@RequestMapping(method = RequestMethod.POST)
	public String register(@Valid @ModelAttribute User user, Model model, HttpSession httpSession) {
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
		} catch (Exception e) {
			e.printStackTrace();
			return "register";
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showLogged(Model model) {
		model.addAttribute(new User());
		List<INews> popularNews;
		try {
			popularNews = NewsDAO.showAllNews("rating");
			List<INews> latestNews = NewsDAO.showAllNews("date");
			model.addAttribute("latestNews", latestNews);
			model.addAttribute("popularNews", popularNews);
			Map<String, List<String>> allCategories = CategoryDAO.AllCategories();
			model.addAttribute("allCategories", allCategories);
			
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NewsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "register";
	}
}

package com.topnews.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.topnews.models.News;
import com.topnews.models.User;
import com.topnews.modelsDAO.AdminDAO;
import com.topnews.modelsDAO.UserDAO;

@Controller
public class AdminController {

	@RequestMapping(value = "/AdminPanel", method = RequestMethod.GET)
	public String showNewsInCategory(Model model, HttpSession httpSession, HttpServletRequest req) {
		try {
			if (UserDAO.isAdmin((User) httpSession.getAttribute("user"))) {
				model.addAttribute("user", httpSession.getAttribute("user"));
				return "admin_panel";
			} else {
				return "redirect:/pages/404.html";
			}

		} catch (Exception e) {
			return "forward:/Login";
		}
	}

	@RequestMapping(value = "/Category", method = RequestMethod.GET)
	public String showNewsInCategory(@ModelAttribute("name") String name, Model model, HttpSession httpSession,
			HttpServletRequest req) {
		try {
			if (UserDAO.isAdmin((User) httpSession.getAttribute("user"))) {
				model.addAttribute("name", name);
				List<News> news = UserDAO.showNewsInSubcategory(name);
				model.addAttribute("news", news);
				return "ShowCategory";
			} else {
				return "redirect:/pages/404.html";
			}

		} catch (Exception e) {
			return "forward:/Login";
		}
	}

	@RequestMapping(value = "/News", method = RequestMethod.GET)
	public String showCurrentNews(@ModelAttribute("category") String category, @ModelAttribute("id") int id, Model model,
			HttpSession httpSession) {
		try {
			if (UserDAO.isAdmin((User) httpSession.getAttribute("user"))) {
				model.addAttribute("id", id);
				model.addAttribute("category", category);
				News news = UserDAO.showCurrentNews(id);
				model.addAttribute("news", news);
				return "showCurrent";
			} else {
				return "redirect:/pages/404.html";
			}

		} catch (Exception e) {
			return "forward:/Login";
		}
	}

	@RequestMapping(value = "/AddNews", method = RequestMethod.POST)
	public String AddNews(@ModelAttribute News news, String category, HttpSession httpSession) {
		try {
			if (UserDAO.isAdmin((User) httpSession.getAttribute("user"))) {
				AdminDAO.addNews(news, category);
				return "SuccessAddNews";
			}
		} catch (Exception e) {
			return "forward:/Login";
		}

		return "redirect:404.html";
	}

	@RequestMapping(value = "/AddNews", method = RequestMethod.GET)
	public String showLoggedToAdd(Model model) {
		model.addAttribute(new News());
		return "AddNews";
	}

	@RequestMapping(value = "/DeleteNews", method = RequestMethod.POST)
	public String deleteNews(@ModelAttribute News news, HttpSession httpSession) {
		try {
			if (UserDAO.isAdmin((User) httpSession.getAttribute("user"))) {
				AdminDAO.deleteNews(news.getId());
				return "deleteNews";
			}
		} catch (Exception e) {
			return "forward:/Login";
		}

		return "redirect:404.html";
	}

	@RequestMapping(value = "/DeleteNews", method = RequestMethod.GET)
	public String showLoggedToDelete(Model model) {
		model.addAttribute(new News());
		return "deleteNews";
	}

}

package com.topnews.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.topnews.exceptions.CategoryException;
import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.NewsException;
import com.topnews.exceptions.UserException;
import com.topnews.models.Category;
import com.topnews.models.INews;
import com.topnews.models.News;
import com.topnews.models.User;
import com.topnews.modelsDAO.CategoryDAO;
import com.topnews.modelsDAO.NewsDAO;
import com.topnews.modelsDAO.UserDAO;

@Controller
public class CategoryController {

	@RequestMapping(value = "/Category", method = RequestMethod.GET)
	public String showNewsInCategory(Model model, HttpSession httpSession, HttpServletRequest request) {
		try {
			model.addAttribute("previous", request.getHeader("Referer"));
			if ((User) httpSession.getAttribute("user") != null) {
				boolean isAdmin = UserDAO.isAdmin((User) httpSession.getAttribute("user"));
				if (isAdmin) {
					model.addAttribute("isAdmin", isAdmin);
				}
			}
			String name = null;
			Integer page = 0;
			if (request.getParameter("name") != null && request.getParameter("page") != null) {
				name = request.getParameter("name");
				page = Integer.parseInt(request.getParameter("page"));
				if (CategoryDAO.isCategoryExists(name)) {
					List<News> worldNews = NewsDAO.getWorldNews();
					for (int index = 0; index < worldNews.size(); index++) {
						News currentNews = worldNews.get(index);
						NewsDAO.addWorldNews(currentNews);
					}
					int countOfPages = NewsDAO.getNumberOfPages(name);
					int numberOfPage = 0;
					List<Integer> pages = new ArrayList<Integer>();
					if (countOfPages > 1) {
						for (int index = 0; index < countOfPages; index++) {
							numberOfPage++;
							pages.add(numberOfPage);
						}
					}
					List<News> news = NewsDAO.showNewsInChosenPage(name, page);
					model.addAttribute("pages", pages);
					model.addAttribute("news", news);
					model.addAttribute("name", name);
					List<INews> latestNews = NewsDAO.showAllNews("date");
					model.addAttribute("latestNews", latestNews);
					Map<String, List<String>> allCategories = CategoryDAO.AllCategories();
					model.addAttribute("allCategories", allCategories);
					List<String> categories = CategoryDAO.showAllCategories();
					model.addAttribute("categories", categories);
					return "showCategory";
				} else {
					model.addAttribute("message",
							"The category which currently you are trying to access does not exist.");
					return "forward:/Error";
				}
			} else {
				model.addAttribute("message", "invalidData");
				return "forward:/Error";
			}

		} catch (ConnectionException e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		} catch (NewsException e) {
			e.printStackTrace();
			return "index";
		} catch (UserException e) {
			e.printStackTrace();
			return "login";
		} catch (CategoryException e) {
			e.printStackTrace();
			model.addAttribute("message", "invalidData");
			return "forward:/Error";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "invalidData");
			return "forward:/Error";
		}
	}

	@RequestMapping(value = "/AddCategory", method = RequestMethod.POST)
	public String AddCategory(@ModelAttribute("category") Category category, Model model, HttpSession httpSession) {
		try {
			User user = (User) httpSession.getAttribute("user");
			if (UserDAO.isAdmin(user)) {
				CategoryDAO.addCategory(category.getName(), category.getSubcategory());
				List<String> categories = CategoryDAO.showAllCategories();
				model.addAttribute("categories", categories);
				model.addAttribute("message", "Successfully added category.");
				return "addCategory";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/pages/404.html";
		}
		return "redirect:/pages/404.html";
	}

	@RequestMapping(value = "/AddCategory", method = RequestMethod.GET)
	public String showLoggedToAdd(Model model) {
		model.addAttribute(new Category());
		try {
			List<String> categories = CategoryDAO.showAllCategories();
			model.addAttribute("categories", categories);
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (NewsException e) {
			e.printStackTrace();
		}
		return "addCategory";
	}

	@RequestMapping(value = "/DeleteCategory", method = RequestMethod.POST)
	public String DeleteCategory(@ModelAttribute("category") Category category, Model model, HttpSession httpSession) {
		try {
			User user = (User) httpSession.getAttribute("user");

			if (UserDAO.isAdmin(user)) {
				CategoryDAO.deleteCategory(category.getSubcategory());
				List<String> categories = CategoryDAO.showAllCategories();
				model.addAttribute("categories", categories);
				model.addAttribute("message", "Successfully deleted category.");
				return "deleteCategory";
			}
		} catch (Exception e) {
			httpSession.setAttribute("errorDelete", "Failed to delete category.");
			httpSession.setAttribute("messageDelete", null);
			e.printStackTrace();
			return "redirect:/DeleteCategory";
		}

		return "redirect:/pages/404.html";
	}

	@RequestMapping(value = "/DeleteCategory", method = RequestMethod.GET)
	public String showLoggedToDelete(Model model) {
		try {
			model.addAttribute(new Category());
			List<String> categories = CategoryDAO.showAllCategories();
			model.addAttribute("categories", categories);
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (NewsException e) {
			e.printStackTrace();
		}
		return "deleteCategory";
	}
}

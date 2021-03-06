package com.topnews.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.topnews.dataLoad.DataLoader;
import com.topnews.exceptions.CategoryException;
import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.NewsException;
import com.topnews.exceptions.UserException;
import com.topnews.models.Category;
import com.topnews.models.INews;
import com.topnews.models.IUser;
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
			IUser user = null;
			DataLoader.LoadSiteData(httpSession, model);
			model.addAttribute("previous", request.getHeader("Referer"));
			if ((User) httpSession.getAttribute("user") != null) {
				user = (User) httpSession.getAttribute("user");
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
					NewsDAO.clearWorldCategory();
					List<News> worldNews = NewsDAO.getWorldNews();
					int sizeOfWorldCategory = worldNews.size();
					for (int index = 0; index < sizeOfWorldCategory; index++) {
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
					int isInFavourites = 0;
					Map<Integer, Integer> favourites = new HashMap<Integer, Integer>();
					for (INews currentNews : news) {
						if (user != null) {
							isInFavourites = UserDAO.checkFavourites(currentNews, user);
							int newsId = NewsDAO.getNewsId(currentNews);
							favourites.put(newsId, isInFavourites);
						}
					}
					model.addAttribute("favourites", favourites);
					model.addAttribute("pages", pages);
					model.addAttribute("news", news);
					model.addAttribute("name", name);
					return "showCategory";
				} else {
					model.addAttribute("message", "invalidCategory");
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
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		} catch (UserException e) {
			e.printStackTrace();
			model.addAttribute("message", "notLogged");
			return "forward:/Login";
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
			if ((User) httpSession.getAttribute("user") != null) {
				User user = (User) httpSession.getAttribute("user");
				if (UserDAO.isAdmin(user)) {
					CategoryDAO.addCategory(category.getName(), category.getSubcategory());
					DataLoader.LoadSiteData(httpSession, model);
					model.addAttribute("message", "successAddCategory");
					return "addCategory";
				} else {
					model.addAttribute("message", "notFoundPage");
					return "forward:/Error";
				}
			}
			model.addAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (UserException e) {
			e.printStackTrace();
			model.addAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (ConnectionException e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "failedAddCategory");
			return "forward:/Error";
		}
	}

	@RequestMapping(value = "/AddCategory", method = RequestMethod.GET)
	public String showLoggedToAdd(Model model, HttpSession httpSession) {
		try {
			if ((User) httpSession.getAttribute("user") != null) {
				User user = (User) httpSession.getAttribute("user");
				if (UserDAO.isAdmin(user)) {
					model.addAttribute(new Category());
					DataLoader.LoadSiteData(httpSession, model);
					return "addCategory";
				}
				model.addAttribute("message", "notFoundPage");
				return "forward:/Error";

			}
			model.addAttribute("message", "notFoundPage");
			return "redirect:/Error";

		} catch (ConnectionException e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		} catch (UserException e) {
			e.printStackTrace();
			model.addAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		}
	}

	@RequestMapping(value = "/DeleteCategory", method = RequestMethod.POST)
	public String DeleteCategory(@ModelAttribute("category") Category category, Model model, HttpSession httpSession) {
		try {
			if ((User) httpSession.getAttribute("user") != null) {
				User user = (User) httpSession.getAttribute("user");
				if (UserDAO.isAdmin(user)) {
					CategoryDAO.deleteCategory(category.getSubcategory());
					DataLoader.LoadSiteData(httpSession, model);
					model.addAttribute("message", "successDeleteCategory");
					return "deleteCategory";
				}
				model.addAttribute("message", "notFoundPage");
				return "forward:/Error";
			}
			model.addAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (UserException e) {
			e.printStackTrace();
			model.addAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (ConnectionException e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "failedDeleteCategory");
			return "forward:/Error";
		}
	}

	@RequestMapping(value = "/DeleteCategory", method = RequestMethod.GET)
	public String showLoggedToDelete(Model model, HttpSession httpSession) {
		try {
			if ((User) httpSession.getAttribute("user") != null) {
				User user = (User) httpSession.getAttribute("user");
				if (UserDAO.isAdmin(user)) {
					model.addAttribute(new Category());
					DataLoader.LoadSiteData(httpSession, model);
					return "deleteCategory";
				}
				model.addAttribute("message", "notFoundPage");
				return "forward:/Error";
			}
			model.addAttribute("message", "notFoundPage");
			return "forward:/Error";
		} catch (ConnectionException e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		} catch (UserException e) {
			e.printStackTrace();
			model.addAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		}
	}
}

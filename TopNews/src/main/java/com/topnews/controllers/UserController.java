package com.topnews.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.topnews.dataLoad.DataLoader;
import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.NewsException;
import com.topnews.models.IUser;
import com.topnews.models.News;
import com.topnews.models.User;
import com.topnews.modelsDAO.NewsDAO;
import com.topnews.modelsDAO.UserDAO;

@Controller
public class UserController {
	@RequestMapping(value = "/Favourites", method = RequestMethod.GET)
	public String showFavourites(Model model, HttpSession httpSession, HttpServletRequest request) {

		try {
			DataLoader.LoadSiteData(httpSession, model);
			model.addAttribute("previous", request.getHeader("Referer"));
			if ((User) httpSession.getAttribute("user") != null) {
				IUser user = (User) httpSession.getAttribute("user");
				String username = user.getUsername();
				Integer page = 0;
				if (request.getParameter("page") != null) {
					page = Integer.parseInt(request.getParameter("page"));
					int countOfPages;
					countOfPages = NewsDAO.getNumberOfPagesForFavourites(user);
					int numberOfPage = 0;
					List<Integer> pages = new ArrayList<Integer>();
					if (countOfPages > 1) {
						for (int index = 0; index < countOfPages; index++) {
							numberOfPage++;
							pages.add(numberOfPage);
						}
					}
					List<News> news = UserDAO.showFavouriteNewsInChosenPage(username, page);
					Map<Integer, String> categories = new HashMap<>();
					for (News currentNews : news) {
						categories.put(currentNews.getId(), currentNews.getCategory());
					}
					model.addAttribute("pages", pages);
					model.addAttribute("news", news);
					model.addAttribute("categories", categories);
					model.addAttribute("name", user.getUsername());
					return "showFavourites";
				}
				model.addAttribute("message", "notFoundPage");
				return "forward:/Error";
			} else {
				model.addAttribute("message", "invalidData");
				return "forward:/Error";
			}

		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (NewsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	@RequestMapping(value = "/AddToFavourites", method = RequestMethod.GET)
	public String addToFavourites(Model model, HttpSession httpSession, HttpServletRequest request) {
		try {
			if (request.getParameter("id") != null) {
				int newsId = Integer.parseInt(request.getParameter("id"));
				if ((User) httpSession.getAttribute("user") != null) {
					User user = (User) httpSession.getAttribute("user");
					if (user.getUsername() != null) {
						UserDAO.addToFavourites(newsId, user.getUsername());
						String referer = request.getHeader("Referer");
						return "redirect:" + referer;
					}
				}
				model.addAttribute("message", "notFoundPage");
				return "forward:/Error";
			}
			model.addAttribute("message", "notFoundPage");
			return "forward:/Error";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		}
	}
	
	@RequestMapping(value = "/RemoveFavourites", method = RequestMethod.GET)
	public String removeFromFavourites(Model model, HttpSession httpSession, HttpServletRequest request) {
		try {
			if (request.getParameter("id") != null) {
				int newsId = Integer.parseInt(request.getParameter("id"));
				if ((User) httpSession.getAttribute("user") != null) {
					User user = (User) httpSession.getAttribute("user");
					if (user.getUsername() != null) {
						UserDAO.removeFromFavourites(newsId, user.getUsername());
						String referer = request.getHeader("Referer");
						return "redirect:" + referer;
					}
				}
				model.addAttribute("message", "notFoundPage");
				return "forward:/Error";
			}
			model.addAttribute("message", "notFoundPage");
			return "forward:/Error";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		}
	}

}

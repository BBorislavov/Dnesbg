package com.topnews.controllers;

import java.io.File;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.topnews.dataLoad.DataLoader;
import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.NewsException;
import com.topnews.exceptions.UserException;
import com.topnews.models.Comment;
import com.topnews.models.INews;
import com.topnews.models.News;
import com.topnews.models.User;
import com.topnews.modelsDAO.NewsDAO;
import com.topnews.modelsDAO.UserDAO;

@Controller
public class NewsController {
	private static final int NUMBER_OF_COMBINATIONS = 1000;
	private static final int ANONYMOUS_USER = 4;
	public static final String LOCATION = "D:\\news_images\\";
	public static final String SERVER_IMAGES_PATH = "./news_images/";
	public static final String SERVER_DEFAULT_IMAGE = "./news_images/icon-default-news.png";

	@RequestMapping(value = "/AddNews", method = RequestMethod.POST)
	public String AddNews(@ModelAttribute News news, @RequestParam("photoUrl") MultipartFile multipartFile,
			String category, Model model, HttpSession httpSession) {
		try {
			if ((User) httpSession.getAttribute("user") != null) {
				User user = (User) httpSession.getAttribute("user");
				if (UserDAO.isAdmin(user)) {
					String photoUrl = SERVER_DEFAULT_IMAGE;
					if (!multipartFile.isEmpty()) {
						String[] path = multipartFile.getOriginalFilename().split(":\\\\");
					
						String fileName = path[path.length - 1];
						String username = null;
						if ((String) httpSession.getAttribute("username") != null) {
							username = (String) httpSession.getAttribute("username");
						} else {
							username = "default";
						}
						String location = LOCATION + username + "\\";
						new File(location).mkdir();
						int random = (new Random().nextInt() * NUMBER_OF_COMBINATIONS);
						FileCopyUtils.copy(multipartFile.getBytes(), new File(location + random + fileName));
						photoUrl = SERVER_IMAGES_PATH + username + "/" + random + fileName;
					}
					if (news.getText().equals("Invalid text") || news.getTitle().equals("Invalid title")) {
						model.addAttribute("error", "incorrectNews");
						return "addNews";
					}
					System.err.println(photoUrl);
					NewsDAO.addNews(news, category, photoUrl);
					model.addAttribute("message", "successNews");
					DataLoader.LoadSiteData(httpSession, model);
					return "addNews";
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
		} catch (NewsException e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
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

	@RequestMapping(value = "/AddNews", method = RequestMethod.GET)
	public String showLoggedToAdd(Model model, HttpSession httpSession) {
		try {
			if (httpSession.getAttribute("user") != null) {
				User user = (User) httpSession.getAttribute("user");
				if (UserDAO.isAdmin(user)) {
					model.addAttribute(new News());
					DataLoader.LoadSiteData(httpSession, model);
					return "addNews";
				}
				model.addAttribute("message", "notFoundPage");
				return "forward:/Error";
			}
			model.addAttribute("message", "notFoundPage");
			return "forward:/Error";
		} catch (UserException e) {
			e.printStackTrace();
			model.addAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (ConnectionException e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		}

	}

	@RequestMapping(value = "/News", method = RequestMethod.GET)
	public String showCurrentNews(Model model, HttpSession httpSession, HttpServletRequest request) {
		try {
			String category = request.getParameter("category");
			Integer id = Integer.parseInt(request.getParameter("id"));
			if (category != null && id != null) {
				httpSession.setAttribute("id", id);
				model.addAttribute("category", category);
				httpSession.setAttribute("category", category);
				if ((User) httpSession.getAttribute("user") != null) {
					User user = (User) httpSession.getAttribute("user");
					int userId = UserDAO.getUserIdFromDB(user.getUsername());
					httpSession.setAttribute("userId", userId);
				} else {
					httpSession.setAttribute("userId", ANONYMOUS_USER);
				}
				List<News> lastNews = NewsDAO.showLastNewsInSubcategory(category);
				model.addAttribute("lastNews", lastNews);
				INews news = NewsDAO.showCurrentNews(id);
				NewsDAO.increaseRating(id);
				model.addAttribute("news", news);
				model.addAttribute(new Comment());
				DataLoader.LoadSiteData(httpSession, model);
				return "showCurrent";
			} else {
				model.addAttribute("message", "serverMaintenance");
				return "forward:/Error";
			}
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

	@RequestMapping(value = "/DeleteNews", method = RequestMethod.GET)
	public String deleteNews(Model model, HttpSession httpSession, HttpServletRequest request) {
		try {
			if (request.getParameter("id") != null) {
				int id = Integer.parseInt(request.getParameter("id"));
				if ((User) httpSession.getAttribute("user") != null) {
					User user = (User) httpSession.getAttribute("user");
					if (UserDAO.isAdmin(user)) {
						NewsDAO.deleteNews(id);
						String referer = request.getHeader("Referer");
						return "redirect:" + referer;
					}
					model.addAttribute("message", "notFoundPage");
					return "forward:/Error";
				}
				model.addAttribute("message", "notFoundPage");
				return "forward:/Error";
			}
			model.addAttribute("message", "notFoundPage");
			return "forward:/Error";
		} catch (UserException e) {
			e.printStackTrace();
			model.addAttribute("message", "notLogged");
			return "forward:/Login";
		} catch (ConnectionException e) {
			e.printStackTrace();
			model.addAttribute("message", "serverMaintenance");
			return "forward:/Error";
		}
	}
}

package com.topnews.controllers;

import java.io.File;
import java.util.List;
import java.util.Map;
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

import com.topnews.models.Comment;
import com.topnews.models.INews;
import com.topnews.models.News;
import com.topnews.models.User;
import com.topnews.modelsDAO.CategoryDAO;
import com.topnews.modelsDAO.NewsDAO;
import com.topnews.modelsDAO.UserDAO;

@Controller
public class NewsController {
	private static final int NUMBER_OF_COMBINATIONS = 10000;
	private static final int ANONYMOUS_USER = 4;
	public static final String LOCATION = "D:\\news_images\\";
	public static final String SERVER_IMAGES_PATH = "./news_images/";
	public static final String SERVER_DEFAULT_IMAGE = "./news_images/alert-001.jpg";

	@RequestMapping(value = "/AddNews", method = RequestMethod.POST)
	public String AddNews(@ModelAttribute News news, @RequestParam("photoUrl") MultipartFile multipartFile,
			String category, Model model, HttpSession httpSession) {
		try {
			User user = (User) httpSession.getAttribute("user");
			if (UserDAO.isAdmin(user)) {
				String photoUrl = SERVER_DEFAULT_IMAGE;
				if (!multipartFile.isEmpty()) {
					String[] path = multipartFile.getOriginalFilename().split(":\\\\");
					String fileName = path[path.length - 1] + (new Random().nextInt()*NUMBER_OF_COMBINATIONS);
					String username = null;
					if ((String) httpSession.getAttribute("username") != null) {
						username = (String) httpSession.getAttribute("username");
					} else {
						username = "default";
					}
					String location = LOCATION + username + "\\";
					new File(location).mkdir();
					FileCopyUtils.copy(multipartFile.getBytes(), new File(location + fileName));
					photoUrl = SERVER_IMAGES_PATH + username + "/" + fileName;
				}
				if (news.getText().equals("Invalid text") || news.getTitle().equals("Invalid title")) {
					model.addAttribute("error", "Failed to add news - title or text was incorectly filled!");
					List<String> categories = CategoryDAO.showAllCategories();
					model.addAttribute("categories", categories);
					List<INews> latestNews = NewsDAO.showAllNews("date");
					List<INews> popularNews = NewsDAO.showAllNews("rating");
					model.addAttribute("latestNews", latestNews);
					model.addAttribute("popularNews", popularNews);
					Map<String, List<String>> allCategories = CategoryDAO.AllCategories();
					model.addAttribute("allCategories", allCategories);
					return "addNews";
				}
				NewsDAO.addNews(news, category, photoUrl);
				model.addAttribute("message", "Your news has been successfully created.");
				List<String> categories = CategoryDAO.showAllCategories();
				model.addAttribute("categories", categories);
				List<INews> latestNews = NewsDAO.showAllNews("date");
				List<INews> popularNews = NewsDAO.showAllNews("rating");
				model.addAttribute("latestNews", latestNews);
				model.addAttribute("popularNews", popularNews);
				Map<String, List<String>> allCategories = CategoryDAO.AllCategories();
				model.addAttribute("allCategories", allCategories);
				return "addNews";
			}
			return "login";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/pages/404.html";
		}
	}

	@RequestMapping(value = "/AddNews", method = RequestMethod.GET)
	public String showLoggedToAdd(Model model) {
		model.addAttribute(new News());
		try {
			List<String> categories = CategoryDAO.showAllCategories();
			model.addAttribute("categories", categories);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/pages/404.html";
		}
		return "addNews";
	}

	@RequestMapping(value = "/News", method = RequestMethod.GET)
	public String showCurrentNews(@ModelAttribute("category") String category, @ModelAttribute("id") int id,
			Model model, HttpSession httpSession) {
		try {
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
			List<INews> latestNews = NewsDAO.showAllNews("date");
			model.addAttribute("latestNews", latestNews);
			model.addAttribute("news", news);
			Map<String, List<String>> allCategories = CategoryDAO.AllCategories();
			model.addAttribute("allCategories", allCategories);
			model.addAttribute(new Comment());
			return "showCurrent";
		} catch (Exception e) {
			e.printStackTrace();
			return "forward:/Login";
		}
	}

	@RequestMapping(value = "/DeleteNews", method = RequestMethod.GET)
	public String deleteNews(@ModelAttribute("id") int id, Model model, HttpSession httpSession,
			HttpServletRequest request) {
		try {
			if ((User) httpSession.getAttribute("user") != null) {
				User user = (User) httpSession.getAttribute("user");
				if (UserDAO.isAdmin(user)) {
					NewsDAO.deleteNews(id);
					String referer = request.getHeader("Referer");
					return "redirect:" + referer;
				}
			}
			return "forward:/Login";
		} catch (Exception e) {
			e.printStackTrace();
			return "error-404";
		}
	}
}

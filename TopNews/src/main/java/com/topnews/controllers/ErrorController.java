package com.topnews.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.NewsException;
import com.topnews.models.INews;
import com.topnews.modelsDAO.CategoryDAO;
import com.topnews.modelsDAO.NewsDAO;

@Controller
public class ErrorController {
	@RequestMapping(value = "/*", method = RequestMethod.GET)
	public String home(Model model, HttpSession httpSession) {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		httpSession.setAttribute("message", "notFoundPage");
		return "forward:/Error";
	}

	@RequestMapping(value = "/Error", method = RequestMethod.GET)
	public String error(Model model, HttpSession httpSession) {
		List<INews> allNews;
		try {
			allNews = NewsDAO.showAllNews();
			List<INews> popularNews = NewsDAO.showAllNews("rating");
			List<INews> latestNews = NewsDAO.showAllNews("date");
			model.addAttribute("latestNews", latestNews);
			model.addAttribute("popularNews", popularNews);
			model.addAttribute("allNews", allNews);
			Map<String, List<String>> allCategories = CategoryDAO.AllCategories();
			model.addAttribute("allCategories", allCategories);
			return "error-404";
		} catch (ConnectionException e) {
			e.printStackTrace();
			return "redirect:/";
		} catch (NewsException e) {
			e.printStackTrace();
			return "redirect:/";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/";		}

	}
}

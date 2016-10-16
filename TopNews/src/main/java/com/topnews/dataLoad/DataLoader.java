package com.topnews.dataLoad;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.NewsException;
import com.topnews.models.INews;
import com.topnews.modelsDAO.CategoryDAO;
import com.topnews.modelsDAO.EmailDAO;
import com.topnews.modelsDAO.NewsDAO;

public abstract class DataLoader {

	public static void LoadSiteData(HttpSession httpSession, Model model) {
		try {
			List<INews> allNews = NewsDAO.showAllNews();
			List<INews> popularNews = NewsDAO.showAllNews("rating");
			List<INews> latestNews = NewsDAO.showAllNews("date");
			httpSession.setAttribute("latestNews", latestNews);
			httpSession.setAttribute("popularNews", popularNews);
			httpSession.setAttribute("allNews", allNews);
			Map<String, List<String>> allCategories = CategoryDAO.AllCategories();
			httpSession.setAttribute("allCategories", allCategories);
			List<String> categories = CategoryDAO.showAllCategories();
			httpSession.setAttribute("categories", categories);
			int unreaded = EmailDAO.numberOfUnreaded();
			int readed = EmailDAO.numberOfReaded();
			httpSession.setAttribute("unreaded", unreaded);
			httpSession.setAttribute("readed", readed);
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NewsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

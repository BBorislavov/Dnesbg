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
			List<INews> mostCommentedNews = NewsDAO.getNewsWithMostComments();
			httpSession.setAttribute("mostCommented", mostCommentedNews);
			List<INews> allNews = NewsDAO.showAllNews();
			List<INews> popularNews = NewsDAO.showAllNews("rating");
			List<INews> latestNews = NewsDAO.showAllNews("date");
			List<INews> bulgariaNews = NewsDAO.showNewsInCategpry("Bulgaria");
			List<INews> sportNews = NewsDAO.showNewsInCategpry("Sport");
			List<INews> businessNews = NewsDAO.showNewsInCategpry("Business");
			List<INews> technologiesNews = NewsDAO.showNewsInCategpry("Technologies");
			INews firstSportNews = NewsDAO.showFirstNewsInCategpry("Sport");
			INews firstBulgariaNews = NewsDAO.showFirstNewsInCategpry("Bulgaria");
			INews firstBusinessNews = NewsDAO.showFirstNewsInCategpry("Business");
			INews firstTechnologiesNews = NewsDAO.showFirstNewsInCategpry("Technologies");
			httpSession.setAttribute("firstSportNews", firstSportNews);
			httpSession.setAttribute("firstBusinessNews", firstBusinessNews);
			httpSession.setAttribute("firstTechnologiesNews", firstTechnologiesNews);
			httpSession.setAttribute("firstBulgariaNews", firstBulgariaNews);
			httpSession.setAttribute("bulgariaNews", bulgariaNews);
			httpSession.setAttribute("sportNews", sportNews);
			httpSession.setAttribute("businessNews", businessNews);
			httpSession.setAttribute("technologiesNews", technologiesNews);
			httpSession.setAttribute("latestNews", latestNews);
			httpSession.setAttribute("popularNews", popularNews);
			httpSession.setAttribute("allNews", allNews);
			Map<String, List<String>> allCategories = CategoryDAO.allCategories();
			Map<String, List<String>> categoryNames = CategoryDAO.allCategoriesNames();
			httpSession.setAttribute("categoryNames", categoryNames);
			httpSession.setAttribute("allCategories", allCategories);
			List<String> categories = CategoryDAO.showAllCategories();
			httpSession.setAttribute("categories", categories);
			List<String> subcategories = CategoryDAO.showAllSubCategories();
			httpSession.setAttribute("subcategories", subcategories);
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

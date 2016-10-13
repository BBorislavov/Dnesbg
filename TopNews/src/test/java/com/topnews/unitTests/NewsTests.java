package com.topnews.unitTests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.NewsException;
import com.topnews.models.News;
import com.topnews.modelsDAO.NewsDAO;

public class NewsTests {
	
	@Test
	public void testCreateNews() throws ConnectionException, NewsException {
		List<News> news = NewsDAO.showNewsInSubcategory("test");

		assertTrue(news.size() > 0);
	}


	@Test
	public void testShowNews() throws ConnectionException, NewsException {
		List<News> news = NewsDAO.showNewsInSubcategory("test");

		assertTrue(news.size() > 0);
	}

}

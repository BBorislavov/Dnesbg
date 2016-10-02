package com.topnews.unitTests;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;

import com.topnews.modelsDAO.UserDAO;
import com.topnews.exceptions.CommentException;
import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.NewsException;
import com.topnews.exceptions.UserException;
import com.topnews.models.News;
import com.topnews.models.User;

public class UserTests {

	// @Test
	// public void testUserRegister() throws ConnectionException, UserException{
	//
	// User user = new User("mesibg4", "afsa6Jfa", "asfasn@mail.bg");
	// UserDAO.registerUser(user);
	//
	// boolean isExist = UserDAO.isUserExisting(user.getUsername(),
	// user.getPassword());
	// assertTrue(isExist);
	// }

	// @Test
	// public void testIsAdmin() throws UserException, ConnectionException {
	// boolean isNotAdmin = UserDAO.isAdmin("mesibg3");
	// boolean isAdmin = UserDAO.isAdmin("mesibg");
	// assertFalse(isNotAdmin);
	// assertTrue(isAdmin);
	// }

	// @Test
	// public void testAddComment() throws ConnectionException, UserException{
	// UserDAO.addComment(1, 8, "����� ��� ����, ����");
	//
	// }

	// @Test
	// public void testDeleteComment() throws ConnectionException,
	// CommentException{
	// UserDAO.removeComment(1, 8, "����� ��� ����, ����");
	// }

	@Test
	public void testShowNews() throws ConnectionException, NewsException {
		List<News> news = UserDAO.showNewsInSubcategory("������");

		assertTrue(news.size() > 0);
	}

}

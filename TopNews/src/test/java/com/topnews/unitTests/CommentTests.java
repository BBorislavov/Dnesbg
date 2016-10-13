package com.topnews.unitTests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.topnews.exceptions.CommentException;
import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.UserException;
import com.topnews.modelsDAO.CommentDAO;

public class CommentTests {

	@Test
	public void testAddComment() throws ConnectionException, UserException {
		int newsId = 1;
		int userId = 1;
		String text = "Comment text";
		boolean isAdded = CommentDAO.addComment(newsId, userId, text);
		assertTrue(isAdded);
	}

	@Test
	public void testDeleteComment() throws ConnectionException, CommentException {
		String text = "Comment text";
		boolean isRemoved = CommentDAO.removeComment(4);
		assertTrue(isRemoved);
	}

}

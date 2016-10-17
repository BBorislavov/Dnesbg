package com.topnews.unitTests;

import org.junit.Test;

import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.UserException;
import com.topnews.modelsDAO.AdminDAO;
import com.topnews.modelsDAO.NewsDAO;

public class AdminTests {

	@Test
	public void giveRights() throws ConnectionException, UserException{
		AdminDAO.giveRights("test user");
	}
	
	
	@Test
	public void removeRights() throws ConnectionException, UserException{
		AdminDAO.removeRights("test user");
	}

	@Test
	public void deleteUser() throws ConnectionException, UserException{
		NewsDAO.deleteNews(111);
	}

}

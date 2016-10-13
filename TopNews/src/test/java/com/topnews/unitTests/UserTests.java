package com.topnews.unitTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.UserException;
import com.topnews.models.User;
import com.topnews.modelsDAO.UserDAO;

public class UserTests {

	@Test
	public void testUserRegister() throws ConnectionException, UserException {
		User user = new User("test user", "md5(password)", "testUser@mail.bg");
		UserDAO.registerUser(user);

		boolean isExist = UserDAO.isUserExisting(user);
		assertTrue(isExist);
	}

	@Test
	public void testIsAdmin() throws UserException, ConnectionException {
		User user = new User("test user", "md5(password)", "testUser@mail.bg");
		boolean isAdmin = UserDAO.isAdmin(user);
		assertTrue(isAdmin);
		assertFalse(isAdmin);
	}

}

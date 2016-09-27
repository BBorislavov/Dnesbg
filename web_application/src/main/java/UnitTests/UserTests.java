package UnitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import DnesbgDAO.UserDAO;
import exceptions.ConnectionException;
import exceptions.UserException;

public class UserTests {
	
	@Test
	public void testUserLogin() throws ConnectionException, UserException{
		boolean isExist = UserDAO.isUserExisting("mesibg", "123456");
		
		assertTrue(isExist);
	}

}

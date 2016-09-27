package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import exceptions.UserException;

public class Validations {
	

	public static boolean isValidUsername(User user) throws UserException {
		if (isValidString(user.getUsername())) {
			return true;
		}
		throw new UserException("Invalid username");
	}
	

	private static boolean isValidString(String string) {
		if (string != null && string.trim().length() > 0) {
			return true;
		}
		return false;
	}
}

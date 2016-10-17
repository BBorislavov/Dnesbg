package com.topnews.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidator {
	private Pattern pattern;
	private Matcher matcher;
	private static final String USERNAME_PATTERN = "^[a-z" 		// Only lower case symbols are allowed
												 + "0-9_-]" 	// Digits and _ symbols are allowed
												 + "{3,15}$"; 	// Username must be between 3 and 15 symbols

	public UsernameValidator() {
		pattern = Pattern.compile(USERNAME_PATTERN);
	}

	public boolean validate(final String username) {
		matcher = pattern.matcher(username);
		return matcher.matches();
	}
}

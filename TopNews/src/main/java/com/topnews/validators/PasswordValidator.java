package com.topnews.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
	private Pattern pattern;
	private Matcher matcher;
	private static final String PASSWORD_PATTERN = "(?=.*[0-9])" // must have at least one digit
												 + "(?=.*[a-z])" // must have at least one lower case
												 + "(?=.*[A-Z])" // must have at least one upper case
												 + "(?=\\S+$)"	 // no spaces allowed
												 + ".{6,15}";	 // must be between 6 and 15 chars

	public PasswordValidator() {
		pattern = Pattern.compile(PASSWORD_PATTERN);
	}

	public boolean validate(final String password) {
		matcher = pattern.matcher(password);
		return matcher.matches();
	}
}

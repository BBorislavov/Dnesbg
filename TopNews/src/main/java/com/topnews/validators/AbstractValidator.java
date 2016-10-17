package com.topnews.validators;

public class AbstractValidator {
	
	public static final String INVALID_USERNAME = "Invalid username";
	public static final String INVALID_EMAIL = "Invalid email";
	public static final String INVALID_PASSWORD = "Invalid password";
	public static final String INVALID_SENDER = "Invalid sender";
	public static final String INVALID_SUBJECT = "Invalid subject";
	public static final String INVALID_TEXT = "Invalid text";
	public static final String INVALID_PHOTOURL = "Invalid photo";
	public static final String INVALID_TITLE = "Invalid title";
	public static final String INVALID_SUBCATEGORY = "Invalid subcategory";
	public static final String INVALID_CATEGORY = "Invalid subcategory";
	public static final String INVALID_COMMENT = "Invalid comment";
	

	public static boolean isValidString(String string) {
		if (string != null && string.trim().length() > 0) {
			return true;
		}
		return false;
	}
}

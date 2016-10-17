package com.topnews.models;

import com.topnews.validators.AbstractValidator;

public class User implements IUser {
	private int userId;
	private String username;
	private String password;
	private String email;
	private boolean isAdmin;

	public User(String username, String password, String email) {
		setUsername(username);
		setPassword(password);
		setEmail(email);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		if (userId > 0) {
			this.userId = userId;
		}
	}

	public User() {
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (AbstractValidator.isValidString(password)) {
			this.password = password;
		} else {
			this.password = AbstractValidator.INVALID_PASSWORD;
		}
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		if (AbstractValidator.isValidString(email)) {
			this.email = email;
		} else {
			this.email = AbstractValidator.INVALID_EMAIL;
		}
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if (AbstractValidator.isValidString(username)) {
			this.username = username;
		} else {
			this.username = AbstractValidator.INVALID_USERNAME;
		}

	}

}

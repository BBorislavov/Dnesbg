package com.topnews.models;

import com.topnews.exceptions.UserException;
import com.topnews.interfaces.IUser;

public class User implements IUser {

	private int userId;
	private String username;
	private String password;
	private String email;
	private boolean isAdmin;

	public User(String username, String password, String email) throws UserException {
		setUsername(username);
		setPassword(password);
		setEmail(email);
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) throws UserException {
		if (userId > 0) {
			this.userId = userId;
		} else {
			throw new UserException("Invalid user id");
		}
	}

	public User() {
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		if (isValidString(password))
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws UserException {
		if (isValidString(email)) {
			this.email = email;
		} else {
			throw new UserException("Invalid email");
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

	public void setUsername(String username) throws UserException {
		if (isValidString(username)) {
			this.username = username;
		} 

	}

	private static boolean isValidString(String string) {
		return string != null && !string.trim().isEmpty();
	}

	public void giveRights(String name) throws UserException {
		throw new UserException("You have no rights to set admin");
	}

}

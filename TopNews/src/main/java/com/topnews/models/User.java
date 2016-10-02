package com.topnews.models;

import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.topnews.exceptions.UserException;
import com.topnews.interfaces.IUser;

public class User implements IUser{

	private AtomicInteger userId;
	private String username;
	@NotEmpty(message = "Please enter your password.")
    @Size(min = 6, max = 15, message = "Your password must between 6 and 15 characters")
	private String password;
	private String email;
	private boolean isAdmin;

	public User(String username, String password, String email) throws UserException {
		setUsername(username);
		setPassword(password);
		setEmail(email);
	}

	public int getUserId() {
		return userId.get();
	}

	public void setUserId(int userId) throws UserException {
		if (userId > 0) {
			this.userId.getAndSet(userId);
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
		if (isValidString(username)){
			this.username = username;
		} else {
			throw new UserException("Invalid username");
		}
		
	}

	private static boolean isValidString(String string) {
		return string != null && !string.trim().isEmpty();
	}

	public void giveRights(String name) throws UserException {
		throw new UserException("You have no rights to set admin");
	}
	

}

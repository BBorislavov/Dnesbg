package com.topnews.models;

import com.topnews.exceptions.UserException;

public class Admin extends User{

	public Admin(String username, String password, String email) throws UserException {
		super(username, password, email);
	}
	
	@Override
	public void giveRights(String name) throws UserException {
		System.out.println("hello");
	}

}

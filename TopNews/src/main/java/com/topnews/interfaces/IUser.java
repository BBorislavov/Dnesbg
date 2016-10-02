package com.topnews.interfaces;

import com.topnews.exceptions.UserException;

public interface IUser {
	
//	public AtomicInteger getUserId();

	public String getUsername();

	public String getPassword();

	public String getEmail();

	public boolean isAdmin();
	
	public void giveRights(String name) throws UserException;

}

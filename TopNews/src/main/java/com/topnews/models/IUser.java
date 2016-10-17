package com.topnews.models;

public interface IUser {

	int getUserId();

	String getUsername();

	String getPassword();

	String getEmail();

	boolean isAdmin();

}

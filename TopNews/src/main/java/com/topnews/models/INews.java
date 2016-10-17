package com.topnews.models;

import com.topnews.exceptions.NewsException;

public interface INews {

	int getId();

	void setId(int id) throws NewsException;

	String getTitle();

	void setTitle(String title) throws NewsException;

	String getText();

	void setText(String text) throws NewsException;

	String getDateOfPost();

	void setDateOfPost(String dateOfPost);

	String getPhotoUrl();

	String getCategory();

	void setCategory(String category);

}
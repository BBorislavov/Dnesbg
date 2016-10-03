package com.topnews.models;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import com.topnews.exceptions.NewsException;

public class News {
	private int id;
	private String title;
	private String text;
	private String photoUrl;
	private String category;
	private String dateOfPost;
	
	public News() {
	}

	
	
	public News(String title, String text, String photoUrl, String date, int id) throws NewsException {
		setTitle(title);
		setText(text);
		setPhotoUrl(photoUrl);
		setDateOfPost(date);
		setId(id);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) throws NewsException {
		if (id > 0) {
			this.id=id;
		} else {
			throw new NewsException("Invalid news id");
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) throws NewsException {
		if (isValidString(title)) {
			this.title = title;
		} else {
			throw new NewsException("Invalid title");
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) throws NewsException {
		if (isValidString(text)) {
			this.text = text;
		} else {
			throw new NewsException("Invalid text");
		}
	}

	public String getDateOfPost() {
		return dateOfPost;
	}

	public void setDateOfPost(String dateOfPost) {
		this.dateOfPost = dateOfPost;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	private void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	private static boolean isValidString(String string) {
		return string != null && !string.trim().isEmpty();
	}

}
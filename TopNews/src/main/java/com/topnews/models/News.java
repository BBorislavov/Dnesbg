package com.topnews.models;

import java.util.concurrent.atomic.AtomicInteger;

import com.topnews.validators.AbstractValidator;

public class News implements INews {
	private int id;
	private String title;
	private String text;
	private String photoUrl;
	private String category;
	private String dateOfPost;
	private AtomicInteger rating = new AtomicInteger(0);

	public News() {
	}

	public News(String title, String text, String photoUrl, String date, int id, String category, int rating){
		setTitle(title);
		setText(text);
		setPhotoUrl(photoUrl);
		setDateOfPost(date);
		setId(id);
		setCategory(category);
		setRating(rating);
	}

	public AtomicInteger getRating() {
		return rating;
	}

	public void setRating(int rating) {

		this.rating.getAndSet(rating);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id > 0) {
			this.id = id;
		} 
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if (AbstractValidator.isValidString(title)) {
			this.title = title;
		} else {
			this.title = AbstractValidator.INVALID_TITLE;
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (AbstractValidator.isValidString(text)) {
			this.text = text;
		} else {
			this.text = AbstractValidator.INVALID_TEXT;
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
		if (AbstractValidator.isValidString(category)){
		this.category = category;
		} else {
			this.category = AbstractValidator.INVALID_CATEGORY;
		}
	}

}

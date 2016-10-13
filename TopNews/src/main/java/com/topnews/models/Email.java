package com.topnews.models;

import com.topnews.validators.AbstractValidator;

public class Email implements IEmail {

	private String sender;
	private String subject;
	private String text;
	private String date;
	private String photo;
	private boolean isReaded;
	private int id;

	public Email() {
	}

	public Email(String sender, String subject, String text, String date, String photo, int id) {
		setSender(sender);
		setSubject(subject);
		setDate(date);
		setText(text);
		setPhoto(photo);
		setId(id);
	}


	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		if (AbstractValidator.isValidString(sender)) {
			this.sender = sender;
		} else {
			this.sender = AbstractValidator.INVALID_SENDER;
		}
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		if (AbstractValidator.isValidString(subject)) {
			this.subject = subject;
		} else {
			this.subject = AbstractValidator.INVALID_SUBJECT;
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getPhoto() {
		return photo;
	}

	private void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean isReaded() {
		return isReaded;
	}

	public void setReaded(boolean isReaded) {
		this.isReaded = isReaded;
	}

	public int getId() {
		return id;
	}

	private void setId(int id) {
		if (id>0){
		this.id = id;
		}
	}

}

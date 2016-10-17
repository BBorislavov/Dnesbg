package com.topnews.models;

public interface IEmail {

	String getSender();

	void setSender(String sender);

	String getSubject();

	void setSubject(String subject);

	String getText();

	void setText(String text);

	String getDate();

	void setDate(String date);

	String getPhoto();

//	void setPhoto(String photo);

}
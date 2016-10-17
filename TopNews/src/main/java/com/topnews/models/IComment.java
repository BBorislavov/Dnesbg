package com.topnews.models;

public interface IComment {

	String getText();

	void setText(String text);

	String getUser();

	void setUser(String user);

	int getNewsId();

	void setNewsId(int newsId);

	String getDate();

	void setDate(String date);

	int getCommentId();

	void setCommentId(int commentId);

	String toString();

}
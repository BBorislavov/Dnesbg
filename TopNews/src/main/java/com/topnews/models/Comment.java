package com.topnews.models;

import com.topnews.validators.AbstractValidator;

public class Comment implements IComment {

	private String text;
	private int commentId;
	private String user;
	private int newsId;
	private String date;

	public Comment(int id, String text, String user, String date) {
		setCommentId(id);
		setText(text);
		setUser(user);
		setDate(date);
	}

	public Comment() {
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setText(String text) {
		if (AbstractValidator.isValidString(text)) {
			this.text = text;
		} else {
			this.text = AbstractValidator.INVALID_COMMENT;
		}
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	public void setUser(String user) {
		if (AbstractValidator.isValidString(user)) {
			this.user = user;
		} else {
			this.user = AbstractValidator.INVALID_USERNAME;
		}
	}

	@Override
	public int getNewsId() {
		return newsId;
	}

	@Override
	public void setNewsId(int newsId) {
		if (newsId > 0) {
			this.newsId = newsId;
		}
	}

	@Override
	public String getDate() {
		return date;
	}

	@Override
	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public int getCommentId() {
		return commentId;
	}

	@Override
	public void setCommentId(int commentId) {
		if (commentId > 0) {
			this.commentId = commentId;
		}
	}

	public String toString() {
		return "Comment [text=" + text + ", user=" + user + ", date=" + date + "]";
	}

}

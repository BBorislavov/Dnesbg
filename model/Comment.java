package model;

import java.util.concurrent.atomic.AtomicInteger;

import exceptions.CommentException;

public class Comment {

	private String text;
	private AtomicInteger commentId;
	private int userId;
	private int newsId;

	public Comment(String text) throws CommentException {
		setText(text);
		setUserId(userId);
		setNewsId(newsId);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) throws CommentException {
		if (isValidString(text)) {
			this.text = text;
		} else {
			throw new CommentException("Invalid comment");
		}
	}

//	public int getCommentId() {
//		return commentId.get();
//	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) throws CommentException {
		if (userId > 0) {
			this.userId = userId;
		} else {
			throw new CommentException("Invalid userId");
		}
	}

	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) throws CommentException {
		if (newsId > 0) {
			this.newsId = newsId;
		} else {
			throw new CommentException("Invalid newsId");
		}
	}

	private static boolean isValidString(String string) {
		return string != null && !string.trim().isEmpty();
	}

}

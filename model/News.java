package model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import exceptions.NewsException;

public class News {
	private AtomicInteger newsId;
	private String title;
	private String text;
	private final LocalDateTime dateOfPost = LocalDateTime.now();

	public News(String title, String text) throws NewsException {
		setTitle(title);
		setText(text);
	}

//	public int getNewsId() {
//		return newsId.get();
//	}
//
//	public void setNewsId(int newsId) throws NewsException {
//		if (newsId > 0) {
//			this.newsId.getAndSet(newsId);
//		} else {
//			throw new NewsException("Invalid news id");
//		}
//	}

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
		if (isValidString(text)){
		this.text = text;
		} else {
			throw new NewsException("Invalid text");
		}
	}

	public LocalDateTime getDateOfPost() {
		return dateOfPost;
	}

	private static boolean isValidString(String string) {
		return string != null && !string.trim().isEmpty();
	}

}

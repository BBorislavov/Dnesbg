package com.topnews.modelsDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.topnews.exceptions.CommentException;
import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.NewsException;
import com.topnews.exceptions.UserException;
import com.topnews.models.Comment;

public class CommentDAO {
	
	private static final String ADD_COMMENT = "INSERT INTO news_db.comments " + "VALUES(null,?,?,?,?);";
	private static final String DELETE_COMMENT = "DELETE FROM news_db.comments WHERE comment_id=?;";
	private static final String GET_NEWS_COMMENTS = "SELECT u.username, c.text, c.date, c.comment_id FROM news_db.comments c"
			+ " JOIN news_db.users u ON(c.user_id=u.id) WHERE News_id=? ORDER BY date DESC;";
	
	public static boolean addComment(int newsId, int userId, String text) throws ConnectionException, UserException {

		Connection connection = DBConnection.getInstance().getConnection();
		String currentTime = LocalDateTime.now().toString();

		try {
			PreparedStatement statement = connection.prepareStatement(ADD_COMMENT);
			statement.setInt(1, newsId);
			statement.setInt(2, userId);
			statement.setString(3, text);
			statement.setString(4, currentTime);
			statement.executeUpdate();
			return true;
		} catch (Exception e) {
			throw new UserException("Failed to add comment.", e);
		}
	}

	public static boolean removeComment(int id)
			throws ConnectionException, CommentException {

		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(DELETE_COMMENT);
			statement.setInt(1, id);
			statement.executeUpdate();
			return true;
		} catch (Exception e) {
			throw new CommentException("Failed to remove comment.", e);
		}
	}
	
	public static List<Comment> showCommentsUnderNews(int id) throws ConnectionException, NewsException {

		Connection connection = DBConnection.getInstance().getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(GET_NEWS_COMMENTS);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			List<Comment> showCommentsUnderNews = new ArrayList<Comment>();
			while (resultSet.next()) {
				String username = resultSet.getString("u.username");
				String text = resultSet.getString("c.text");
				String date = resultSet.getString("c.date");
				int commentId = resultSet.getInt("c.comment_id");
				Comment comments = new Comment(commentId, text, username, date);
				showCommentsUnderNews.add(comments);
			}
			return Collections.unmodifiableList(showCommentsUnderNews);
		} catch (Exception e) {
			throw new NewsException("Failed to show comments under news.", e);
		}
	}

}

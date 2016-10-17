package com.topnews.modelsDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.topnews.exceptions.AlertException;
import com.topnews.exceptions.CommentException;
import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.NewsException;
import com.topnews.models.Email;
import com.topnews.models.IEmail;

public class EmailDAO extends AbstractDAO {

	private static final String ADD_EMAIL = "INSERT INTO news_db.emails VALUES (null,?,?,?,?, 0);";
	private static final String INSERT_PHOTO = "INSERT INTO news_db.photos VALUES(null, null, ?, ?);";
	private static final String NUMBER_OF_UNREADED = "SELECT COUNT(*) FROM emails WHERE is_readed = 0;";
	private static final String NUMBER_OF_READED = "SELECT COUNT(*) FROM emails WHERE is_readed = 1;";
	private static final String GET_UNREADED = "SELECT u.username, e.* FROM emails e  "
			+ " JOIN users u  ON (u.id = e.user_id) WHERE is_readed=0 ORDER BY date DESC;";
	private static final String GET_READED = "SELECT u.username, e.* FROM emails e  "
			+ " JOIN users u  ON (u.id = e.user_id) WHERE is_readed=1 ORDER BY date DESC;";
	private static final String GET_EMAIL_ID = "SELECT id FROM emails WHERE subject = ?;";
	private static final String SHOW_CURRENT_ALERT = "SELECT u.username, e.subject , e.text , e.date , p.url FROM emails e "
			+ " JOIN photos p  ON (p.email_id = e.id) JOIN users u ON (u.id = e.user_id) WHERE e.id = ?;";
	private static final String SET_READED = "UPDATE emails SET is_readed = 1 WHERE id = ?;";
	private static final String DELETE_EMAIL = "DELETE FROM emails WHERE id=?;";
	private static final String DELETE_PHOTO = "DELETE FROM photos WHERE email_id = ?;";

	public static boolean sendEmail(IEmail email, int userId, String photoUrl)
			throws NewsException, ConnectionException {
		
		try {
			String subject = email.getSubject();
			String text = email.getText();
			String date = LocalDateTime.now().toString();
			
			PreparedStatement insertStatement = connection.prepareStatement(ADD_EMAIL);
			insertStatement.setInt(1, userId);
			insertStatement.setString(2, subject);
			insertStatement.setString(3, text);
			insertStatement.setString(4, date);
			insertStatement.executeUpdate();

			PreparedStatement idStatement = connection.prepareStatement(GET_EMAIL_ID);
			idStatement.setString(1, subject);
			ResultSet resultSetNews = idStatement.executeQuery();
			resultSetNews.next();
			int emailId = resultSetNews.getInt(1);

			PreparedStatement addPhotoStatement = connection.prepareStatement(INSERT_PHOTO);
			addPhotoStatement.setInt(1, emailId);
			addPhotoStatement.setString(2, photoUrl);
			addPhotoStatement.executeUpdate();

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NewsException("Failed to add email", e);
		}
	}

	public static int numberOfUnreaded() throws ConnectionException, NewsException {
		try {
			PreparedStatement statement = connection.prepareStatement(NUMBER_OF_UNREADED);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			int numberOfUnreaded = resultSet.getInt(1);

			return numberOfUnreaded;
		} catch (Exception e) {
			throw new NewsException("Failed to show unreaded.", e);
		}
	}

	public static List<IEmail> showUnreaded() throws ConnectionException, NewsException {
		try {
			PreparedStatement statement = connection.prepareStatement(GET_UNREADED);
			ResultSet resultSet = statement.executeQuery();
			List<IEmail> unreadedAlerts = new ArrayList<IEmail>();
			while (resultSet.next()) {
				String sender = resultSet.getString("username");
				String subject = resultSet.getString("subject");
				String text = resultSet.getString("text");
				String date = resultSet.getString("date");
				int id = resultSet.getInt("id");
				unreadedAlerts.add(new Email(sender, subject, text, date, "blank photo", id));
			}

			return Collections.unmodifiableList(unreadedAlerts);
		} catch (Exception e) {
			throw new NewsException("Failed to show unreaded.", e);
		}
	}

	public static int numberOfReaded() throws ConnectionException, NewsException {
		try {
			PreparedStatement statement = connection.prepareStatement(NUMBER_OF_READED);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			int numberOfReaded = resultSet.getInt(1);

			return numberOfReaded;
		} catch (Exception e) {
			throw new NewsException("Failed to show readed.", e);
		}
	}

	public static List<IEmail> showReaded() throws ConnectionException, NewsException {
		try {
			PreparedStatement statement = connection.prepareStatement(GET_READED);
			ResultSet resultSet = statement.executeQuery();
			List<IEmail> readedAlerts = new ArrayList<IEmail>();
			while (resultSet.next()) {
				String sender = resultSet.getString("username");
				String subject = resultSet.getString("subject");
				String text = resultSet.getString("text");
				String date = resultSet.getString("date");
				int id = resultSet.getInt("id");
				readedAlerts.add(new Email(sender, subject, text, date, "blank photo", id));
			}

			return Collections.unmodifiableList(readedAlerts);
		} catch (Exception e) {
			throw new NewsException("Failed to show readed.", e);
		}
	}

	public static IEmail showCurrentAlert(int id) throws ConnectionException, AlertException {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(SHOW_CURRENT_ALERT);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();

			String sender = resultSet.getString("u.username");
			String subject = resultSet.getString("e.subject");
			String url = resultSet.getString("p.url");
			String text = resultSet.getString("e.text");
			String date = resultSet.getString("e.date");

			IEmail alert = new Email(sender, subject, text, date, url, id);
			return alert;
		} catch (Exception e) {
			throw new AlertException("Failed to show current news.", e);
		}
	}

	public static void setReaded(int id) throws SQLException, ConnectionException, AlertException {
		try {
			PreparedStatement addPhotoStatement = connection.prepareStatement(SET_READED);
			addPhotoStatement.setInt(1, id);
			addPhotoStatement.executeUpdate();
		} catch (Exception e) {
			throw new AlertException("Failed to set readed", e);
		}
	}

	public static boolean deleteAlert(int id) throws ConnectionException, CommentException {
		try {
			PreparedStatement photoStatement = connection.prepareStatement(DELETE_PHOTO);
			photoStatement.setInt(1, id);
			photoStatement.executeUpdate();

			PreparedStatement statement = connection.prepareStatement(DELETE_EMAIL);
			statement.setInt(1, id);
			statement.executeUpdate();
			return true;
		} catch (Exception e) {
			throw new CommentException("Failed to delete alert.", e);
		}
	}

}

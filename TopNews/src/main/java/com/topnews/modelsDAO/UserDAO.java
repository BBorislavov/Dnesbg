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
import com.topnews.interfaces.IUser;
import com.topnews.models.Category;
import com.topnews.models.News;
import com.topnews.validators.EmailVaildator;
import com.topnews.validators.PasswordVaildator;
import com.topnews.validators.UserNameVaildator;

public class UserDAO {

	private static final String INSERT_USER_IN_SQL = "INSERT INTO news_db.users VALUES (null,?,md5(?),?,false);";

	private static final String CHECK_USER_EXISTING = "SELECT COUNT(*) FROM news_db.users WHERE username = ? AND password = md5(?);";

	private static final String CHECK_IS_ADMIN = "SELECT is_admin FROM news_db.users WHERE username = ?;";

	private static final String ADD_COMMENT = "INSERT INTO news_db.comments " + "VALUES(null,?,?,?,?);";

	private static final String DELETE_COMMENT = "DELETE FROM news_db.comments " + "WHERE News_id=?" + " AND User_id=?"
			+ " AND text=?;";

	private static final String SHOW_NEWS_FROM_SUBCATEGORY = "SELECT n.id, p.url, n.title, n.date FROM news_db.news n"
			+ " JOIN news_db.news_has_categories nc" + " ON (n.id=nc.news_id)" + " JOIN news_db.categories c"
			+ " ON (nc.subcategory_id = c.subcategory_id)" + " LEFT OUTER JOIN (news_db.photos p)" + " ON (n.id=p.news_id)"
			+ " WHERE c.name=?;";

<<<<<<< HEAD
	private static final String SHOW_CURRENT_NEWS = "SELECT p.url, n.title, n.text, n.date FROM news_db.news n"
			+ " JOIN news_db.news_has_categories nc" + " ON (n.id=nc.news_id)" + " JOIN news_db.categories c"
			+ " ON (nc.subcategory_id = c.subcategory_id)" + " LEFT OUTER JOIN (news_db.photos p)" + " ON (n.id=p.news_id)"
			+ " WHERE n.id=?;";

=======
>>>>>>> 9946e4c20da744a7724d3382ab8643aa020cb169
	public static boolean registerUser(IUser user) throws ConnectionException, UserException {

		Connection connection = DBConnection.getInstance().getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(INSERT_USER_IN_SQL);
			if (new UserNameVaildator().Validate(user.getUsername())) {
				statement.setString(1, user.getUsername());
			} else {
				throw new UserException("Invalid username");
			}
			if (new PasswordVaildator().validate(user.getPassword())) {
				statement.setString(2, user.getPassword());
			} else {
				throw new UserException("Invalid password");
			}
			if (new EmailVaildator().validate(user.getEmail())) {
				statement.setString(3, user.getEmail());
			} else {
				throw new UserException("Invalid email");
			}
			statement.executeUpdate();
			return true;
		} catch (Exception e) {
			throw new UserException("Registration failed. Try again.", e);
		}
	}

	public static boolean isUserExisting(IUser user) throws ConnectionException, UserException {

		Connection connection = DBConnection.getInstance().getConnection();
		try {

			PreparedStatement statement = connection.prepareStatement(CHECK_USER_EXISTING);
			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			int isExisting = resultSet.getInt("COUNT(*)");
			if (isExisting == 0) {
				System.out.println("User does not exist");
				return false;

			}
			return true;

		} catch (Exception e) {
			throw new UserException("Unable to login. Try again.", e);
		}
	}

	public static boolean isAdmin(IUser user) throws ConnectionException, UserException {

		Connection connection = DBConnection.getInstance().getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(CHECK_IS_ADMIN);
			statement.setString(1, user.getUsername());

			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			if (resultSet.getInt("is_admin") == 0) {
				return false;
			}
			return true;

		} catch (Exception e) {
			throw new UserException("Failed check user rights.", e);
		}
	}

	public static void addComment(int newsId, int userId, String text) throws ConnectionException, UserException {

		Connection connection = DBConnection.getInstance().getConnection();
		String currentTime = LocalDateTime.now().toString();

		try {
			PreparedStatement statement = connection.prepareStatement(ADD_COMMENT);
			statement.setInt(1, newsId);
			statement.setInt(2, userId);
			statement.setString(3, text);
			statement.setString(4, currentTime);
			statement.executeUpdate();
		} catch (Exception e) {
			throw new UserException("Failed to add comment.", e);
		}
	}

	public static void removeComment(int newsId, int userId, String text) throws ConnectionException, CommentException {

		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(DELETE_COMMENT);
			statement.setInt(1, newsId);
			statement.setInt(2, userId);
			statement.setString(3, text);
			statement.executeUpdate();

		} catch (Exception e) {
			throw new CommentException("Failed to remove comment.", e);
		}
	}

	public static List<News> showNewsInSubcategory(String subcategoryName) throws ConnectionException, NewsException {

		Connection connection = DBConnection.getInstance().getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(SHOW_NEWS_FROM_SUBCATEGORY);
			statement.setString(1, subcategoryName);
			ResultSet resultSet = statement.executeQuery();
			List<News> newsInSubcategory = new ArrayList<News>();
			while (resultSet.next()) {
				String url = resultSet.getString("p.url");
				String title = resultSet.getString("n.title");
				String date = resultSet.getString("n.date");
<<<<<<< HEAD
				int id = resultSet.getInt("n.id");
				News news = new News(title, "show full news", url, date, id);
=======
				System.out.println(date);
				News news = new News(title, "show full news", url);
>>>>>>> 9946e4c20da744a7724d3382ab8643aa020cb169
				newsInSubcategory.add(news);
			}
			return Collections.unmodifiableList(newsInSubcategory);
		} catch (Exception e) {
			throw new NewsException("Failed to show news.", e);
		}
	}
	
	public static News showCurrentNews(int id) throws ConnectionException, NewsException {

		Connection connection = DBConnection.getInstance().getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(SHOW_CURRENT_NEWS);
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			String url = resultSet.getString("p.url");
			String title = resultSet.getString("n.title");
			String date = resultSet.getString("n.date");
			String text = resultSet.getString("n.text");
			News currentNews = new News(title, text, url, date, id);
			return currentNews;
		} catch (Exception e) {
			throw new NewsException("Failed to show current news.", e);
		}
	}

}

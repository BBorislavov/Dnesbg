package com.topnews.modelsDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.NewsException;
import com.topnews.exceptions.UserException;
import com.topnews.models.INews;
import com.topnews.models.IUser;
import com.topnews.models.News;
import com.topnews.validators.EmailValidator;
import com.topnews.validators.PasswordValidator;
import com.topnews.validators.UsernameValidator;

public class UserDAO extends AbstractDAO {

	private static final String INSERT_USER_IN_SQL = "INSERT INTO news_db.users VALUES (null,?,md5(?),?,false);";
	private static final String CHECK_USER_EXISTING = "SELECT COUNT(*) FROM news_db.users WHERE username = ? AND password = md5(?);";
	public static final String CHECK_USER_ID = "SELECT news_db.users.id FROM news_db.users WHERE username = ?";
	private static final String CHECK_IS_ADMIN = "SELECT is_admin FROM news_db.users WHERE username = ?;";
	private static final String CHECK_USERNAME_EXISTING = "SELECT COUNT(*) FROM news_db.users WHERE username = ?;";
	private static final String ADD_NEWS_TO_FAVOURITES = "INSERT INTO users_has_favourite_news VALUES (?,?)";
	private static final String CHECK_IS_FAVOURITE = "SELECT news_id FROM users_has_favourite_news WHERE users_id = ? AND news_id = ?;";
	private static final String REMOVE_NEWS_FROM_FAVOURITES = "DELETE FROM users_has_favourite_news WHERE news_id = ? AND users_id = ?;";
	private static final String SHOW_FAVOURITE_NEWS = "SELECT c.name, n.id, n.rating, p.url, n.title, n.text, n.date FROM news n JOIN news_db.news_has_categories nc"
			+ " ON (n.id=nc.news_id) JOIN news_db.categories c ON (nc.subcategory_id = c.subcategory_id) LEFT OUTER JOIN (news_db.photos p) ON (n.id=p.news_id)"
			+ " JOIN users_has_favourite_news uf ON (uf.news_id = n.id) JOIN users u ON (u.id = uf.users_id) WHERE u.username=? ORDER BY n.date DESC;";

	public static boolean registerUser(IUser user) throws ConnectionException, UserException {

		try {
			PreparedStatement statement = connection.prepareStatement(INSERT_USER_IN_SQL);
			boolean isValidUsername = new UsernameValidator().validate(user.getUsername());
			boolean isValidPassword = new PasswordValidator().validate(user.getPassword());
			boolean isValidEmail = new EmailValidator().validate(user.getEmail());
			if (isValidUsername && isValidPassword && isValidEmail) {
				statement.setString(1, user.getUsername());
				statement.setString(2, user.getPassword());
				statement.setString(3, user.getEmail());
				statement.executeUpdate();
				return true;
			}
		} catch (Exception e) {
			throw new UserException("Registration failed. Try again.", e);
		}
		return false;
	}

	public static boolean isUserExisting(IUser user) throws ConnectionException, UserException {

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

	public static boolean isUsernameExisting(IUser user) throws ConnectionException, UserException {

		try {

			PreparedStatement statement = connection.prepareStatement(CHECK_USERNAME_EXISTING);
			statement.setString(1, user.getUsername());
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			int isExisting = resultSet.getInt("COUNT(*)");
			if (isExisting == 0) {
				System.out.println("User does not exist");
				return false;

			}
			return true;

		} catch (Exception e) {
			throw new UserException("Unable to check username. Try again.", e);
		}
	}

	public static int getUserIdFromDB(String username) throws ConnectionException, UserException {

		try {
			PreparedStatement statement = connection.prepareStatement(CHECK_USER_ID);
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			int id = resultSet.getInt("id");
			return id;
		} catch (Exception e) {
			throw new UserException("Unable to login. Try again.", e);
		}
	}

	public static boolean isAdmin(IUser user) throws ConnectionException, UserException {

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

	public static void addToFavourites(int newsId, String username) throws UserException {

		try {
			PreparedStatement userIdStatement = connection.prepareStatement(CHECK_USER_ID);
			userIdStatement.setString(1, username);
			ResultSet resultSet = userIdStatement.executeQuery();
			resultSet.next();
			int userId = resultSet.getInt("id");

			PreparedStatement addFavouritesStatement = connection.prepareStatement(ADD_NEWS_TO_FAVOURITES);
			addFavouritesStatement.setInt(1, newsId);
			addFavouritesStatement.setInt(2, userId);
			addFavouritesStatement.executeUpdate();

		} catch (Exception e) {
			throw new UserException("Failed add news to favourites.", e);
		}

	}

	public static int checkFavourites(INews currentNews, IUser user) throws UserException {
		try {
			PreparedStatement getNewsIdStatement = connection.prepareStatement(NewsDAO.GET_NEWS_ID);
			getNewsIdStatement.setString(1, currentNews.getTitle());
			ResultSet newsResultSet = getNewsIdStatement.executeQuery();
			newsResultSet.next();
			int newsId = newsResultSet.getInt(1);
			
			PreparedStatement getUserIdStatement = connection.prepareStatement(CHECK_USER_ID);
			getUserIdStatement.setString(1, user.getUsername());
			ResultSet userResultSet = getUserIdStatement.executeQuery();
			userResultSet.next();
			int userId = userResultSet.getInt(1);
			
			PreparedStatement checkFavouritesStatement = connection.prepareStatement(CHECK_IS_FAVOURITE);
			checkFavouritesStatement.setInt(1, userId);
			checkFavouritesStatement.setInt(2, newsId);
			ResultSet resultSet = checkFavouritesStatement.executeQuery();
			int favouriteNews = 0;
			if (resultSet.next()){
			favouriteNews = resultSet.getInt(1);
			}
			return favouriteNews;
		} catch (SQLException e) {
			throw new UserException("Failed to check favourites.", e);
		}

	}

	public static void removeFromFavourites(int newsId, String username) {
		
		try {
			PreparedStatement userIdStatement = connection.prepareStatement(CHECK_USER_ID);
			userIdStatement.setString(1, username);
			ResultSet resultSet = userIdStatement.executeQuery();
			resultSet.next();
			int userId = resultSet.getInt("id");
			System.err.println(userId);
			
			PreparedStatement addFavouritesStatement = connection.prepareStatement(REMOVE_NEWS_FROM_FAVOURITES);
			addFavouritesStatement.setInt(1, newsId);
			addFavouritesStatement.setInt(2, userId);
			addFavouritesStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static List<News> showFavouriteNewsInChosenPage(String username, Integer pageNumber) throws NewsException {
		try {
			PreparedStatement statement = connection.prepareStatement(SHOW_FAVOURITE_NEWS);
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();
			List<News> newsInChosenPage = new ArrayList<News>();
			int firstIndex = (pageNumber * NewsDAO.NUMBER_OF_NEWS_IN_ONE_PAGE) - NewsDAO.ALL_NEWS_EXCEPT_FIRST;
			int lastIndex = (pageNumber * NewsDAO.NUMBER_OF_NEWS_IN_ONE_PAGE);
			int newsNumber = 0;
			while (resultSet.next()) {
				String url = resultSet.getString("p.url");
				String title = resultSet.getString("n.title");
				String date = resultSet.getString("n.date");
				String fullText = resultSet.getString("n.text");
				int rating = resultSet.getInt("n.rating");
				String categoryName = resultSet.getString("c.name");
				int countSize = 0;
				for (int index = 0; index < fullText.length(); index++) {
					if (fullText.charAt(index) != '.') {
						countSize++;
					} else {
						break;
					}
				}
				String text = fullText.substring(0, countSize) + "...";
				int id = resultSet.getInt("n.id");
				newsNumber++;
				if (newsNumber >= firstIndex && newsNumber <= lastIndex) {
					newsInChosenPage.add(new News(title, text, url, date, id, categoryName, rating));
				}
			}
			return Collections.unmodifiableList(newsInChosenPage);
		} catch (Exception e) {
			throw new NewsException("Failed to show news.", e);
		}
	}
	
	
}

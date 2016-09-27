package DnesbgDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import exceptions.CommentException;
import exceptions.ConnectionException;
import exceptions.NewsException;
import exceptions.UserException;
import interfaces.IUser;
import model.Category;
import validators.EmailVaildator;
import validators.PasswordVaildator;
import validators.UserNameVaildator;

public class UserDAO {

	private static final String INSERT_USER_IN_SQL = "INSERT INTO news_db.users VALUES (null,?,md5(?),?,false);";

	private static final String CHECK_USER_EXISTING = "SELECT COUNT(*) FROM news_db.users WHERE username = ? AND password = md5(?);";

	private static final String CHECK_IS_ADMIN = "SELECT is_admin FROM news_db.users WHERE username = ?;";

	private static final String ADD_COMMENT = "INSERT INTO news_db.comments" + "VALUES(null,?,?,?,?);";

	private static final String DELETE_COMMENT = "DELETE FROM news_db.users" + "WHERE News_id=?" + "AND User_id=?"
			+ "AND text='?';";

	private static final String SHOW_NEWS_FROM_SUBCATEGORY = "SELECT p.url, n.title FROM news_db.news n"
			+ "JOIN news_db.news_has_categories nc" + "ON (n.id=nc.news_id)" + "JOIN news_db.categories c"
			+ "ON (nc.subcategory_id = c.category_id)" + "JOIN (news_db.photos p)" + "ON (n.id=p.news_id)"
			+ "AND c.name='?';";

	public static void registerUser(IUser user) throws ConnectionException, UserException {

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

		} catch (Exception e) {
			throw new UserException("Registration failed. Try again.");
		}
	}

	public static boolean isUserExisting(String username, String password) throws ConnectionException, UserException {

		Connection connection = DBConnection.getInstance().getConnection();
		try {

			System.out.println("1");
			PreparedStatement statement = connection.prepareStatement(CHECK_USER_EXISTING);
			statement.setString(1, username);
			statement.setString(2, password);
			System.out.println("2");
			ResultSet resultSet = statement.executeQuery();
			System.out.println("3");
			resultSet.next();
			int isExisting = resultSet.getInt("COUNT(*)");
			System.out.println("4");
			if (isExisting == 0) {
				System.out.println("User does not exist");
				return false;

			}
			System.out.println("5");
			return true;

		} catch (Exception e) {
			throw new UserException("Failed check user existing. Try again.");
		}
	}

	public static boolean isAdmin(String username) throws ConnectionException, UserException {

		Connection connection = DBConnection.getInstance().getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(CHECK_IS_ADMIN);
			statement.setString(1, username);

			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			if (resultSet.getInt("is_admin") == 0) {
				System.out.println("This user hasn't admin rights.");
				return false;
			}
			return true;

		} catch (Exception e) {
			throw new UserException("Failed check user rights.");
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
			throw new UserException("Failed to add comment.");
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
			throw new CommentException("Failed to remove comment.");
		}
	}

	public static void showNewsInSubcategory(String subcategoryName) throws ConnectionException, NewsException {

		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement statement = connection.prepareStatement(SHOW_NEWS_FROM_SUBCATEGORY);

			statement.setString(1, subcategoryName);

			ResultSet resultSet = statement.executeQuery();

			List<String> titles = new ArrayList<String>();
			List<String> urls = new ArrayList<String>();

			while (resultSet.next()) {

				String url = resultSet.getString("p.url");
				String title = resultSet.getString("t.title");
				titles.add(title);
				urls.add(url);
				// jsp.... !!!!
			}

		} catch (Exception e) {
			throw new NewsException("Failed to show news.");
		}
	}

}

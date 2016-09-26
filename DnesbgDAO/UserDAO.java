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

public class UserDAO {
	
	private static final String INSERT_USER_IN_SQL = "INSERT INTO news_db.users VALUES (null,?,?,?,false);";
	
	private static final String CHECK_USER_EXISTING = "SELECT COUNT(*)  FROM news_db.users"
													+ "WHERE username = '?' AND password = '?';";
	
	private static final String ADD_COMMENT = "INSERT INTO news_db.comments"
													+ "VALUES(null,?,?,'?','?');";
	
	private static final String DELETE_COMMENT = "DELETE FROM news_db.users"
													+ "WHERE News_id=?"
													+ "AND User_id=?"
													+ "AND text='?';";
	
	private static final String SHOW_NEWS_FROM_SUBCATEGORY = "SELECT p.url, n.title FROM news_db.news n"
													+ "JOIN news_db.news_has_categories nc"
													+ "ON (n.id=nc.news_id)"
													+ "JOIN news_db.categories c"
													+ "ON (nc.subcategory_id = c.category_id)"
													+ "JOIN (news_db.photos p)"
													+ "ON (n.id=p.news_id)"
													+ "AND c.name='?';";
	
	public static void registerUser(IUser user) throws ConnectionException, UserException {
		
		Connection connection = DBConnection.getInstance().getConnection();
		try {
			String sql = INSERT_USER_IN_SQL;
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getEmail());
			statement.executeUpdate();

		} catch (Exception e) {
			throw new UserException("Registration failed. Try again.");
		} 
	}
	
	public static boolean isUserExisting(String username, String password) throws ConnectionException, UserException {
		
		Connection connection = DBConnection.getInstance().getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(CHECK_USER_EXISTING);
			statement.setString(1, username);
			statement.setString(2, password);

			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			if (resultSet.getInt("count") == 0) {
				System.out.println("User does not exist");
				return false;
			}
			return true;

		} catch (Exception e) {
			throw new UserException("Failed check user existing. Try again.");
		}
	}
	
	public static void addComment(int newsId,int userId,String text) throws ConnectionException, UserException{
		
		Connection connection = DBConnection.getInstance().getConnection();
		String currentTime =LocalDateTime.now().toString();
		
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
	
	public static void removeComment(int newsId,int userId,String text) throws ConnectionException, CommentException{
		
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
	
	public static void showNewsInSubcategory(String subcategoryName) throws ConnectionException, NewsException{
		
		Connection connection = DBConnection.getInstance().getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(SHOW_NEWS_FROM_SUBCATEGORY);
			
			statement.setString(1, subcategoryName);

			ResultSet resultSet = statement.executeQuery();
			
			List<String> newsFromCategory = new ArrayList<String>();
			
			while(resultSet.next()){
				
				String url = resultSet.getString("p.url");
				String title = resultSet.getString("t.title");
				//jsp.... !!!!
			}
			
		} catch (Exception e) {
			throw new NewsException("Failed to show news.");
		} 
	}
	
}

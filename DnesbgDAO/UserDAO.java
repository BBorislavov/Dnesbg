package DnesbgDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;

import exceptions.ConnectionException;
import interfaces.IUser;

public class UserDAO {
	
	private static final String INSERT_USER_IN_SQL = "INSERT INTO news_db.users VALUES (null,?,?,?,false);";
	
	private static final String CHECK_USER_EXISTING = "SELECT COUNT(*)  FROM news_db.users"
													+ "WHERE username = '?' AND password = '?';";
	
	private static final String ADD_COMMENT = "INSERT INTO news_db.comments"
													+ "VALUES(null,?,?,'?','?');";
	
	private static final String DELETE_COMMENT = "DELETE FROM news_db.users"
													+ "WHERE News_id=?"
													+ "AND User_id=?"
													+ "AND text='?'"
													+ "AND date='?';";
	
	private static final String SHOW_NEWS_FROM_CATEGORY = "SELECT title FROM news_db.news n"
													+ "JOIN news_db.news_has_categories nc"
													+ "ON (n.id=nc.news_id)"
													+ "JOIN news_db.categories c"
													+ "ON (nc.subcategory_id = c.category_id)"
													+ "AND c.name='?';";
	
	public static void registerUser(IUser user) throws ConnectionException {
		
		Connection connection = DBConnection.getInstance().getConnection();
		try {
			String sql = INSERT_USER_IN_SQL;
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getEmail());
			statement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static boolean isUserExisting(String username, String password) throws ConnectionException {
		
		Connection connection = DBConnection.getInstance().getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(CHECK_USER_EXISTING);
			statement.setString(1, username);
			statement.setString(2, password);

			ResultSet rs = statement.executeQuery();
			rs.next();
			if (rs.getInt("count") == 0) {
				System.out.println("User does not exist");
				return false;
			}
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void addComment(int newsId,int userId,String text,LocalDateTime date) throws ConnectionException{
		
		Connection connection = DBConnection.getInstance().getConnection();
		String DateTimeToString = date.toString();
		
		try {
			PreparedStatement statement = connection.prepareStatement(ADD_COMMENT);
			statement.setInt(1, newsId);
			statement.setInt(2, userId);
			statement.setString(3, text);
			statement.setString(4, DateTimeToString);
			statement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void removeComment(int newsId,int userId,String text,LocalDateTime date) throws ConnectionException{
		
		Connection connection = DBConnection.getInstance().getConnection();
		String DateTimeToString = date.toString();
		
		try {
			PreparedStatement statement = connection.prepareStatement(DELETE_COMMENT);
			statement.setInt(1, newsId);
			statement.setInt(2, userId);
			statement.setString(3, text);
			statement.setString(4, DateTimeToString);
			statement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void showNewsInSubcategory(String subcategoryName) throws ConnectionException{
		
		Connection connection = DBConnection.getInstance().getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(SHOW_NEWS_FROM_CATEGORY);
			
			statement.setString(1, subcategoryName);
			statement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
}

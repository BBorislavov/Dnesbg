package DnesbgDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import exceptions.ConnectionException;

public class AdminDAO {
	
private static final String GIVE_RIGHTS_TO_USER = "UPDATE news_db.users SET is_admin=\"1\" "
													+ "WHERE username='?';";

private static final String REMOVE_RIGHTS_TO_USER = "UPDATE news_db.users SET is_admin=\"0\" "
													+ "WHERE username='?';";

private static final String DELETE_USER = "DELETE FROM news_db.users" 
													+ "WHERE username='?';";
	
	
	public static void giveRights(String username) throws ConnectionException {
		Connection connection = DBConnection.getInstance().getConnection();
		try {
			String sql = GIVE_RIGHTS_TO_USER;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	public static void removeRights(String username) throws ConnectionException {
		Connection connection = DBConnection.getInstance().getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(REMOVE_RIGHTS_TO_USER);
			statement.setString(1, username);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	public static void deleteUser(String username) throws ConnectionException {
		Connection connection = DBConnection.getInstance().getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(DELETE_USER);
			statement.setString(1, username);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
}

package com.topnews.modelsDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.UserException;

public class AdminDAO extends AbstractDAO {

	private static final String GIVE_RIGHTS_TO_USER = "UPDATE news_db.users SET is_admin=\"1\" WHERE username=?;";
	private static final String REMOVE_RIGHTS_TO_USER = "UPDATE news_db.users SET is_admin=\"0\" WHERE username=?;";
	private static final String DELETE_USER_FROM_COMMENTS = "UPDATE comments SET user_id=5 WHERE user_id=?;";
	private static final String DELETE_USER = "DELETE FROM news_db.users" + " WHERE username=?;";
	private static final String DELETE_USER_FROM_EMAILS = "UPDATE emails SET user_id=5 WHERE user_id=?;";

	public static boolean giveRights(String username) throws ConnectionException, UserException {
		try {
			String sql = GIVE_RIGHTS_TO_USER;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("Failed to give rights", e);
		}
	}

	public static void removeRights(String username) throws ConnectionException, UserException {
		try {
			PreparedStatement statement = connection.prepareStatement(REMOVE_RIGHTS_TO_USER);
			statement.setString(1, username);
			statement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("Failed to remove rights", e);
		}
	}

	public static void deleteUser(String username) throws ConnectionException, UserException {
		try {
			PreparedStatement getUserIdStatement = connection.prepareStatement(UserDAO.CHECK_USER_ID);
			getUserIdStatement.setString(1, username);
			ResultSet resultSet = getUserIdStatement.executeQuery();
			resultSet.next();
			int userId = resultSet.getInt(1);
			
			PreparedStatement commentStatement = connection.prepareStatement(DELETE_USER_FROM_COMMENTS);
			commentStatement.setInt(1, userId);
			commentStatement.executeUpdate();
			
			PreparedStatement emailStatement = connection.prepareStatement(DELETE_USER_FROM_EMAILS);
			emailStatement.setInt(1, userId);
			emailStatement.executeUpdate();
			
			PreparedStatement deleteUserStatement = connection.prepareStatement(DELETE_USER);
			deleteUserStatement.setString(1, username);
			deleteUserStatement.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw new UserException("Failed to delete user", e);
		}
	}
}

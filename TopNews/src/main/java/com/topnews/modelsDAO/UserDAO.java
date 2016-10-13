package com.topnews.modelsDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.UserException;
import com.topnews.models.IUser;
import com.topnews.validators.EmailValidator;
import com.topnews.validators.PasswordValidator;
import com.topnews.validators.UsernameValidator;

public class UserDAO {

	private static final String INSERT_USER_IN_SQL = "INSERT INTO news_db.users VALUES (null,?,md5(?),?,false);";
	private static final String CHECK_USER_EXISTING = "SELECT COUNT(*) FROM news_db.users WHERE username = ? AND password = md5(?);";
	public static final String CHECK_USER_ID = "SELECT news_db.users.id FROM news_db.users WHERE username = ?";
	private static final String CHECK_IS_ADMIN = "SELECT is_admin FROM news_db.users WHERE username = ?;";

	public static boolean registerUser(IUser user) throws ConnectionException, UserException {

		Connection connection = DBConnection.getInstance().getConnection();
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

	public static int getUserIdFromDB(String username) throws ConnectionException, UserException {

		Connection connection = DBConnection.getInstance().getConnection();
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
}

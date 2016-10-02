package com.topnews.modelsDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;

import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.UserException;
import com.topnews.models.News;

public class AdminDAO {
	
private static final String INSERT_NEWS_IN_CATEGORY = "INSERT INTO news_db.news_has_categories VALUES(?,?);";

private static final String INSERT_PHOTO = "INSERT INTO news_db.photos (news_id) VALUES (?);";

private static final String GET_NEWS_ID = "SELECT id FROM news_db.news WHERE title = ?;";

private static final String INSERT_NEWS = "INSERT INTO news_db.news VALUES (null,?,?,?)";

private static final String GET_CATEGORY_ID = "SELECT subcategory_id FROM news_db.categories WHERE name = ?;";

private static final String DELETE_SUBCATEGORY = "DELETE FROM news_db.categories WHERE name=?;";

private static final String INSERT_SUBCATEGORY = "INSERT INTO news_db.categories VALUES (? , null , ?);";

private static final String GIVE_RIGHTS_TO_USER = "UPDATE news_db.users SET is_admin=\"1\" "
													+ "WHERE username=?;";

private static final String REMOVE_RIGHTS_TO_USER = "UPDATE news_db.users SET is_admin=\"0\" "
													+ "WHERE username=?;";

private static final String DELETE_USER = "DELETE FROM news_db.users" 
													+ " WHERE username=?;";


	
	
	public static void giveRights(String username) throws ConnectionException, UserException {
		Connection connection = DBConnection.getInstance().getConnection();
		try {
			String sql = GIVE_RIGHTS_TO_USER;
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.executeUpdate();
		} catch (Exception e) {
			throw new UserException("Failed to give rights" , e);
		} 
	}
	
	
	public static void removeRights(String username) throws ConnectionException, UserException {
		Connection connection = DBConnection.getInstance().getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(REMOVE_RIGHTS_TO_USER);
			statement.setString(1, username);
			statement.executeUpdate();
		} catch (Exception e) {
			throw new UserException("Failed to remove rights" , e);
		} 
	}
	
	
	public static void deleteUser(String username) throws ConnectionException, UserException {
		Connection connection = DBConnection.getInstance().getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(DELETE_USER);
			statement.setString(1, username);
			statement.executeUpdate();
		} catch (Exception e) {
			throw new UserException("Failed to delete user" , e);
		} 
	}
	
	
	public static void addSubcategory(String nameOfSubcategory, String categoryName) throws ConnectionException, UserException {
		Connection connection = DBConnection.getInstance().getConnection();
		try {
			PreparedStatement selectStatement = connection.prepareStatement(GET_CATEGORY_ID);
			selectStatement.setString(1, categoryName);
			ResultSet resultSet = selectStatement.executeQuery();
			resultSet.next();
			int categoryId = resultSet.getInt(1);
			
			PreparedStatement insertStatement = connection.prepareStatement(INSERT_SUBCATEGORY);
			insertStatement.setInt(1, categoryId);
			insertStatement.setString(2, nameOfSubcategory);
			insertStatement.executeUpdate();
		} catch (Exception e) {
			throw new UserException("Failed to add category" , e);
		} 
	}
	
	public static void removeSubcategory(String nameOfSubcategory) throws ConnectionException, UserException {
		Connection connection = DBConnection.getInstance().getConnection();
		try {
			PreparedStatement insertStatement = connection.prepareStatement(DELETE_SUBCATEGORY);
			insertStatement.setString(1, nameOfSubcategory);
			insertStatement.executeUpdate();
		} catch (Exception e) {
			throw new UserException("Failed to add category" , e);
		} 
	}
	
	public static void addNews(News news, String category) throws ConnectionException, UserException{
		Connection connection = DBConnection.getInstance().getConnection();
		String title = news.getTitle();
		String text = news.getText();
		String date = LocalDateTime.now().toString();
		
		try {
			PreparedStatement insertStatement = connection.prepareStatement(INSERT_NEWS);
			insertStatement.setString(1, title);
			insertStatement.setString(2, text);
			insertStatement.setString(3, date);
			insertStatement.executeUpdate();
			
			PreparedStatement idStatement = connection.prepareStatement(GET_NEWS_ID);
			idStatement.setString(1, title);
			ResultSet resultSetNews = idStatement.executeQuery();
			resultSetNews.next();
			int newsId = resultSetNews.getInt(1);
			
			PreparedStatement addPhotoStatement = connection.prepareStatement(INSERT_PHOTO);
			addPhotoStatement.setInt(1, newsId);
			addPhotoStatement.executeUpdate();
			
			PreparedStatement statementGetCategoryId = connection.prepareStatement(GET_CATEGORY_ID);
			statementGetCategoryId.setString(1, category);
			ResultSet resultSetCategory = statementGetCategoryId.executeQuery();
			resultSetCategory.next();
			int categoryId = resultSetCategory.getInt(1);
			
			PreparedStatement statementInsertNewsCategory = connection.prepareStatement(INSERT_NEWS_IN_CATEGORY);
			statementInsertNewsCategory.setInt(1, newsId);
			statementInsertNewsCategory.setInt(2, categoryId);
			statementInsertNewsCategory.executeUpdate();
			
		} catch (SQLException e) {
			throw new UserException("Failed to add news" , e);
		}
		
		
	}
	
}

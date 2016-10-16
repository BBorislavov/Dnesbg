package com.topnews.modelsDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.topnews.exceptions.CategoryException;
import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.NewsException;
import com.topnews.exceptions.UserException;
import com.topnews.models.Category;

public class CategoryDAO extends AbstractDAO {

	static final String GET_CATEGORY_ID = "SELECT subcategory_id FROM news_db.categories WHERE name = ?;";
	static final String SHOW_CATEGORIES = "SELECT categories.name FROM categories ORDER BY category_id;";
	private static final String GET_SUBCATEGORIES = "SELECT sub.name FROM news_db.categories c"
			+ " LEFT OUTER JOIN (news_db.categories sub) ON (c.subcategory_id = sub.category_id)"
			+ " WHERE c.name = ? ORDER BY sub.subcategory_id;";
	private static final String GET_ALL_SUBCATEGORIES = "SELECT name FROM categories WHERE category_id IS NOT NULL;";
	private static final String GET_MAIN_CATEGORIES = "SELECT name FROM news_db.categories WHERE category_id is null AND subcategory_id <> 1 ORDER BY subcategory_id;";
	private static final String DELETE_CATEGORY = "DELETE FROM news_db.categories WHERE subcategory_id=?;";
	private static final String INSERT_CATEGORY = "INSERT INTO news_db.categories VALUES (? , null , ?);";
	private static final String CHECK_CATEGORY_EXISTING = "SELECT COUNT(*) FROM categories WHERE name = ?;";

	public static void addCategory(String category, String subcategory) throws ConnectionException, UserException {
		try {
			PreparedStatement selectStatement = connection.prepareStatement(GET_CATEGORY_ID);
			if (!subcategory.trim().isEmpty()) {
				selectStatement.setString(1, category);
			} else {
				throw new CategoryException("Invalid category name");
			}
			ResultSet resultSet = selectStatement.executeQuery();
			PreparedStatement insertStatement = connection.prepareStatement(INSERT_CATEGORY);
			if (resultSet.next()) {
				int categoryId = resultSet.getInt(1);
				insertStatement.setInt(1, categoryId);
			} else {
				insertStatement.setString(1, null);
			}
			insertStatement.setString(2, subcategory);
			insertStatement.executeUpdate();
		} catch (Exception e) {
			throw new UserException("Failed to add category", e);
		}
	}

	public static void deleteCategory(String name) throws ConnectionException, UserException {

		try {
			PreparedStatement categoryIdStatement = connection.prepareStatement(GET_CATEGORY_ID);
			categoryIdStatement.setString(1, name);
			ResultSet resultSetNews = categoryIdStatement.executeQuery();
			resultSetNews.next();
			int categoryId = resultSetNews.getInt(1);

			PreparedStatement insertStatement = connection.prepareStatement(DELETE_CATEGORY);
			insertStatement.setInt(1, categoryId);
			insertStatement.executeUpdate();

		} catch (SQLException e) {
			throw new UserException("Failed to delete category", e);
		}
	}

	public static List<String> showAllCategories() throws ConnectionException, NewsException {
		try {
			PreparedStatement statement = connection.prepareStatement(SHOW_CATEGORIES);
			ResultSet resultSet = statement.executeQuery();
			List<String> newsInCategory = new ArrayList<String>();
			while (resultSet.next()) {
				String name = resultSet.getString("name");
				newsInCategory.add(name);
			}
			return Collections.unmodifiableList(newsInCategory);
		} catch (Exception e) {
			throw new NewsException("Failed to show categories.", e);
		}
	}

	public static Map<String, List<String>> allCategories() throws ConnectionException, NewsException {
		try {
			PreparedStatement categoryStatement = connection.prepareStatement(GET_MAIN_CATEGORIES);
			ResultSet resultSet = categoryStatement.executeQuery();
			Map<String, ArrayList<String>> newsInCategory = new HashMap<String, ArrayList<String>>();
			while (resultSet.next()) {
				String categoryName = resultSet.getString("name");
				PreparedStatement subStatement = connection.prepareStatement(GET_SUBCATEGORIES);
				subStatement.setString(1, categoryName);
				ResultSet subResultSet = subStatement.executeQuery();
				ArrayList<String> subcategories = new ArrayList<String>();
				while (subResultSet.next()) {
					String subcategoryName = subResultSet.getString("sub.name");
					if (subcategoryName != null) {
						subcategoryName = subcategoryName.replaceAll(" ", "%20");
					}
					subcategories.add(subcategoryName);
				}
				newsInCategory.put(categoryName, subcategories);
			}
			return Collections.unmodifiableMap(newsInCategory);
		} catch (Exception e) {
			throw new NewsException("Failed to show categories.", e);
		}
	}
	
	public static List<String> showAllSubCategories() throws ConnectionException, NewsException {
		try {
			Statement categoryStatement = connection.createStatement();
			ResultSet resultSet = categoryStatement.executeQuery(GET_ALL_SUBCATEGORIES);
			List<String> subcategories = new ArrayList<String>();
			while (resultSet.next()) {
				String categoryName = resultSet.getString("name");
				categoryName = categoryName.replaceAll(" ", "%20");
				subcategories.add(categoryName);
			}
			return Collections.unmodifiableList(subcategories);
		} catch (Exception e) {
			throw new NewsException("Failed to show categories.", e);
		}
	}
	

	public static boolean isCategoryExists(String name) throws CategoryException {
		PreparedStatement categoryIdStatement;
		try {
			categoryIdStatement = connection.prepareStatement(CHECK_CATEGORY_EXISTING);
			categoryIdStatement.setString(1, name);
			ResultSet resultSetNews = categoryIdStatement.executeQuery();
			resultSetNews.next();
			int isExisting = resultSetNews.getInt(1);
			if (isExisting == 1) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new CategoryException("Failed check is category existing.", e);
		}
		return false;
	}

}

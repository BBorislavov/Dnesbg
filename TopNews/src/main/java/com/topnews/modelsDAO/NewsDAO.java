package com.topnews.modelsDAO;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.topnews.exceptions.ConnectionException;
import com.topnews.exceptions.NewsException;
import com.topnews.exceptions.UserException;
import com.topnews.models.INews;
import com.topnews.models.News;

public class NewsDAO {

	private static final String DEFAULT_TITLE = "No title";
	private static final String DEFAULT_TEXT = "No text";
	private static final String BLANK_DATE_TIME = "2016-10-12 06:34:28";
	private static final String DEFAULT_IMAGE_URL = "./news_images/icon-default-news.png";
	private static final String INSERT_NEWS_IN_CATEGORY = "INSERT INTO news_db.news_has_categories VALUES(?,?);";
	private static final String INSERT_PHOTO = "INSERT INTO news_db.photos VALUES(null,?, null, ?);";
	private static final String INSERT_NEWS = "INSERT INTO news_db.news VALUES (null,?,?,?, 0)";
	private static final String GET_NEWS_ID = "SELECT id FROM news_db.news WHERE title = ?;";
	private static final String DELETE_NEWS_FROM_CATEGORY = "DELETE FROM news_db.news_has_categories WHERE news_id = ?;";
	private static final String DELETE_NEWS = "DELETE FROM news_db.news WHERE id = ?;";
	private static final String DELETE_PHOTO = "DELETE FROM news_db.photos WHERE news_id = ?;";
	private static final String SHOW_NEWS_FROM_SUBCATEGORY = "SELECT n.id, n.rating, p.url, n.title, n.text, n.date FROM news_db.news n"
			+ " JOIN news_db.news_has_categories nc" + " ON (n.id=nc.news_id)" + " JOIN news_db.categories c"
			+ " ON (nc.subcategory_id = c.subcategory_id)" + " LEFT OUTER JOIN (news_db.photos p)"
			+ " ON (n.id=p.news_id)" + " WHERE c.name=?;";
	private static final String SHOW_LAST_NEWS_FROM_SUBCATEGORY = "SELECT n.id, n.rating, p.url, n.title, n.date, c.name FROM news_db.news n"
			+ " JOIN news_db.news_has_categories nc" + " ON (n.id=nc.news_id)" + " JOIN news_db.categories c"
			+ " ON (nc.subcategory_id = c.subcategory_id)" + " LEFT OUTER JOIN (news_db.photos p)"
			+ " ON (n.id=p.news_id) WHERE c.name=? ORDER BY n.date DESC LIMIT 10;";
	private static final String SHOW_CURRENT_NEWS = "SELECT p.url, n.title, n.text, n.date, n.rating, c.name FROM news_db.news n"
			+ " JOIN news_db.news_has_categories nc" + " ON (n.id=nc.news_id)" + " JOIN news_db.categories c"
			+ " ON (nc.subcategory_id = c.subcategory_id)" + " LEFT OUTER JOIN (news_db.photos p)"
			+ " ON (n.id=p.news_id)" + " WHERE n.id=?;";
	private static final String SHOW_ALL_NEWS = "SELECT p.url, n.id, n.rating, n.title, n.text, c.name FROM news_db.news n"
			+ " JOIN news_db.news_has_categories nc" + " ON (n.id=nc.news_id)" + " JOIN news_db.categories c"
			+ " ON (nc.subcategory_id = c.subcategory_id)" + " JOIN (news_db.photos p) ON (n.id=p.news_id);";
	private static final String GET_RATING = "SELECT rating+1 as 'increased' FROM news_db.news WHERE id = ?;";
	private static final String INCREASE_RATING = "UPDATE news_db.news SET rating = ? WHERE news.id = ?;";
	private static final String GET_NEWS = "SELECT p.url, n.id, n.rating, n.title, c.name FROM news_db.news n"
			+ " JOIN news_db.news_has_categories nc" + " ON (n.id=nc.news_id)" + " JOIN news_db.categories c"
			+ " ON (nc.subcategory_id = c.subcategory_id)" + " JOIN (news_db.photos p) ON (n.id=p.news_id) ORDER BY n.";
	private static final String DESCENDING = " DESC LIMIT 5;";
	private static final String CHECK_FOR_EXISTING = "SELECT COUNT(*) FROM news WHERE title = ? AND text = ?";

	public static void addNews(INews news, String category, String photoUrl) throws NewsException, ConnectionException {
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
			addPhotoStatement.setString(2, photoUrl);
			addPhotoStatement.executeUpdate();

			PreparedStatement statementGetCategoryId = connection.prepareStatement(CategoryDAO.GET_CATEGORY_ID);
			statementGetCategoryId.setString(1, category);
			ResultSet resultSetCategory = statementGetCategoryId.executeQuery();
			resultSetCategory.next();
			int categoryId = resultSetCategory.getInt(1);

			PreparedStatement statementInsertNewsCategory = connection.prepareStatement(INSERT_NEWS_IN_CATEGORY);
			statementInsertNewsCategory.setInt(1, newsId);
			statementInsertNewsCategory.setInt(2, categoryId);
			statementInsertNewsCategory.executeUpdate();

		} catch (SQLException e) {
			throw new NewsException("Failed to add news", e);
		}
	}

	public static void deleteNews(int id) throws ConnectionException, UserException {
		Connection connection = DBConnection.getInstance().getConnection();

		try {
			PreparedStatement photoStatement = connection.prepareStatement(DELETE_PHOTO);
			photoStatement.setInt(1, id);
			photoStatement.executeUpdate();

			PreparedStatement categoryStatement = connection.prepareStatement(DELETE_NEWS_FROM_CATEGORY);
			categoryStatement.setInt(1, id);
			categoryStatement.executeUpdate();

			PreparedStatement newsStatement = connection.prepareStatement(DELETE_NEWS);
			newsStatement.setInt(1, id);
			newsStatement.executeUpdate();

		} catch (SQLException e) {
			throw new UserException("Failed to delete news", e);
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
				String fullText = resultSet.getString("n.text");
				int rating = resultSet.getInt("n.rating");
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
				News news = new News(title, text, url, date, id, subcategoryName, rating);
				newsInSubcategory.add(news);
			}
			return Collections.unmodifiableList(newsInSubcategory);
		} catch (Exception e) {
			throw new NewsException("Failed to show news.", e);
		}
	}

	public static List<News> showLastNewsInSubcategory(String subcategoryName)
			throws ConnectionException, NewsException {

		Connection connection = DBConnection.getInstance().getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(SHOW_LAST_NEWS_FROM_SUBCATEGORY);
			statement.setString(1, subcategoryName);
			ResultSet resultSet = statement.executeQuery();
			List<News> newsInSubcategory = new ArrayList<News>();
			while (resultSet.next()) {
				String url = resultSet.getString("p.url");
				String title = resultSet.getString("n.title");
				String date = resultSet.getString("n.date");
				int id = resultSet.getInt("n.id");
				int rating = resultSet.getInt("n.rating");
				String category = resultSet.getString("c.name");
				News news = new News(title, "show full news", url, date, id, category, rating);
				newsInSubcategory.add(news);
			}
			return Collections.unmodifiableList(newsInSubcategory);
		} catch (Exception e) {
			throw new NewsException("Failed to show news.", e);
		}
	}

	public static INews showCurrentNews(int id) throws ConnectionException, NewsException {

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
			String category = resultSet.getString("c.name");
			int rating = resultSet.getInt("n.rating");
			INews currentNews = new News(title, text, url, date, id, category, rating);
			return currentNews;
		} catch (Exception e) {
			throw new NewsException("Failed to show current news.", e);
		}
	}

	public static List<INews> showAllNews() throws ConnectionException, NewsException {

		Connection connection = DBConnection.getInstance().getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(SHOW_ALL_NEWS);
			ResultSet resultSet = statement.executeQuery();
			Set<INews> allNews = new HashSet<INews>();
			while (resultSet.next()) {
				String url = resultSet.getString("p.url");
				String title = resultSet.getString("n.title");
				String fullText = resultSet.getString("n.text");
				int id = resultSet.getInt("n.id");
				String category = resultSet.getString("c.name");
				int rating = resultSet.getInt("n.rating");
				int countSize = 0;
				for (int index = 0; index < fullText.length(); index++) {
					if (fullText.charAt(index) != '.') {
						countSize++;
					} else {
						break;
					}
				}
				String text = fullText.substring(0, countSize) + "...";
				INews currentNews = new News(title, text, url, "now", id, category, rating);
				allNews.add(currentNews);
			}
			List<INews> randomNews = new LinkedList<INews>();
			Iterator<INews> iterator = allNews.iterator();
			while (iterator.hasNext()) {
				randomNews.add(iterator.next());
			}
			return randomNews;
		} catch (Exception e) {
			throw new NewsException("Failed to show news.", e);
		}
	}

	public static List<INews> showAllNews(String orderBy) throws ConnectionException, NewsException {

		Connection connection = DBConnection.getInstance().getConnection();
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(GET_NEWS + orderBy + DESCENDING);

			List<INews> orderedNews = new ArrayList<INews>();
			while (resultSet.next()) {
				String url = resultSet.getString("p.url");
				String title = resultSet.getString("n.title");
				int id = resultSet.getInt("n.id");
				String category = resultSet.getString("c.name");
				int rating = resultSet.getInt("n.rating");
				INews currentNews = new News(title, "text", url, "now", id, category, rating);
				orderedNews.add(currentNews);
			}
			return orderedNews;
		} catch (Exception e) {
			throw new NewsException("Failed to show news.", e);
		}
	}

	public static boolean increaseRating(int newsId) {
		try {
			Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement getStatement = connection.prepareStatement(GET_RATING);
			getStatement.setInt(1, newsId);
			ResultSet resultSet = getStatement.executeQuery();
			resultSet.next();
			int rating = resultSet.getInt(1);
			PreparedStatement increaseStatement = connection.prepareStatement(INCREASE_RATING);
			increaseStatement.setInt(1, rating);
			increaseStatement.setInt(2, newsId);
			increaseStatement.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ConnectionException e) {
			e.printStackTrace();
		}
		return false;

	}

	public static List<News> getWorldNews() throws NewsException {
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) new URL(
					"https://newsapi.org/v1/articles?source=cnn&sortBy=top&apiKey=7a41af136e8a49e5b527271957d7dc38")
							.openConnection();
			connection.connect();
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedInputStream bis = new BufferedInputStream(connection.getInputStream());
				byte[] bytes = new byte[connection.getContentLength()];
				bis.read(bytes);
				String json = new String(bytes, "UTF-8");
				List<News> worldNews = new ArrayList<News>();
				JsonObject allNews = (JsonObject) new JsonParser().parse(json);
				JsonArray jsonArray = allNews.getAsJsonArray("articles");
				for (int index = 0; index < jsonArray.size(); index++) {
					JsonObject singleNews = (JsonObject) jsonArray.get(index);

					String title = null;
					if (!singleNews.get("title").isJsonNull()) {
						title = singleNews.get("title").getAsString();
					} else {
						title = DEFAULT_TITLE;
					}

					String text = null;
					if (!singleNews.get("description").isJsonNull()) {
						text = singleNews.get("description").getAsString();
					} else {
						text = DEFAULT_TEXT;
					}

					String photoUrl = null;
					if (!singleNews.get("urlToImage").isJsonNull()) {
						photoUrl = singleNews.get("urlToImage").getAsString();
					} else {
						photoUrl = DEFAULT_IMAGE_URL;
					}
					String date = null;
					if (!singleNews.get("publishedAt").isJsonNull()) {
						date = singleNews.get("publishedAt").getAsString();
					} else {
						date = BLANK_DATE_TIME;
					}

					worldNews.add(new News(title, text, photoUrl, date, 0, "World", 0));
				}
				bis.close();
				return worldNews;
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new NewsException("Failed to show world news", e);
		} finally {
			connection.disconnect();
		}
		return null;
	}

	public static void addWorldNews(News news) throws ConnectionException, NewsException {

		try {
			if (isNotExisting(news)) {
			Connection connection = DBConnection.getInstance().getConnection();
			String title = news.getTitle();
			String text = news.getText();
			String date = news.getDateOfPost().substring(0, news.getDateOfPost().length() - 1);
			String photoUrl = news.getPhotoUrl();
			String category = "World";

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
			addPhotoStatement.setString(2, photoUrl);
			addPhotoStatement.executeUpdate();

			PreparedStatement statementGetCategoryId = connection.prepareStatement(CategoryDAO.GET_CATEGORY_ID);
			statementGetCategoryId.setString(1, category);
			ResultSet resultSetCategory = statementGetCategoryId.executeQuery();
			resultSetCategory.next();
			int categoryId = resultSetCategory.getInt(1);

			PreparedStatement statementInsertNewsCategory = connection.prepareStatement(INSERT_NEWS_IN_CATEGORY);
			statementInsertNewsCategory.setInt(1, newsId);
			statementInsertNewsCategory.setInt(2, categoryId);
			statementInsertNewsCategory.executeUpdate();
			}

		} catch (SQLException e) {
			throw new NewsException("Failed to add news", e);

		}
	}

	private static boolean isNotExisting(News news) throws NewsException {
		try {
			Connection connection = DBConnection.getInstance().getConnection();

			String title = news.getTitle();
			String text = news.getText();

			PreparedStatement statement = connection.prepareStatement(CHECK_FOR_EXISTING);
			statement.setString(1, title);
			statement.setString(2, text);

			ResultSet resultSetNews = statement.executeQuery();
			resultSetNews.next();
			int result = resultSetNews.getInt(1);

			if (result == 0) {
				return true;
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
			throw new NewsException("Failed to check is existing", e);
		}
	}
}

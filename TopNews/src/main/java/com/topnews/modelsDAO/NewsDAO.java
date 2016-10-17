package com.topnews.modelsDAO;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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
import com.topnews.models.Comment;
import com.topnews.models.INews;
import com.topnews.models.IUser;
import com.topnews.models.News;

public class NewsDAO extends AbstractDAO {

	public static final int ALL_NEWS_EXCEPT_FIRST = 9;
	public static final int BONUS_PAGE = 1;
	public static final int NUMBER_OF_NEWS_IN_ONE_PAGE = 10;
	private static final String DEFAULT_TITLE = "No title";
	private static final String DEFAULT_TEXT = "No text";
	private static final String DEFAULT_IMAGE_URL = "./news_images/icon-default-news.png";
	private static final String INSERT_NEWS_IN_CATEGORY = "INSERT INTO news_db.news_has_categories VALUES(?,?);";
	private static final String INSERT_PHOTO = "INSERT INTO news_db.photos VALUES(null,?, null, ?);";
	private static final String INSERT_NEWS = "INSERT INTO news_db.news VALUES (null,?,?,?, 0)";
	public static final String GET_NEWS_ID = "SELECT id FROM news WHERE title = ?;";
	private static final String GET_CATEGORY_ID = "SELECT subcategory_id FROM news_db.categories WHERE name = ?;";
	private static final String DELETE_NEWS_FROM_CATEGORY = "DELETE FROM news_db.news_has_categories WHERE news_id = ?;";
	private static final String DELETE_NEWS = "DELETE FROM news_db.news WHERE id = ?;";
	private static final String DELETE_PHOTO = "DELETE FROM news_db.photos WHERE news_id = ?;";
	private static final String DELETE_FROM_COMMENTS = "DELETE FROM comments WHERE news_id = ?;";
	private static final String DELETE_FROM_FAVOURITES = "DELETE FROM users_has_favourite_news WHERE news_id = ?;";
	private static final String SHOW_NEWS_FROM_SUBCATEGORY = "SELECT n.id, n.rating, p.url, n.title, n.text, n.date FROM news n"
			+ " JOIN news_db.news_has_categories nc" + " ON (n.id=nc.news_id)" + " JOIN news_db.categories c"
			+ " ON (nc.subcategory_id = c.subcategory_id)" + " LEFT OUTER JOIN (news_db.photos p)"
			+ " ON (n.id=p.news_id)" + " WHERE c.name=? ORDER BY n.date DESC;";
	private static final String COUNT_NEWS_FROM_SUBCATEGORY = "SELECT COUNT(*) FROM news n JOIN news_has_categories nc"
			+ " ON (n.id = nc.news_id) JOIN categories c ON (c.subcategory_id = nc.subcategory_id) WHERE c.name = ?;";
	private static final String COUNT_NEWS_FROM_FAVOURITES = "SELECT COUNT(*) FROM users_has_favourite_news uf JOIN users u ON (u.id = uf.users_id) WHERE u.username = ?;";
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
	private static final String GET_NEWS_OF_CATEGORY = "SELECT p.url, n.id, n.rating, n.title, c.name FROM news_db.news n "
			+ "JOIN news_db.news_has_categories nc ON (n.id=nc.news_id) JOIN news_db.categories c ON (nc.subcategory_id = c.subcategory_id) "
			+ "JOIN (news_db.photos p) ON (n.id=p.news_id) WHERE c.category_id = ? ORDER BY n.date DESC LIMIT 6;";
	private static final String GET_FIRST_NEWS_OF_CATEGORY = "SELECT p.url, n.id, n.rating, n.text, n.title, c.name FROM news_db.news n "
			+ "JOIN news_db.news_has_categories nc ON (n.id=nc.news_id) JOIN news_db.categories c ON (nc.subcategory_id = c.subcategory_id) "
			+ "JOIN (news_db.photos p) ON (n.id=p.news_id) WHERE c.category_id = ? ORDER BY n.date DESC LIMIT 1;";
	private static final String DESCENDING = " DESC LIMIT 5;";
	private static final String CHECK_FOR_EXISTING = "SELECT COUNT(*) FROM news WHERE (title = ? AND text = ?) OR (title = ? AND date = ?) OR (text = ? AND date = ?);";
	private static final String GET_OLD_WORLD_NEWS = "SELECT id FROM news n JOIN news_has_categories nc ON (nc.news_id = n.id) JOIN categories c"
			+ " ON (nc.subcategory_id = c.subcategory_id) WHERE c.name='WORLD' AND date<?;";
	private static final String GET_NEWS_WITH_MOST_COMMENTS = "SELECT n.id, ca.name, p.url, n.title, COUNT(c.news_id) comments FROM comments c"
			+ " JOIN news n ON (n.id = c.news_id) JOIN photos p ON (p.news_id = n.id) JOIN news_has_categories nc ON (nc.news_id = n.id) JOIN categories ca"
			+ " ON (ca.subcategory_id = nc.subcategory_id) GROUP BY c.news_id ORDER BY comments DESC LIMIT 12;";
	public static void addNews(INews news, String category, String photoUrl) throws NewsException, ConnectionException {
		try {
			String title = news.getTitle();
			String text = news.getText();
			String date = LocalDateTime.now().toString();

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
			category = category.replaceAll("%20", " ");
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

		try {
			PreparedStatement favouritesStatement = connection.prepareStatement(DELETE_FROM_FAVOURITES);
			favouritesStatement.setInt(1, id);
			favouritesStatement.executeUpdate();

			PreparedStatement photoStatement = connection.prepareStatement(DELETE_PHOTO);
			photoStatement.setInt(1, id);
			photoStatement.executeUpdate();

			PreparedStatement commentStatement = connection.prepareStatement(DELETE_FROM_COMMENTS);
			commentStatement.setInt(1, id);
			commentStatement.executeUpdate();

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

	public static void clearWorldCategory() throws ConnectionException, UserException {

		try {
			PreparedStatement idStatement = connection.prepareStatement(GET_OLD_WORLD_NEWS);
			String fiveDaysPeriod = LocalDateTime.now().minusDays(5).toString();
			idStatement.setString(1, fiveDaysPeriod);
			ResultSet resultSetNews = idStatement.executeQuery();
			int numberOfDeleted = 0;
			while (resultSetNews.next()) {
				numberOfDeleted++;
				int newsId = resultSetNews.getInt(1);
				PreparedStatement photoStatement = connection.prepareStatement(DELETE_PHOTO);
				photoStatement.setInt(1, newsId);
				photoStatement.executeUpdate();

				PreparedStatement categoryStatement = connection.prepareStatement(DELETE_NEWS_FROM_CATEGORY);
				categoryStatement.setInt(1, newsId);
				categoryStatement.executeUpdate();

				PreparedStatement newsStatement = connection.prepareStatement(DELETE_NEWS);
				newsStatement.setInt(1, newsId);
				newsStatement.executeUpdate();
			}
			System.err.print(numberOfDeleted + " news has been deleted. ");
			if (numberOfDeleted == 0) {
				System.err.println("There is no older news by " + fiveDaysPeriod);
			}
		} catch (SQLException e) {
			throw new UserException("Failed to delete news", e);
		}
	}

	public static int getNumberOfPages(String subcategoryName) throws ConnectionException, NewsException {

		try {
			PreparedStatement statement = connection.prepareStatement(COUNT_NEWS_FROM_SUBCATEGORY);
			statement.setString(1, subcategoryName);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			int numberOfNews = resultSet.getInt(1);
			int pages;
			if (numberOfNews % NUMBER_OF_NEWS_IN_ONE_PAGE == 0) {
				pages = numberOfNews / NUMBER_OF_NEWS_IN_ONE_PAGE;
				return pages;
			} else {
				pages = (numberOfNews / NUMBER_OF_NEWS_IN_ONE_PAGE) + BONUS_PAGE;
				return pages;
			}
		} catch (Exception e) {
			throw new NewsException("Failed to count pages.", e);
		}
	}

	public static int getNumberOfPagesForFavourites(IUser user) throws ConnectionException, NewsException {

		try {
			PreparedStatement statement = connection.prepareStatement(COUNT_NEWS_FROM_FAVOURITES);
			statement.setString(1, user.getUsername());
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			int numberOfNews = resultSet.getInt(1);
			int pages;
			if (numberOfNews % NUMBER_OF_NEWS_IN_ONE_PAGE == 0) {
				pages = numberOfNews / NUMBER_OF_NEWS_IN_ONE_PAGE;
				return pages;
			} else {
				pages = (numberOfNews / NUMBER_OF_NEWS_IN_ONE_PAGE) + BONUS_PAGE;
				return pages;
			}
		} catch (Exception e) {
			throw new NewsException("Failed to count pages.", e);
		}
	}

	public static List<News> showNewsInChosenPage(String subcategoryName, int pageNumber)
			throws ConnectionException, NewsException {

		try {
			PreparedStatement statement = connection.prepareStatement(SHOW_NEWS_FROM_SUBCATEGORY);
			statement.setString(1, subcategoryName);
			ResultSet resultSet = statement.executeQuery();
			List<News> newsInChosenPage = new ArrayList<News>();
			int firstIndex = (pageNumber * NUMBER_OF_NEWS_IN_ONE_PAGE) - ALL_NEWS_EXCEPT_FIRST;
			int lastIndex = (pageNumber * NUMBER_OF_NEWS_IN_ONE_PAGE);
			int newsNumber = 0;
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
				newsNumber++;
				if (newsNumber >= firstIndex && newsNumber <= lastIndex) {
					newsInChosenPage.add(new News(title, text, url, date, id, subcategoryName, rating));
				}
			}
			return Collections.unmodifiableList(newsInChosenPage);
		} catch (Exception e) {
			throw new NewsException("Failed to show news.", e);
		}
	}

	public static List<News> showLastNewsInSubcategory(String subcategoryName)
			throws ConnectionException, NewsException {

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

	public static List<INews> showNewsInCategpry(String category) throws ConnectionException, NewsException {

		try {
			PreparedStatement getNewsIdStatement = connection.prepareStatement(GET_CATEGORY_ID);
			getNewsIdStatement.setString(1, category);
			ResultSet idResultSet = getNewsIdStatement.executeQuery();
			idResultSet.next();
			int id = idResultSet.getInt(1);

			PreparedStatement newsStatement = connection.prepareStatement(GET_NEWS_OF_CATEGORY);
			newsStatement.setInt(1, id);
			ResultSet newsResultSet = newsStatement.executeQuery();

			List<INews> newsInCategory = new ArrayList<INews>();
			int newsNumber = 0;
			while (newsResultSet.next()) {
				newsNumber++;
				if (newsNumber > 1) {
					String url = newsResultSet.getString("p.url");
					String title = newsResultSet.getString("n.title");
					int newsId = newsResultSet.getInt("n.id");
					String newsCategory = newsResultSet.getString("c.name");
					int rating = newsResultSet.getInt("n.rating");
					INews currentNews = new News(title, "text", url, "now", newsId, newsCategory, rating);
					newsInCategory.add(currentNews);
				}
			}
			return newsInCategory;
		} catch (Exception e) {
			throw new NewsException("Failed to show news.", e);
		}
	}

	public static INews showFirstNewsInCategpry(String category) throws ConnectionException, NewsException {

		try {
			PreparedStatement getNewsIdStatement = connection.prepareStatement(GET_CATEGORY_ID);
			getNewsIdStatement.setString(1, category);
			ResultSet idResultSet = getNewsIdStatement.executeQuery();
			idResultSet.next();
			int id = idResultSet.getInt(1);

			PreparedStatement newsStatement = connection.prepareStatement(GET_FIRST_NEWS_OF_CATEGORY);
			newsStatement.setInt(1, id);
			ResultSet newsResultSet = newsStatement.executeQuery();

			if (newsResultSet.next()) {
				String url = newsResultSet.getString("p.url");
				String title = newsResultSet.getString("n.title");
				int newsId = newsResultSet.getInt("n.id");
				String newsCategory = newsResultSet.getString("c.name");
				int rating = newsResultSet.getInt("n.rating");
				String fullText = newsResultSet.getString("n.text");
				int countPoints = 0;
				int countSize = 0;
				for (int index = 0; index < fullText.length(); index++) {
					countSize++;
					if (fullText.charAt(index) == '.') {
						countPoints++;
						if (countPoints == 2) {
							break;
						}
					}
				}
				String text = fullText.substring(0, countSize) + "...";
				INews currentNews = new News(title, text, url, "now", newsId, newsCategory, rating);
				return currentNews;
			}
			
		} catch (Exception e) {
			throw new NewsException("Failed to show news.", e);
		}
		return null;
	}

	public static boolean increaseRating(int newsId) {
		try {
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
						date = LocalDateTime.now().toString();
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

			String title = news.getTitle();
			String text = news.getText();
			String date = news.getDateOfPost().substring(0, news.getDateOfPost().length() - 1);

			PreparedStatement statement = connection.prepareStatement(CHECK_FOR_EXISTING);
			// CHECK BY TITLE AND TEXT
			statement.setString(1, title);
			statement.setString(2, text);
			// CHECK BY TITLE AND DATE
			statement.setString(3, title);
			statement.setString(4, date);
			// CHECK BY TEXT AND DATE
			statement.setString(5, text);
			statement.setString(6, date);

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

	public static int getNewsId(INews currentNews) throws UserException {
		try {
			PreparedStatement getNewsIdStatement = connection.prepareStatement(GET_NEWS_ID);
			getNewsIdStatement.setString(1, currentNews.getTitle());
			ResultSet newsResultSet = getNewsIdStatement.executeQuery();
			newsResultSet.next();
			int newsId = newsResultSet.getInt(1);
			return newsId;
		} catch (SQLException e) {
			throw new UserException("Failed to check news id.", e);
		}
	}
	
	public static List<INews> getNewsWithMostComments() throws ConnectionException, NewsException {

		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(GET_NEWS_WITH_MOST_COMMENTS);
			List<INews> showCommentsUnderNews = new ArrayList<INews>();
			while (resultSet.next()) {
				int newsId = resultSet.getInt(1);
				String category = resultSet.getString(2);
				String photoUrl = resultSet.getString(3);
				String title = resultSet.getString(4);
				int numberOfComments = resultSet.getInt(5);
				showCommentsUnderNews.add(new News(title, "text", photoUrl, "date", newsId, category, numberOfComments));
			}
			return Collections.unmodifiableList(showCommentsUnderNews);
		} catch (Exception e) {
			throw new NewsException("Failed to show news with most comments.", e);
		}
	}

}

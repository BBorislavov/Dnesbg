//package model;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//
//import DnesbgDAO.DBConnection;
//import exceptions.ConnectionException;
//import exceptions.UserException;
//import interfaces.IUser;
//
//public class Demo {
//
//	private static final String INSERT_USER_IN_SQL = "INSERT INTO news_db.users VALUES (null,?,?,?,false);";
//
//	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//	private static final String DB_URL = "jdbc:mysql://localhost:3306/news_db";
//	private static final String USER = "root";
//	private static final String PASS = "9509191704";
//
//	public static void main(String[] args) {
//		try {
//			registerUser(new User("Nisadaski", "Peshvdfo-hakera", "niki_tomvfdvitov@abv.bg"));
//		} catch (ConnectionException e) {
//			e.printStackTrace();
//		} catch (UserException e) {
//			e.printStackTrace();
//		}
//	}
//	public static void registerUser(IUser user) throws ConnectionException {
//		
//		Connection connection = DBConnection.getInstance().getConnection();
//		PreparedStatement st = null;
//
//		try {
//			st = connection.prepareStatement(INSERT_USER_IN_SQL);
//			st.setString(1, user.getUsername());
//			st.setString(2, user.getPassword());
//			st.setString(3, user.getEmail());
//
//			st.executeUpdate();
//		} catch (Exception e) {
//			e.printStackTrace();
//
//		}
//	}
//}

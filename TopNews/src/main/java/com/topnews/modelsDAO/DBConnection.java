package com.topnews.modelsDAO;

import java.sql.Connection;
import java.sql.DriverManager;

import com.topnews.exceptions.ConnectionException;

public class DBConnection {
	private static DBConnection instance;
	
	private Connection connection;
	
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://";
	private static final String USER = "admin";
	private static final String PASS = "admin";
	private static final String DB_SCHEMA = "news_db";
	private static final String DB_PORT = "3306/";
	private static final String DB_HOST = "localhost:";
	
	private DBConnection() throws Exception {
		Class.forName(JDBC_DRIVER);
		this.connection = DriverManager.getConnection(DB_URL + DB_HOST + DB_PORT + DB_SCHEMA, USER, PASS);
	}
	
	
	public static DBConnection getInstance() throws ConnectionException {
		if (instance == null) {
			try {
				instance = new DBConnection();
			} catch (Exception e) {
				e.printStackTrace();
				throw new ConnectionException("Start connection failed. Try again.");
			}
		}
		return instance;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
}

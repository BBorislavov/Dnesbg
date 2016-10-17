package com.topnews.modelsDAO;

import java.sql.Connection;

import com.topnews.exceptions.ConnectionException;

public abstract class AbstractDAO {
	protected static Connection connection;
	
	static {
		 try {
				connection = DBConnection.getInstance().getConnection();
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
}

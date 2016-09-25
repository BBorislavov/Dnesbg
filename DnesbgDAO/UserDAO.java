package DnesbgDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

import exceptions.ConnectionException;
import interfaces.IUser;

public class UserDAO {
	
	private static final String INSERT_USER_IN_SQL = "INSERT INTO news_db.users VALUES (null,?,?,?,false);";
	
	
	public static void registerUser(IUser user) throws ConnectionException {
		Connection connection = DBConnection.getInstance().getConnection();
		try {
			String sql = INSERT_USER_IN_SQL;
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getEmail());
			statement.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}

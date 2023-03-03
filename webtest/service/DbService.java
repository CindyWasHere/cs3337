package webtest.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import webtest.model.User;

public class DbService {
	private String dbDriver = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/userdb";
	private String username = "root";
	private String password = "root";
	private Connection connection;

	// Used to connect to MySQL
	public void loadDriver(String dbDriver) {
		try {
			Class.forName(dbDriver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// Used to connect to MySQL
	public Connection getConnection() {
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	// Adds the user
	// We need a function that finds duplicates and sends user an error
	public void addUser(User user) {
		loadDriver(dbDriver);
		Connection connection = getConnection();
		String sql = "insert into userdb.user values(?,?,?)";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, user.getName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Figures out if there is a user under the same email and password and logins
	// them in/sends error
	public boolean loginUser(String email, String password) {
		loadDriver(dbDriver);
		Connection connection = getConnection();
		boolean status = false;
		try {
			PreparedStatement pstmt = connection
					.prepareStatement("select * from userdb.user where email = ? and password = ?");
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			ResultSet rs = pstmt.executeQuery();
			status = rs.next();
		} catch (Exception e) {
		}
		return status;
	}
}
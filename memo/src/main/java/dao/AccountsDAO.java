package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Account;
import model.Login;


public class AccountsDAO {
	private final String JDBC_URL = "jdbc:mariadb://localhost:3306/memo_app";
	private final String DB_USER = "root";
	private final String DB_PASSWORD = "146178";
	
	public Account findByLogin(Login login) {
		Account account = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			throw new IllegalStateException ("JDBCドライバが読み込めませんでした");
		}
		
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)){
			String sql = "SELECT username, password FROM user WHERE username = ? AND password = ?";
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setString(1,  login.getUsername());
			pStmt.setString(2,  login.getPassword());
			
			ResultSet rs = pStmt.executeQuery();
			
			if(rs.next()) {
				String username = rs.getString("username");
				String password = rs.getString("password");
				account = new Account(username, password);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}
		return account;
	}
}

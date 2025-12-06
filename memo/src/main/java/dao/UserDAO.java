package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.User;

public class UserDAO {

    private static final String URL = "jdbc:mariadb://localhost:3306/memo_app";
    private static final String USER = "root";
    private static final String PASSWORD = "146178";

    private static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバが見つかりません。", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static void close(PreparedStatement pstmt, Connection con) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean create(User user) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();

            String sql = "INSERT INTO user (username, password) VALUES (?, ?)";
            pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());

            int result = pstmt.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(pstmt, conn);
        }
    }
}
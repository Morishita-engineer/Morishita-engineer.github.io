package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Memo;

public class MemoDAO {

    private static final String URL = "jdbc:mariadb://localhost:3306/memo_app";
    private static final String USER = "root";
    private static final String PASSWORD = "146178";

    private static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバが見つかりません。", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static void close(ResultSet rs, PreparedStatement pstmt, Connection con) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        close(pstmt, con);
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

    public List<Memo> findByUserId(String userId) {
        List<Memo> memoList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            String sql = "SELECT id, user_id, title, content, created_at, updated_at FROM memos WHERE user_id = ? ORDER BY updated_at DESC";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userId);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Memo memo = new Memo();
                memo.setId(rs.getInt("id"));
                memo.setUserId(rs.getString("user_id"));
                memo.setTitle(rs.getString("title"));
                memo.setContent(rs.getString("content"));
                memo.setCreatedAt(rs.getTimestamp("created_at"));
                memo.setUpdatedAt(rs.getTimestamp("updated_at"));
                memoList.add(memo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(rs, pstmt, conn);
        }
        return memoList;
    }

    public boolean create(Memo memo) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            String sql = "INSERT INTO memos (user_id, title, content) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memo.getUserId());
            pstmt.setString(2, memo.getTitle());
            pstmt.setString(3, memo.getContent());

            int result = pstmt.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(pstmt, conn);
        }
    }

    public Memo findById(int id) {
        Memo memo = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            String sql = "SELECT id, user_id, title, content, created_at, updated_at FROM memos WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                memo = new Memo();
                memo.setId(rs.getInt("id"));
                memo.setUserId(rs.getString("user_id"));
                memo.setTitle(rs.getString("title"));
                memo.setContent(rs.getString("content"));
                memo.setCreatedAt(rs.getTimestamp("created_at"));
                memo.setUpdatedAt(rs.getTimestamp("updated_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            close(rs, pstmt, conn);
        }
        return memo;
    }

    public boolean update(Memo memo) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            String sql = "UPDATE memos SET title = ?, content = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, memo.getTitle());
            pstmt.setString(2, memo.getContent());
            pstmt.setInt(3, memo.getId());

            int result = pstmt.executeUpdate();
            return result == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            close(pstmt, conn);
        }
    }

    public boolean delete(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = getConnection();
            String sql = "DELETE FROM memos WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

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

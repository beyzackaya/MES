package mes.Database;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import mes.model.User;

public class UserDatabase {

    User users = null;

    public List<User> getAllUsers() {
        List<User> user = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM users WHERE 1=1 ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                users = new User();
                users.setUserId(rs.getInt("user_id"));
                users.setUserName(rs.getString("user_lastname"));
                users.setUserLastname(rs.getString("user_name"));
                users.setUsername(rs.getString("username"));
                users.setPassword(rs.getString("password"));
                users.setRoleId(rs.getInt("role_id"));
                users.add(user);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return user;
    }

//        public String login(String username, String password) throws SQLException {
//        String roleName = null;
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//
//        try {
//            conn = DatabaseConnector.getConnection();
//            String sql = "SELECT r.role_name FROM users u "
//                    + "JOIN roles r ON u.role_id = r.role_id "
//                    + "WHERE u.username = ? AND u.password = ?";
//            pstmt = conn.prepareStatement(sql);
//            pstmt.setString(1, username);
//            pstmt.setString(2, password);
//            rs = pstmt.executeQuery();
//
//            if (rs.next()) {
//                roleName = rs.getString("role_name");
//            }
//        } finally {
//            if (rs != null) rs.close();
//            if (pstmt != null) pstmt.close();
//            if (conn != null) conn.close();
//        }
//        return roleName;
//    }
}

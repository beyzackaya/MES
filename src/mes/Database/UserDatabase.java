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

//    public List<User> getAllUsers() {
//        List<User> user = new ArrayList<>();
//
//        try {
//            Connection conn = DatabaseConnector.getConnection();
//            String sql = "SELECT * FROM users ";
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            ResultSet rs = pstmt.executeQuery();
//
//            while (rs.next()) {
//                users = new User();
//                users.setUserId(rs.getInt("user_id"));
//                users.setUserName(rs.getString("user_lastname"));
//                users.setUserLastname(rs.getString("user_name"));
//                users.setUsername(rs.getString("username"));
//                users.setPassword(rs.getString("password"));
//                users.setRoleId(rs.getInt("role_id"));
//                users.add(user);
//            }
//
//            rs.close();
//            pstmt.close();
//            conn.close();
//        } catch (SQLException ex) {
//            ex.printStackTrace();
//        }
//        return user;
//    }
    public List<User> getAllUsers() {
    List<User> userList = new ArrayList<>();

    try (Connection conn = DatabaseConnector.getConnection()) {
        String sql = "SELECT u.user_id, u.user_name, u.user_lastname, u.username, u.password, u.role_id, w.warehouse_id "
                   + "FROM users u "
                   + "JOIN warehouse_employees w ON u.user_id = w.user_id";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setUserName(rs.getString("user_name"));
            user.setUserLastname(rs.getString("user_lastname"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setRoleId(rs.getInt("role_id"));
            user.setWarehouseId(rs.getInt("warehouse_id"));  // Depo ID'sini alıyoruz
            userList.add(user);
        }

        rs.close();
        pstmt.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return userList;
}

}

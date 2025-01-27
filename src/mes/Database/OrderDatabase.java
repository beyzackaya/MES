
package mes.Database;

import java.util.ArrayList;
import java.util.List;
import mes.model.Order;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDatabase {
    
    
    Order order = null;

    public List<Order> getAllRawMaterials() {
        List<Order> orders = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM orders WHERE 1=1 "; // Filtreleme için
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                order = new Order();
                order.setOrderId(rs.getInt("rawproduct_id"));
                order.setOrdeDate(rs.getDate("rawproduct_name"));
                order.setOrderPrice(rs.getDouble("rawproduct_color"));
                order.setOrderQuantity(rs.getInt("rawproduct_stock"));
                order.setOrderStatus(rs.getString("rawproduct_price"));
                order.setOrderProductId(rs.getInt("rawproduct_price"));
                order.setOrderUserId(rs.getInt("rawproduct_price"));
                order.setOrderWareHouseId(rs.getInt("rawproduct_price"));
                
                orders.add(order);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return orders;
    }
    
}

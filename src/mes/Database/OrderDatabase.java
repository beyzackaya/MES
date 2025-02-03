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
            String sql = "SELECT * FROM orders "; 
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setOrdeDate(rs.getDate("order_date"));
                order.setOrderPrice(rs.getDouble("order_price"));
                order.setOrderQuantity(rs.getInt("order_quantity"));
                order.setOrderStatus(rs.getString("order_status"));
                order.setOrderProductId(rs.getInt("product_id"));
                order.setOrderUserId(rs.getInt("user_id"));
                order.setOrderWareHouseId(rs.getInt("warehouse_id"));

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
    public String getExistingEnumValues() throws SQLException {
        Connection conn = DatabaseConnector.getConnection();
        String sql = "SHOW COLUMNS FROM orders LIKE 'company_name';";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        StringBuilder enumValues = new StringBuilder();
        if (rs.next()) {
            String enumType = rs.getString("Type");
            enumType = enumType.substring(enumType.indexOf('(') + 1, enumType.indexOf(')'));
            String[] enumArr = enumType.split(",");
            for (String value : enumArr) {
                enumValues.append("'").append(value.replace("'", "").trim()).append("',");
            }
        }

        rs.close();
        stmt.close();
        conn.close();

        if (enumValues.length() > 0) {
            enumValues.deleteCharAt(enumValues.length() - 1);
        }

        return enumValues.toString();
    }

}

package mes.Database;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import mes.model.Basket;

public class BasketDatabase {

    public int getProductIdByBasketId(int basketId) {
        String query = "SELECT product_id FROM basket WHERE basket_id = ?";

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, basketId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("product_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getQuantityByBasketId(int basketId) {
        String query = "SELECT quantity FROM basket WHERE basket_id = ?";

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, basketId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("quantity");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<Basket> getAllBaskets() {
        List<Basket> baskets = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM  basket";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Basket basket = new Basket();
                basket.setBasketId(rs.getInt("basket_id"));
                basket.setProductId(rs.getInt("product_id"));
                basket.setOrderId(rs.getInt("order_id"));
                basket.setQuantity(rs.getInt("quantity"));
                basket.setWarehouseId(rs.getInt("warehouse_id"));
                basket.setStatus(rs.getString("status"));
                basket.setOrderPrice(rs.getInt("order_price"));

                baskets.add(basket);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return baskets;
    }

}

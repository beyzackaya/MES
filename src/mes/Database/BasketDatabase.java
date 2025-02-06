/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mes.Database;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import mes.model.Basket;

/**
 *
 * @author beyzackaya
 */
public class BasketDatabase {
    
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

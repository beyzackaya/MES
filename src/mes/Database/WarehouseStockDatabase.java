package mes.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mes.model.WarehouseStock;

public class WarehouseStockDatabase {

    WarehouseStock warehouseStock = null;
    public boolean updateWarehouseStock(int productId, int warehouseId, int quantityToAdd) {
    String query = """
        INSERT INTO warehouse_stock (product_id, warehouse_id, quantity_in_stock)
        VALUES (?, ?, ?)
        ON DUPLICATE KEY UPDATE quantity_in_stock = quantity_in_stock + ?;
    """;
    try (Connection conn = DatabaseConnector.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, productId);
        stmt.setInt(2, warehouseId);
        stmt.setInt(3, quantityToAdd);
        stmt.setInt(4, quantityToAdd);

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0; // Başarılı ise true döner
    } catch (SQLException e) {
        e.printStackTrace();
        return false; // Hata durumunda false döner
    }
}
    
    public List<Map<String, Object>> getWarehousesForProduct(int productId) {
    List<Map<String, Object>> warehouseList = new ArrayList<>();
    String query = """
        SELECT 
            w.warehouse_name, 
            COALESCE(ws.quantity_in_stock, 0) AS quantity_in_stock
        FROM warehouses w
        LEFT JOIN warehouse_stock ws ON w.warehouse_id = ws.warehouse_id AND ws.product_id = ?;
    """;

    try (Connection conn = DatabaseConnector.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, productId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Map<String, Object> warehouseData = new HashMap<>();
            warehouseData.put("warehouse_name", rs.getString("warehouse_name"));
            warehouseData.put("quantity_in_stock", rs.getInt("quantity_in_stock"));
            warehouseList.add(warehouseData);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return warehouseList;
}


    public List<WarehouseStock> getAllWarehouseStock() {
        List<WarehouseStock> warehouseStocks = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * warehouse_stock "; 
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                warehouseStock = new WarehouseStock();
                warehouseStock.setWarehouseStockId(rs.getInt("warehouse_stock_id"));
                warehouseStock.setWarehouseStockQuantityInStock(rs.getInt("quantity_in_stock"));
                warehouseStock.setWarehouseStockWarehouseId(rs.getInt("warehouse_id"));
                warehouseStock.setWarehouseStockproductId(rs.getInt("product_id"));
                warehouseStocks.add(warehouseStock);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return warehouseStocks;
    }

}

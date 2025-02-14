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

//    public boolean updateWarehouseStock(int productId, int warehouseId, int quantityToAdd) {
//        String query = """
//        INSERT INTO warehouse_stock (product_id, warehouse_id, quantity_in_stock)
//        VALUES (?, ?, ?)
//        ON DUPLICATE KEY UPDATE quantity_in_stock = quantity_in_stock + ?;
//    """;
//        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setInt(1, productId);
//            stmt.setInt(2, warehouseId);
//            stmt.setInt(3, quantityToAdd);
//            stmt.setInt(4, quantityToAdd);
//
//            int rowsAffected = stmt.executeUpdate();
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    public List<Map<String, Object>> getWarehousesForProduct(int productId) {
        List<Map<String, Object>> warehouseList = new ArrayList<>();
        String query = """
        SELECT 
            w.warehouse_name, 
            COALESCE(ws.quantity_in_stock, 0) AS quantity_in_stock
        FROM warehouses w
        LEFT JOIN warehouse_stock ws ON w.warehouse_id = ws.warehouse_id AND ws.product_id = ?;
    """;

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
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
    
    
public boolean insertWarehouseStock(int productId, int warehouseId, int quantityChange) {
    int currentStock = getStockForProduct(warehouseId, productId);
    
    if (currentStock == -1) {
        System.out.println("Stok bilgisi bulunamadı! Yeni kayıt ekleniyor...");
        return insertNewStock(productId, warehouseId, quantityChange);
    }

    int newStock = currentStock + quantityChange;
    
    String query = "UPDATE warehouse_stock SET quantity_in_stock = ? WHERE product_id = ? AND warehouse_id = ?";

    try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, newStock);
        stmt.setInt(2, productId);
        stmt.setInt(3, warehouseId);

        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
public boolean decreaseWarehouseStock(int productId, int warehouseId, int quantityChange) {
    int currentStock = getStockForProduct(warehouseId, productId);
    
    if (currentStock == -1) {
        System.out.println("Stok bilgisi bulunamadı! ");
    }

    int newStock = currentStock - quantityChange;
    
    String query = "UPDATE warehouse_stock SET quantity_in_stock = ? WHERE product_id = ? AND warehouse_id = ?";

    try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, newStock);
        stmt.setInt(2, productId);
        stmt.setInt(3, warehouseId);

        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}



public int getStockForProduct(int warehouseId, int productId) {
    String query = "SELECT quantity_in_stock FROM warehouse_stock WHERE warehouse_id = ? AND product_id = ?";
    
    try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, warehouseId);
        stmt.setInt(2, productId);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            return rs.getInt("quantity_in_stock");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1; // Eğer stok bilgisi yoksa -1 döndür
}
public boolean insertNewStock(int productId, int warehouseId, int quantity) {
    String query = "INSERT INTO warehouse_stock (product_id, warehouse_id, quantity_in_stock) VALUES (?, ?, ?)";

    try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, productId);
        stmt.setInt(2, warehouseId);
        stmt.setInt(3, quantity);

        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
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
            int stock= warehouseStock.getWarehouseStockQuantityInStock();
            

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return warehouseStocks;
    }

}

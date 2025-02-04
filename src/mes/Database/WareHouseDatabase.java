package mes.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mes.model.WareHouse;

public class WarehouseDatabase {

    WareHouse warehouses = null;


//    public boolean updateProductTotalStock(int productId) {
//    String query = "SELECT SUM(quantity_in_stock) AS total_stock FROM warehouse_stock WHERE product_id = ?";
//    String updateQuery = "UPDATE products SET product_stock = ? WHERE product_id = ?";
//
//    try (Connection conn = DatabaseConnector.getConnection();
//         PreparedStatement stmt = conn.prepareStatement(query);
//         PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
//
//        stmt.setInt(1, productId);
//        ResultSet rs = stmt.executeQuery();
//
//        if (rs.next()) {
//            int totalStock = rs.getInt("total_stock");
//
//            // Eğer depolarda hiç stok yoksa 0 olarak güncelle
//            updateStmt.setInt(1, totalStock > 0 ? totalStock : 0);
//            updateStmt.setInt(2, productId);
//
//            return updateStmt.executeUpdate() > 0;
//        }
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//    return false;
//}

//
//    public boolean updateStockQuantity(int warehouseId, int productId, int newQuantity) {
//        String query = "UPDATE warehouse_stock SET quantity_in_stock = ? WHERE warehouse_id = ? AND product_id = ?";
//
//        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setInt(1, newQuantity);
//            stmt.setInt(2, warehouseId);
//            stmt.setInt(3, productId);
//            int rowsAffected = stmt.executeUpdate();
//            return rowsAffected > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public int getStockForProduct(int warehouseId, int productId) {
//        String query = "SELECT quantity_in_stock FROM warehouse_stock WHERE warehouse_id = ? AND product_id = ?";
//
//        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setInt(1, warehouseId);
//            stmt.setInt(2, productId);
//            ResultSet rs = stmt.executeQuery();
//            if (rs.next()) {
//                return rs.getInt("quantity_in_stock");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
//
//
//    public boolean processTransfer(int productId, int fromWarehouseId, int toWarehouseId, int transferQuantity) {
//        int fromWarehouseStock = getStockForProduct(fromWarehouseId, productId);
//        System.out.println("Kaynak depo stok: " + fromWarehouseStock);
//
//        int newFromWarehouseStock = fromWarehouseStock - transferQuantity;
//        System.out.println("Yeni kaynak depo stok: " + newFromWarehouseStock);
//
//        int toWarehouseStock = getStockForProduct(toWarehouseId, productId);
//        System.out.println("Hedef depo stok: " + toWarehouseStock);
//
//        int newToWarehouseStock = toWarehouseStock + transferQuantity;
//        System.out.println("Yeni hedef depo stok: " + newToWarehouseStock);
//
//        if (createTransfer(productId, fromWarehouseId, toWarehouseId, transferQuantity)) {
//            System.out.println("Transfer kaydı oluşturuldu.");
//
//            boolean fromUpdated = updateStockQuantity(fromWarehouseId, productId, newFromWarehouseStock);
//            boolean toUpdated = updateStockQuantity(toWarehouseId, productId, newToWarehouseStock);
//
//            if (fromUpdated && toUpdated) {
//                System.out.println("Stok güncellemeleri başarılı.");
//                return true;
//            } else {
//                System.out.println("Stok güncellemeleri başarısız.");
//            }
//        } else {
//            System.out.println("Transfer kaydı oluşturulamadı.");
//        }
//
//        return false;
//    }
//
//    public boolean createTransfer(int productId, int fromWarehouseId, int toWarehouseId, int quantity) {
//        String query = "INSERT INTO warehouse_transfer (product_id, from_warehouse_id, to_warehouse_id, quantity_transferred, transfer_date, transfer_status) VALUES (?, ?, ?, ?, NOW(), 'Pending')";
//
//        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setInt(1, productId);
//            stmt.setInt(2, fromWarehouseId);
//            stmt.setInt(3, toWarehouseId);
//            stmt.setInt(4, quantity);
//
//            return stmt.executeUpdate() > 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
        public int getTotalStockForProduct(int productId) {
        String query = "SELECT SUM(quantity_in_stock) AS total_stock FROM warehouse_stock WHERE product_id = ?";

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total_stock");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

        public boolean updateOrInsertWarehouseStock(int warehouseId, int productId, int quantity) {
        String checkQuery = "SELECT quantity_in_stock FROM warehouse_stock WHERE warehouse_id = ? AND product_id = ?";
        String updateQuery = "UPDATE warehouse_stock SET quantity_in_stock = ? WHERE product_id = ?";
        String insertQuery = "INSERT INTO warehouse_stock (warehouse_id, product_id, quantity_in_stock) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement checkStmt = conn.prepareStatement(checkQuery); PreparedStatement updateStmt = conn.prepareStatement(updateQuery); PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            checkStmt.setInt(1, warehouseId);
            checkStmt.setInt(2, productId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                updateStmt.setInt(1, quantity);
                updateStmt.setInt(2, warehouseId);
                updateStmt.setInt(3, productId);
                return updateStmt.executeUpdate() > 0;
            } else {
                insertStmt.setInt(1, warehouseId);
                insertStmt.setInt(2, productId);
                insertStmt.setInt(3, quantity);
                return insertStmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

        public boolean updateProductStock(int productId, int newStock) {
        String updateQuery = "UPDATE Products SET product_stock = ? WHERE product_id = ? ";
        

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(updateQuery)) {
            stmt.setInt(1, newStock);
            stmt.setInt(2, productId);

            int rowsUpdated = stmt.executeUpdate();

            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getWarehouseNameById(int warehouseId) {
        String warehouseName = null;
        String query = "SELECT warehouse_name FROM warehouses WHERE warehouse_id = ?";
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, warehouseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                warehouseName = rs.getString("warehouse_name");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return warehouseName;
    }

    public String getRawProductNameById(int rawProductId) {
        String rawProductName = null;
        String query = "SELECT rawproduct_name FROM raw_material WHERE rawproduct_id = ?";
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, rawProductId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rawProductName = rs.getString("rawproduct_name");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rawProductName;
    }

    public int getWarehouseIdByName(String warehouseName) {
        String query = "SELECT warehouse_id FROM warehouses WHERE warehouse_name = ?";

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, warehouseName);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("warehouse_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<WareHouse> getAllWarehouses() {
        List<WareHouse> warehouse = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM warehouses ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                warehouses = new WareHouse();
                warehouses.setWarehouseId(rs.getInt("warehouse_id"));
                warehouses.setWarehouseName(rs.getString("warehouse_name"));
                warehouse.add(warehouses);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return warehouse;
    }

}

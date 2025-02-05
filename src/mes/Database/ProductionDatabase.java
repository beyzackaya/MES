package mes.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mes.model.Production;
import java.util.Optional;

public class ProductionDatabase {

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
        return 0;  // Eğer stok bilgisi yoksa 0 döner
    }

    private static void updateWarehouseStockAfterProduction(int productionId) {
        String query = "SELECT product_id, warehouse_id, quantity_produced FROM production WHERE production_id = ?";

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int productId = rs.getInt("product_id");
                int warehouseId = rs.getInt("warehouse_id");
                int quantityProduced = rs.getInt("quantity_produced");

                // WarehouseDatabase içindeki stok güncelleme metodunu çağır
                WarehouseDatabase warehouseDb = new WarehouseDatabase();
                boolean success = warehouseDb.updateOrInsertWarehouseStock(warehouseId, productId, quantityProduced);

                if (success) {
                    System.out.println("Stok güncellendi: Ürün ID = " + productId + ", Depo ID = " + warehouseId + ", Üretilen Miktar = " + quantityProduced);
                } else {
                    System.out.println("Stok güncellenirken hata oluştu!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean updateProductionStatus3(int productionId, String status) {
        String query = "UPDATE production SET status = ? WHERE production_id = ?";
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, status);

            pstmt.setInt(3, productionId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean updateProductionStatus(int productionId, String status) {
        String query = "UPDATE production SET status = ?, start_date = ? WHERE production_id = ?";
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, status);

            if ("In Production".equals(status)) {
                pstmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            } else {
                pstmt.setNull(2, java.sql.Types.DATE);
            }

            pstmt.setInt(3, productionId);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean updateProductionStatus2(int productionId, String status) {
        String query = "UPDATE production SET status = ?, end_date = ? WHERE production_id = ?";
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, status);
            if ("Completed".equals(status)) {
                pstmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            } else {
                pstmt.setNull(2, java.sql.Types.DATE);
            }
            pstmt.setInt(3, productionId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0 && "Completed".equals(status)) {
                // Üretim tamamlandıysa warehouse_stock tablosunu güncelle
                updateWarehouseStockAfterProduction(productionId);
            }
            if (rowsAffected > 0 && "Completed".equals(status)) {
                updateWarehouseStockAfterProduction(productionId);

                WarehouseDatabase warehouseDb = new WarehouseDatabase();

            }

            return rowsAffected > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Optional<Integer> getWarehouseIdByName(String warehouseName) throws SQLException {
        String query = "SELECT warehouse_id FROM warehouses WHERE warehouse_name = ?";
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, warehouseName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(rs.getInt("warehouse_id"));
            }
        }
        return Optional.empty();
    }

    public Optional<Integer> getusernameIdByName(String username) throws SQLException {
        String query = "SELECT user_id FROM users WHERE username = ?";
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(rs.getInt("user_id"));
            }
        }
        return Optional.empty();
    }

    public static List<Production> getProductionsByStatus(String status) {
        List<Production> productions = new ArrayList<>();
        try {
            Connection conn = DatabaseConnector.getConnection();

            String sql = "SELECT * FROM production WHERE status = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Production production = new Production();
                production.setProductionId(rs.getInt("production_id"));
                production.setProductId(rs.getInt("product_id"));
                production.setQuantityProduced(rs.getInt("quantity_produced"));
                production.setWarehouseId(rs.getInt("warehouse_id"));
                production.setRawproductId(rs.getInt("rawproduct_id"));
                production.setStartDate(rs.getDate("start_date"));
                production.setEndDate(rs.getDate("end_date"));
                production.setStatus(rs.getString("status"));
                productions.add(production);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productions;
    }

    public Optional<Integer> getRawProductIdByProductId(int productId) throws SQLException {
        String query = "SELECT rawproduct_id FROM products WHERE product_id = ?";
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(rs.getInt("rawproduct_id"));
            }
        }
        return Optional.empty();
    }

    public boolean createWorkOrder(int productId, int warehouseId, Integer rawProductId, int productionQuantity, String username) throws SQLException {
        String query = "INSERT INTO production (product_id, quantity_produced, warehouse_id, rawproduct_id,username, production_date, status) "
                + "VALUES (?, ?, ?, ?,?, NOW(), 'Pending')";
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            stmt.setInt(2, productionQuantity);
            stmt.setInt(3, warehouseId);
            if (rawProductId != null) {
                stmt.setInt(4, rawProductId);
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }
            stmt.setString(5, username);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Production> getAllProductions() {
        List<Production> productions = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM production";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Production production = new Production();
                production.setProductionId(rs.getInt("production_id"));
                production.setEndDate(rs.getDate("end_date"));
                production.setProductId(rs.getInt("product_id"));
                production.setQuantityProduced(rs.getInt("quantity_produced"));
                production.setRawproductId(rs.getInt("rawproduct_id"));
                production.setStartDate(rs.getDate("start_date"));
                production.setProductionDate(rs.getDate("production_date"));
                production.setStatus(rs.getString("status"));
                production.setWarehouseId(rs.getInt("warehouse_id"));
                production.setUsername(rs.getString("username"));
                productions.add(production);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productions;
    }

    public int getRequiredRawMaterialQuantity(int productId) {
        String query = "SELECT quantity_required FROM product_raw_material WHERE product_id = ?";
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("quantity_required");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getProductIdByProductionId(int productionId) {
        String query = "SELECT product_id FROM production WHERE production_id = ?";
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("product_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getRawMaterialIdByProductId(int productId) {
        String query = "SELECT rawproduct_id FROM product_raw_material WHERE product_id = ?";
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("rawproduct_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int getRawMaterialStock(int rawMaterialId) {
        String query = "SELECT rawproduct_stock FROM raw_material WHERE rawproduct_id = ?";
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, rawMaterialId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("rawproduct_stock");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getProductionQuantityById(int productionId) {
        String query = "SELECT quantity_produced FROM production WHERE production_id = ?";
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productionId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("quantity_produced");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getRawProductIdByName(int rawProductId) {
        String rawProductID = null;
        String query = "SELECT rawproduct_id FROM raw_material WHERE rawproduct_name = ?";
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, rawProductId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                rawProductID = rs.getString("rawproduct_id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rawProductID;
    }

}

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

    public void showTable() {

    }

    public static List<Production> getProductionsByStatus(String status) {
        List<Production> productions = new ArrayList<>();
        try {
            Connection conn = DatabaseConnector.getConnection();

            String sql = "SELECT * FROM production WHERE status = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, status); // status parametresini sorguya bağlıyoruz
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

            if (productions.isEmpty()) {
                System.out.println("No productions found with status: " + status);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productions;
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
            System.out.println("Rows affected: " + rowsAffected);
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
            System.out.println("Rows affected: " + rowsAffected);
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

    public boolean createWorkOrder(int productId, int warehouseId, Integer rawProductId, int productionQuantity) throws SQLException {
        String query = "INSERT INTO production (product_id, quantity_produced, warehouse_id, rawproduct_id, production_date, status) "
                + "VALUES (?, ?, ?, ?, NOW(), 'Pending')";
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            stmt.setInt(2, productionQuantity);
            stmt.setInt(3, warehouseId);
            if (rawProductId != null) {
                stmt.setInt(4, rawProductId);
            } else {
                stmt.setNull(4, java.sql.Types.INTEGER);
            }
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Production> getAllProductions() {
        List<Production> productions = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM production WHERE 1=1 ";
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

}

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

    Production production = null;

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
        String query = "INSERT INTO production (product_id, quantity_produced, warehouse_id, rawproduct_id, start_date, status) "
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
            String sql = "SELECT * FROM production WHERE 1=1 "; // Filtreleme için
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                production = new Production();
                production.setProductionId(rs.getInt("production_id"));
                production.setEndDate(rs.getDate("end_date"));
                production.setProductId(rs.getInt("product_id"));
                production.setQuantityProduced(rs.getInt("quantity_produced"));
                production.setRawproductId(rs.getInt("rawproduct_id"));
                production.setStartDate(rs.getDate("start_date"));
                production.setStatus(rs.getString("status"));
                production.setWarehouseId(rs.getInt("warehouse_id"));
                production.add(productions);
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

package mes.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import mes.model.RawMaterial;

public class RawMaterialDatabase {

    RawMaterial rawMaterial = null;

    public List<RawMaterial> getAllRawMaterials() {
        List<RawMaterial> rawMaterials = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM raw_material ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                rawMaterial = new RawMaterial();
                rawMaterial.setRawProductId(rs.getInt("rawproduct_id"));
                rawMaterial.setRawProductName(rs.getString("rawproduct_name"));
                rawMaterial.setRawProductColor(rs.getString("rawproduct_color"));
                rawMaterial.setRawProductStock(rs.getInt("rawproduct_stock"));
                rawMaterial.setRawProductPrice(rs.getDouble("rawproduct_price"));
                rawMaterials.add(rawMaterial);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return rawMaterials;
    }

    public boolean addRawProduct(String rawProductName, String rawProductColor, int rawProductStock,
            double productPrice) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DatabaseConnector.getConnection();
            conn.setAutoCommit(false);

//            int totalRequiredMaterial = productStock * requiredRawMaterial;
//
//            if (!checkRawMaterialStock(conn, rawProductId, totalRequiredMaterial)) {
//                JOptionPane.showMessageDialog(null, "Yetersiz ham madde stoğu!");
//                return false;
//            }
            String sql = "INSERT INTO raw_material (rawproduct_name, rawproduct_color, rawproduct_stock, rawproduct_price )VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, rawProductName);
            pstmt.setString(2, rawProductColor);
            pstmt.setInt(3, rawProductStock);
            pstmt.setDouble(4, productPrice);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                conn.rollback();
                return false;
            }

//            reduceRawMaterialStock(conn, rawProductId, totalRequiredMaterial);
            conn.commit();
            return true;

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "SQL Hatası: " + ex.getMessage());
            ex.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public boolean deleteProductById(int rawProductId) throws SQLException {
        Connection conn = null;
        PreparedStatement checkStmt = null;
        PreparedStatement deleteStmt = null;

        try {
            conn = DatabaseConnector.getConnection();
            conn.setAutoCommit(false);

            String checkQuery = "SELECT COUNT(*) FROM product_raw_material WHERE rawproduct_id = ?";
            checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, rawProductId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return false;
            }

            String deleteQuery = "DELETE FROM raw_material WHERE rawproduct_id = ?";
            deleteStmt = conn.prepareStatement(deleteQuery);
            deleteStmt.setInt(1, rawProductId);
            int affectedRows = deleteStmt.executeUpdate();

            conn.commit();
            return affectedRows > 0;

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (checkStmt != null) {
                checkStmt.close();
            }
            if (deleteStmt != null) {
                deleteStmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

}

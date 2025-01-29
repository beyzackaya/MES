package mes.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import mes.model.Product;

public class ProductDatabase {

    Product product = null;

    public boolean addProductRawMaterial(Connection conn, int productId, int rawProductId, int quantityRequired) throws SQLException {
        String query = "INSERT INTO product_raw_material (product_id, rawproduct_id, quantity_required) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            stmt.setInt(2, rawProductId);
            stmt.setInt(3, quantityRequired);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteProductById(int productId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;

        try {
            conn = DatabaseConnector.getConnection();
            conn.setAutoCommit(false);

            String deleteRawMaterialQuery = "DELETE FROM product_raw_material WHERE product_id = ?";
            pstmt1 = conn.prepareStatement(deleteRawMaterialQuery);
            pstmt1.setInt(1, productId);
            pstmt1.executeUpdate();

            String deleteProductQuery = "DELETE FROM products WHERE product_id = ?";
            pstmt2 = conn.prepareStatement(deleteProductQuery);
            pstmt2.setInt(1, productId);
            int affectedRows = pstmt2.executeUpdate();

            conn.commit();
            return affectedRows > 0;

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (pstmt1 != null) {
                pstmt1.close();
            }
            if (pstmt2 != null) {
                pstmt2.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }

    public boolean addProduct(String productName, String productGender, String productColor, int productStock,
            String productCategory, double productPrice, int rawProductId, int requiredRawMaterial) {
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

            String sql = "INSERT INTO products (product_name, product_gender, product_color, product_stock, "
                    + "product_category, product_price, rawproduct_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, productName);
            pstmt.setString(2, productGender);
            pstmt.setString(3, productColor);
            pstmt.setInt(4, productStock);
            pstmt.setString(5, productCategory);
            pstmt.setDouble(6, productPrice);
            pstmt.setInt(7, rawProductId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                conn.rollback();
                return false;
            }

            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            int productId = -1;
            if (generatedKeys.next()) {
                productId = generatedKeys.getInt(1);
            } else {
                conn.rollback();
                return false;
            }

            if (!addProductRawMaterial(conn, productId, rawProductId, requiredRawMaterial)) {
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

    private boolean checkRawMaterialStock(Connection conn, int rawProductId, int totalRequiredQuantity) throws SQLException {
        String sql = "SELECT rawproduct_stock FROM raw_material WHERE rawproduct_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, rawProductId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int availableStock = rs.getInt("rawproduct_stock");
                return availableStock >= totalRequiredQuantity;
            }
        }
        return false;
    }

    private void reduceRawMaterialStock(Connection conn, int rawProductId, int usedQuantity) throws SQLException {
        String sql = "UPDATE raw_material SET rawproduct_stock = rawproduct_stock - ? WHERE rawproduct_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, usedQuantity);
            pstmt.setInt(2, rawProductId);
            pstmt.executeUpdate();
        }
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM products ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setProductCategory(rs.getString("product_category"));
                product.setProductColor(rs.getString("product_color"));
                product.setProductGender(rs.getString("product_gender"));
                product.setProductStock(rs.getInt("product_stock"));
                product.setProductPrice(rs.getDouble("product_price"));
                product.setRawProductId(rs.getInt("rawproduct_id"));
                products.add(product);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return products;
    }

    public String getExistingEnumValues() throws SQLException {
        Connection conn = DatabaseConnector.getConnection();
        String sql = "SHOW COLUMNS FROM products LIKE 'product_category';";
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

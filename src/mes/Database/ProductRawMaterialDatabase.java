package mes.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import mes.model.ProductRawMaterial;

public class ProductRawMaterialDatabase {

    ProductRawMaterial productRawMaterial = null;
//public boolean deleteProductById(int productId) throws SQLException {
//    Connection conn = null;
//    PreparedStatement pstmt1 = null;
//    PreparedStatement pstmt2 = null;
//    PreparedStatement checkRelationPstmt = null;
//
//    try {
//        conn = DatabaseConnector.getConnection();
//        conn.setAutoCommit(false); 
//
//        // Hammaddeyi başka bir ürünün kullanıp kullanmadığını kontrol et
//        String checkRelationQuery = "SELECT COUNT(*) FROM product_raw_material WHERE rawproduct_id = ?";
//        checkRelationPstmt = conn.prepareStatement(checkRelationQuery);
//        checkRelationPstmt.setInt(1, productId);
//        ResultSet rs = checkRelationPstmt.executeQuery();
//        
//        if (rs.next()) {
//            int count = rs.getInt(1);
//            if (count > 0) {
//                // Hammadde başka bir ürün tarafından kullanılıyor
//                return false; 
//            }
//        }
//
//        String deleteRawMaterialQuery = "DELETE FROM product_raw_material WHERE rawproduct_id = ?";
//        pstmt1 = conn.prepareStatement(deleteRawMaterialQuery);
//        pstmt1.setInt(1, productId);
//        pstmt1.executeUpdate();
//
//        // Ürünü silme işlemi
//        String deleteProductQuery = "DELETE FROM raw_material WHERE rawproduct_id = ?";
//        pstmt2 = conn.prepareStatement(deleteProductQuery);
//        pstmt2.setInt(1, productId);
//        int affectedRows = pstmt2.executeUpdate();
//
//        conn.commit(); 
//        return affectedRows > 0;
//
//    } catch (SQLException e) {
//        if (conn != null) conn.rollback(); 
//        e.printStackTrace();
//        return false;
//    } finally {
//        if (pstmt1 != null) pstmt1.close();
//        if (pstmt2 != null) pstmt2.close();
//        if (checkRelationPstmt != null) checkRelationPstmt.close();
//        if (conn != null) conn.close();
//    }
//}

    public List<ProductRawMaterial> getAllRoles() {
        List<ProductRawMaterial> productRawMaterials = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM product_raw_material";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                productRawMaterial = new ProductRawMaterial();
                productRawMaterial.setProductId(rs.getInt("product_id"));
                productRawMaterial.setProductRawId(rs.getInt("id"));
                productRawMaterial.setRawId(rs.getInt("rawproduct_id"));
                productRawMaterial.setQuantityRequired(rs.getInt("quantity_required"));

                productRawMaterials.add(productRawMaterial);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productRawMaterials;
    }

}

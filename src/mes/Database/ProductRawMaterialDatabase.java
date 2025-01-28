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

    public List<ProductRawMaterial> getAllRoles() {
        List<ProductRawMaterial> productRawMaterials = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM product_raw_material WHERE 1=1 ";
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

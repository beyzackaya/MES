package mes.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mes.model.RawMaterial;

public class RawMaterialDatabase {

    RawMaterial rawMaterial = null;

    public List<RawMaterial> getAllRawMaterials() {
        List<RawMaterial> rawMaterials = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM raw_material WHERE 1=1 "; // Filtreleme için
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

}

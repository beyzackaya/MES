package mes.Database;

import java.util.ArrayList;
import java.util.List;
import mes.model.Supplier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierDatabase {

    Supplier supplier = null;
    public int getSupplierIdByName(String supplierName) {
        int supplierId = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnector.getConnection();
            String sql = "SELECT supplier_id FROM suppliers WHERE supplier_name = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, supplierName);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                supplierId = rs.getInt("supplier_id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Kaynakları kapatma
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return supplierId;
    }

    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM suppliers ";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                supplier = new Supplier();
                supplier.setSupplierId(rs.getInt("supplier_id"));
                supplier.setSupplierName(rs.getString("supplier_name"));
                supplier.setSupplierAddress(rs.getString("address"));
                supplier.setSupplierContactPerson(rs.getString("contact_person"));
                supplier.setSupplierEmail(rs.getString("email"));
                supplier.setSupplierPhoneNumber(rs.getInt("phone_number"));

                suppliers.add(supplier);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return suppliers;
    }

}

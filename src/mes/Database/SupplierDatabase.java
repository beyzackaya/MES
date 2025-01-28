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

    public List<Supplier> getAllRawMaterials() {
        List<Supplier> suppliers = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM suppliers WHERE 1=1 "; // Filtreleme için
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

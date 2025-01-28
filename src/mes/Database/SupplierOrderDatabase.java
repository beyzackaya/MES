package mes.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mes.model.SupplierOrder;

public class SupplierOrderDatabase {

    SupplierOrder supplierOrder = null;

    public List<SupplierOrder> getAllRawMaterials() {
        List<SupplierOrder> supplierOrders = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * supplier_orders WHERE 1=1 "; // Filtreleme için
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                supplierOrder = new SupplierOrder();
                supplierOrder.setSupplierOrderId(rs.getInt("order_id"));
                supplierOrder.setSupplierExpectedDeliveryDate(rs.getDate("expected_delivery_date"));
                supplierOrder.setSupplierOrderDate(rs.getDate("order_date"));
                supplierOrder.setSupplierOrderQuantity(rs.getInt("order_quantity"));
                supplierOrder.setSupplierOrderStatus(rs.getString("order_status"));
                supplierOrder.setSupplierRawProductId(rs.getInt("rawproduct_id"));
                supplierOrder.setSupplierId(rs.getInt("supplier_id"));
                supplierOrder.setSupplierWarehouseId(rs.getInt("warehouse_id"));
                supplierOrders.add(supplierOrder);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return supplierOrders;
    }

}

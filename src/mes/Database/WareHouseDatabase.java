package mes.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mes.model.WareHouse;

public class WareHouseDatabase {

    WareHouse warehouses = null;

    public List<WareHouse> getAllWarehouses() {
        List<WareHouse> warehouse = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM warehouses WHERE 1=1 "; // Filtreleme için
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                warehouses = new WareHouse();
                warehouses.setWarehouseId(rs.getInt("warehouse_id"));
                warehouses.setWarehouseName(rs.getString("warehouse_name"));
                warehouse.add(warehouses);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return warehouse;
    }

}

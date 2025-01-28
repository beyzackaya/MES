package mes.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mes.model.WarehouseStock;

public class WarehouseStockDatabase {

    WarehouseStock warehouseStock = null;

    public List<WarehouseStock> getAllWarehouseStock() {
        List<WarehouseStock> warehouseStocks = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * warehouse_stock WHERE 1=1 "; // Filtreleme için
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                warehouseStock = new WarehouseStock();
                warehouseStock.setWarehouseStockId(rs.getInt("warehouse_stock_id"));
                warehouseStock.setWarehouseStockQuantityInStock(rs.getInt("quantity_in_stock"));
                warehouseStock.setWarehouseStockWarehouseId(rs.getInt("warehouse_id"));
                warehouseStock.setWarehouseStockproductId(rs.getInt("product_id"));
                warehouseStocks.add(warehouseStock);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return warehouseStocks;
    }

}

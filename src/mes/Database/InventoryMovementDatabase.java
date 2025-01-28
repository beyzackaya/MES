package mes.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mes.model.InventoryMovement;

public class InventoryMovementDatabase {

    InventoryMovement inventoryMovement = null;

    public List<InventoryMovement> getAllInventoryMovements() {
        List<InventoryMovement> inventoryMovements = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM inventory_movement WHERE 1=1 "; // Filtreleme için
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                inventoryMovement = new InventoryMovement();
                inventoryMovement.setMovementId(rs.getInt("movement_id"));
                inventoryMovement.setMovementDate(rs.getDate("movement_date"));
                inventoryMovement.setMovementProductId(rs.getInt("product_id"));
                inventoryMovement.setMovementType(rs.getInt("movement_type"));
                inventoryMovement.setMovementQuantity(rs.getInt("quantity"));
                inventoryMovement.setMovementWarehouseId(rs.getInt("warehouse_id"));
                inventoryMovements.add(inventoryMovement);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return inventoryMovements;
    }

}

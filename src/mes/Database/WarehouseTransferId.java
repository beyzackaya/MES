package mes.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mes.model.WarehouseTransfer;

public class WarehouseTransferId {

    WarehouseTransfer warehouseTransfer = null;

    public List<WarehouseTransfer> getAllWarehouseTransfer() {
        List<WarehouseTransfer> warehouseTransfers = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM warehouse_transfer WHERE 1=1 "; // Filtreleme için
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                warehouseTransfer = new WarehouseTransfer();
                warehouseTransfer.setWarehouseTransferId(rs.getInt("transfer_id"));
                warehouseTransfer.setWarehouseTransferFromWarehouseId(rs.getInt("from_warehouse_id"));
                warehouseTransfer.setWarehouseTransferProductId(rs.getInt("product_id"));
                warehouseTransfer.setWarehouseTransferQuantityTransferred(rs.getInt("quantity_transferred"));
                warehouseTransfer.setWarehouseTransferToWarehouseId(rs.getInt("to_warehouse_id"));
                warehouseTransfer.setWarehouseTransferDate(rs.getDate("transfer_date"));

                warehouseTransfers.add(warehouseTransfer);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return warehouseTransfers;
    }

}

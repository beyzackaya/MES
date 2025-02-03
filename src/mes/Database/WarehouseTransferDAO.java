package mes.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mes.model.WarehouseTransfer;

public class WarehouseTransferDAO {

    WarehouseTransfer warehouseTransfer = null;

    public void addTransfer(WarehouseTransfer transfer) {
        String query = "INSERT INTO warehouse_transfer (from_warehouse_id, to_warehouse_id, product_id, quantity_transferred, transfer_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, transfer.getWarehouseTransferFromWarehouseId());
            pstmt.setInt(2, transfer.getWarehouseTransferId());
            pstmt.setInt(3, transfer.getWarehouseTransferProductId());
            pstmt.setInt(4, transfer.getWarehouseTransferQuantityTransferred());
            pstmt.setDate(5, new java.sql.Date(transfer.getWarehouseTransferDate().getTime()));

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public boolean addTransfer(int productId, int fromWarehouseId, int toWarehouseId, int transferQuantity) {
    String query = "INSERT INTO warehouse_transfer (product_id, from_warehouse_id, to_warehouse_id, quantity_transferred, transfer_date, transfer_status) VALUES (?, ?, ?, ?, NOW(), 'Pending')";

    try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, productId);
        stmt.setInt(2, fromWarehouseId);
        stmt.setInt(3, toWarehouseId);
        stmt.setInt(4, transferQuantity);

        return stmt.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}

    public List<WarehouseTransfer> getAllWarehouseTransfer() {
        List<WarehouseTransfer> warehouseTransfers = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM warehouse_transfer ";
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

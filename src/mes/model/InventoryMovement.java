
package mes.model;

import java.util.Date;
import java.util.List;


public class InventoryMovement {
    private int movementId;
    private Date movementDate;
    private int movementType;
    private int productId;
    private int quantity;
    private int warehouseId;

    public int getMovementId() {
        return movementId;
    }

    public void setMovementId(int movementId) {
        this.movementId = movementId;
    }

    public Date getMovementDate() {
        return movementDate;
    }

    public void setMovementDate(Date movementDate) {
        this.movementDate = movementDate;
    }

    public int getMovementType() {
        return movementType;
    }

    public void setMovementType(int movementType) {
        this.movementType = movementType;
    }

    public int getMovementProductId() {
        return productId;
    }

    public void setMovementProductId(int productId) {
        this.productId = productId;
    }

    public int getMovementQuantity() {
        return quantity;
    }

    public void setMovementQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getMovementWarehouseId() {
        return warehouseId;
    }

    public void setMovementWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public InventoryMovement() {
    }

    public void add(List<InventoryMovement> inventoryMovements) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}

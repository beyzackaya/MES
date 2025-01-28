
package mes.model;

import java.util.Date;


public class WarehouseTransfer {
    private int warehouseTransferId;
    private int warehouseTransferFromWarehouseId;
    private int warehouseTransferProductId;
    private int warehouseTransferQuantityTransferred;
    private int warehouseTransferToWarehouseId;
    private Date warehouseTransferDate;

    public int getWarehouseTransferId() {
        return warehouseTransferId;
    }

    public void setWarehouseTransferId(int warehouseTransferId) {
        this.warehouseTransferId = warehouseTransferId;
    }

    public int getWarehouseTransferFromWarehouseId() {
        return warehouseTransferFromWarehouseId;
    }

    public void setWarehouseTransferFromWarehouseId(int warehouseTransferFromWarehouseId) {
        this.warehouseTransferFromWarehouseId = warehouseTransferFromWarehouseId;
    }

    public int getWarehouseTransferProductId() {
        return warehouseTransferProductId;
    }

    public void setWarehouseTransferProductId(int warehouseTransferProductId) {
        this.warehouseTransferProductId = warehouseTransferProductId;
    }

    public int getWarehouseTransferQuantityTransferred() {
        return warehouseTransferQuantityTransferred;
    }

    public void setWarehouseTransferQuantityTransferred(int warehouseTransferQuantityTransferred) {
        this.warehouseTransferQuantityTransferred = warehouseTransferQuantityTransferred;
    }

    public int getWarehouseTransferToWarehouseId() {
        return warehouseTransferToWarehouseId;
    }

    public void setWarehouseTransferToWarehouseId(int warehouseTransferToWarehouseId) {
        this.warehouseTransferToWarehouseId = warehouseTransferToWarehouseId;
    }

    public Date getWarehouseTransferDate() {
        return warehouseTransferDate;
    }

    public void setWarehouseTransferDate(Date warehouseTransferDate) {
        this.warehouseTransferDate = warehouseTransferDate;
    }

    public WarehouseTransfer() {
    }
}

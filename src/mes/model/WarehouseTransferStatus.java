
package mes.model;

import java.util.Date;
public class WarehouseTransferStatus {
    private int statusId;
    private int transferId;
    private int employeeId;
    private int fromWarehouseId; 
    private int toWarehouseId;    
    private int productId;        
    private int quantityTransferred; 
    private Date transferDate;  
    private String status;
    private Date actionDate;

    // Getter ve Setter metodları
    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getFromWarehouseId() {
        return fromWarehouseId;
    }

    public void setFromWarehouseId(int fromWarehouseId) {
        this.fromWarehouseId = fromWarehouseId;
    }

    public int getToWarehouseId() {
        return toWarehouseId;
    }

    public void setToWarehouseId(int toWarehouseId) {
        this.toWarehouseId = toWarehouseId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantityTransferred() {
        return quantityTransferred;
    }

    public void setQuantityTransferred(int quantityTransferred) {
        this.quantityTransferred = quantityTransferred;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }
}
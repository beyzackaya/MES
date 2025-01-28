
package mes.model;

import java.util.Date;

public class SupplierOrder {
    private int supplierOrderId;
    private Date supplierExpectedDeliveryDate;
    private Date supplierOrderDate;
    private int supplierOrderQuantity;
    private String supplierOrderStatus;
    private int supplierRawProductId;
    private int supplierId;
    private int supplierWarehouseId;

    public int getSupplierOrderId() {
        return supplierOrderId;
    }

    public void setSupplierOrderId(int supplierOrderId) {
        this.supplierOrderId = supplierOrderId;
    }

    public Date getSupplierExpectedDeliveryDate() {
        return supplierExpectedDeliveryDate;
    }

    public void setSupplierExpectedDeliveryDate(Date supplierExpectedDeliveryDate) {
        this.supplierExpectedDeliveryDate = supplierExpectedDeliveryDate;
    }

    public Date getSupplierOrderDate() {
        return supplierOrderDate;
    }

    public void setSupplierOrderDate(Date supplierOrderDate) {
        this.supplierOrderDate = supplierOrderDate;
    }

    public int getSupplierOrderQuantity() {
        return supplierOrderQuantity;
    }

    public void setSupplierOrderQuantity(int supplierOrderQuantity) {
        this.supplierOrderQuantity = supplierOrderQuantity;
    }

    public String getSupplierOrderStatus() {
        return supplierOrderStatus;
    }

    public void setSupplierOrderStatus(String supplierOrderStatus) {
        this.supplierOrderStatus = supplierOrderStatus;
    }

    public int getSupplierRawProductId() {
        return supplierRawProductId;
    }

    public void setSupplierRawProductId(int supplierRawProductId) {
        this.supplierRawProductId = supplierRawProductId;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public int getSupplierWarehouseId() {
        return supplierWarehouseId;
    }

    public void setSupplierWarehouseId(int supplierWarehouseId) {
        this.supplierWarehouseId = supplierWarehouseId;
    }

    public SupplierOrder() {
    }
    
    
}

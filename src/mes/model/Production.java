
package mes.model;

import java.util.Date;
import java.util.List;


public class Production {
    private int production_id;
    private Date end_date;
    private Date start_date;
    private int product_id;
    private int quantity_produced;
    private int rawproduct_id;
    private String status;
    private int warehouse_id;
    private Date productionDate;

    public Date getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(Date productionDate) {
        this.productionDate = productionDate;
    }

    

    public Production() {
    }

    public int getProductionId() {
        return production_id;
    }

    public void setProductionId(int production_id) {
        this.production_id = production_id;
    }

    public Date getEndDate() {
        return end_date;
    }

    public void setEndDate(Date end_date) {
        this.end_date = end_date;
    }

    public Date getStartDate() {
        return start_date;
    }

    public void setStartDate(Date start_date) {
        this.start_date = start_date;
    }

    public int getProductId() {
        return product_id;
    }

    public void setProductId(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantityProduced() {
        return quantity_produced;
    }

    public void setQuantityProduced(int quantity_produced) {
        this.quantity_produced = quantity_produced;
    }

    public int getRawproductId() {
        return rawproduct_id;
    }

    public void setRawproductId(int rawproduct_id) {
        this.rawproduct_id = rawproduct_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWarehouseId() {
        return warehouse_id;
    }

    public void setWarehouseId(int warehouse_id) {
        this.warehouse_id = warehouse_id;
    }

    public void add(List<Production> productions) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
    
}

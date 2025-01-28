
package mes.model;

import java.util.Date;


public class Order {
    private int orderId;
    private Date ordeDate;
    private double orderPrice;
    private int orderQuantity;
    private String orderStatus;
    private int productId;
    private int userId;
    private int wareHouseId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getOrdeDate() {
        return ordeDate;
    }

    public void setOrdeDate(Date ordeDate) {
        this.ordeDate = ordeDate;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderProductId() {
        return productId;
    }

    public void setOrderProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderUserId() {
        return userId;
    }

    public void setOrderUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderWareHouseId() {
        return wareHouseId;
    }

    public void setOrderWareHouseId(int wareHouseId) {
        this.wareHouseId = wareHouseId;
    }

    public Order() {
    }
    
}

package mes.model;

import java.util.ArrayList;
import java.util.List;

public class Basket {

    private int productId;
    private int orderPrice;
    private int quantity;
    private int warehouseId;
    private int basketId;
    private String status;
    private int orderId;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public int getBasketId() {
        return basketId;
    }

    public void setBasketId(int basketId) {
        this.basketId = basketId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    private static Basket instance;
    private List<OrderProducts> basketItems;

    // Singleton: Tekil bir nesne oluşturuyoruz
    public Basket() {
        basketItems = new ArrayList<>();
    }

    public static Basket getInstance() {
        if (instance == null) {
            instance = new Basket();
        }
        return instance;
    }

    public OrderProducts getProductById(int productId, int warehouseId) {
        for (OrderProducts product : basketItems) {
            if (product.getId() == productId && product.getWarehouse_id() == warehouseId) {
                return product;
            }
        }
        return null;
    }

    public void addProduct(OrderProducts product) {
        basketItems.add(product);
    }

    public List<OrderProducts> getBasketItems() {
        return basketItems;
    }
}

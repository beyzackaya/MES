package mes.model;

public class RawMaterial {

    public int getRawProductId() {
        return rawproduct_id;
    }

    public void setRawProductId(int rawproduct_id) {
        this.rawproduct_id = rawproduct_id;
    }

    public String getRawProductColor() {
        return rawproduct_color;
    }

    public void setRawProductColor(String rawproduct_color) {
        this.rawproduct_color = rawproduct_color;
    }

    public String getRawProductName() {
        return rawproduct_name;
    }

    public void setRawProductName(String rawproduct_name) {
        this.rawproduct_name = rawproduct_name;
    }

    public double getRawProductPrice() {
        return rawproduct_price;
    }

    public void setRawProductPrice(double rawproduct_price) {
        this.rawproduct_price = rawproduct_price;
    }

    public int getRawProductStock() {
        return rawproduct_stock;
    }

    public void setRawProductStock(int rawproduct_stock) {
        this.rawproduct_stock = rawproduct_stock;
    }
    private int rawproduct_id;
    private String rawproduct_color;
    private String rawproduct_name;
    private double rawproduct_price;
    private int rawproduct_stock;

}

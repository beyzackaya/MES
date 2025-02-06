package mes.model;

public class Product {

    private int product_id;
    private String product_name;
    private int product_stock;
    private double product_price;
    private String product_category;
    private String product_color;
    private String product_gender;
    private int rawProduct_id;

    public int getRawProductId() {
        return rawProduct_id;
    }

    public void setRawProductId(int rawProduct_id) {
        this.rawProduct_id = rawProduct_id;
    }

    public Product() {
      

    }

    public int getProductId() {
        return product_id;
    }

    public int getProductStock() {
        return product_stock;
    }

    public double getProductPrice() {
        return product_price;
    }

    public String getProductName() {
        return product_name;
    }

    public String getProductCategory() {
        return product_category;
    }

    public String getProductColor() {
        return product_color;
    }

    public String getProductGender() {
        return product_gender;
    }

    public void setProductId(int product_id) {
        this.product_id = product_id;
    }

    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    public void setProductCategory(String product_category) {
        this.product_category = product_category;
    }

    public void setProductCategory(int product_stock) {
        this.product_stock = product_stock;
    }

    public void setProductPrice(double product_price) {
        this.product_price = product_price;
    }

    public void setProductColor(String product_color) {
        this.product_color = product_color;
    }

    public void setProductGender(String product_gender) {
        this.product_gender = product_gender;
    }

    public void setProductStock(int product_stock) {
        this.product_stock = product_stock;
    }

}

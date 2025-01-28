
package mes.model;


public class ProductRawMaterial {
    private int productRawId;
    private int productId;
    private int rawId;
    private int quantityRequired;

    public int getProductRawId() {
        return productRawId;
    }

    public void setProductRawId(int productRawId) {
        this.productRawId = productRawId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getRawId() {
        return rawId;
    }

    public void setRawId(int rawId) {
        this.rawId = rawId;
    }

    public int getQuantityRequired() {
        return quantityRequired;
    }

    public void setQuantityRequired(int quantityRequired) {
        this.quantityRequired = quantityRequired;
    }

    public ProductRawMaterial() {
    }
    
}

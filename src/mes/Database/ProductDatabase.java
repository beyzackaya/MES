package mes.Database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mes.model.Product;

public class ProductDatabase {

    Product product = null;

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM products WHERE 1=1 "; // Filtreleme için
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                product = new Product();
                product.setProductId(rs.getInt("product_id"));
                product.setProductName(rs.getString("product_name"));
                product.setProductCategory(rs.getString("product_category"));
                product.setProductColor(rs.getString("product_color"));
                product.setProductGender(rs.getString("product_gender"));
                product.setProductStock(rs.getInt("product_stock"));
                product.setProductPrice(rs.getDouble("product_price"));
                product.setRawProductId(rs.getInt("rawproduct_id"));
                products.add(product);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return products;
    }
}

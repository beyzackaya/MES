
package mes.Database;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mes.model.Customer;


public class CustomerDatabase {
    public void addProductToBasket(int productId, int warehouseId, int quantity, double price, int orderId) {
    String query = "INSERT INTO basket (product_id, warehouse_id, quantity, order_price, order_id) VALUES (?, ?, ?, ?, ?)";
    
    try (Connection con = DatabaseConnector.getConnection();
         PreparedStatement pst = con.prepareStatement(query)) {
        pst.setInt(1, productId);
        pst.setInt(2, warehouseId);
        pst.setInt(3, quantity);
        pst.setDouble(4, price);
        pst.setInt(5, orderId);
        pst.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
    public int createOrder(double totalPrice, int customerId, int userId) {
    int orderId = -1;
    String query = "INSERT INTO orders (order_date, order_status, order_price, customer_id,user_id) VALUES (CURDATE(), 'Pending', ?, ?,?)";
    
    try (Connection con = DatabaseConnector.getConnection();
         PreparedStatement pst = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        pst.setDouble(1, totalPrice);
        pst.setInt(2, customerId);
        pst.setInt(3, userId);
        pst.executeUpdate();
        
        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            orderId = rs.getInt(1);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return orderId;
}
    
        public String getNameById(int customerId) {
        String productName = null;
        String query = "SELECT company_name FROM customers WHERE customer_id = ?";
        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                productName = rs.getString("company_name");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productName;
    }

    public int getCustomerIdByCompanyName(String companyName) {
    int customerId = -1;
    String query = "SELECT customer_id FROM customers WHERE company_name = ?";
    try (Connection con = DatabaseConnector.getConnection();
         PreparedStatement pst = con.prepareStatement(query)) {
        pst.setString(1, companyName);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            customerId = rs.getInt("customer_id");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return customerId;
}
        public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();

        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM  customers"; 
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setCompanyName(rs.getString("company_name"));
                customer.setAddress(rs.getString("address"));
                customer.setContact_person(rs.getString("contact_person"));
                customer.setPhoneNumber(rs.getInt("phone_number"));

                customers.add(customer);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return customers;
    }

    
}

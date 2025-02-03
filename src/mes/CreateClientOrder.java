package mes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mes.Database.DatabaseConnector;
import mes.Database.OrderDatabase;


public class CreateClientOrder extends javax.swing.JFrame {

    public CreateClientOrder() {
        initComponents();
        refresh();
        calculateTotalPrice();
        loadCategories();
    }
    DefaultTableModel model = new DefaultTableModel();

    private void calculateTotalPrice() {
        double totalPrice = 0.0;
        DefaultTableModel tblModel = (DefaultTableModel) orderProduct_tbl.getModel();
        int priceColumnIndex = 5;

        for (int i = 0; i < tblModel.getRowCount(); i++) {
            try {
                String priceStr = tblModel.getValueAt(i, priceColumnIndex).toString();
                double price = Double.parseDouble(priceStr.replace(",", ".")); // Eğer fiyat virgülle ayrılmışsa, noktaya dönüştürün
                totalPrice += price;
            } catch (NumberFormatException ex) {
                System.err.println("Hata: Satır " + (i + 1) + " için fiyat geçersiz: " + ex.getMessage());
            }
        }

        oPCtotalPrice.setText(String.valueOf(totalPrice));
    }

    private void loadCategories() {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SHOW COLUMNS FROM orders LIKE 'company_name';";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> orders = new ArrayList<>();

            if (rs.next()) {
                String enumType = rs.getString("Type");
                enumType = enumType.substring(enumType.indexOf('(') + 1, enumType.indexOf(')'));
                String[] enumValues = enumType.split(",");

                for (String value : enumValues) {
                    orders.add(value.replace("'", "").trim());
                }
            }

            companyName_combox.removeAllItems();

            for (String order : orders) {
                companyName_combox.addItem(order);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void refresh() {
        model.addColumn("Product Name");
        model.addColumn("Warehouse");
        model.addColumn("Color");
        model.addColumn("Gender");
        model.addColumn("Quantity");
        model.addColumn("Price");

        try (Connection conn = DatabaseConnector.getConnection()) {
            String query = "SELECT  p.product_id, p.product_name, p.product_color, p.product_gender, b.quantity, b.order_price,b.warehouse_id "
                    + "FROM products p "
                    + "JOIN basket b ON p.product_id = b.product_id";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int warehouseId = rs.getInt("warehouse_id");
                String productName = rs.getString("product_name");
                String color = rs.getString("product_color");
                String gender = rs.getString("product_gender");
                int quantity = rs.getInt("quantity");
                double salePrice = rs.getDouble("order_price");
                double totalPrice = salePrice * quantity;

                model.addRow(new Object[]{productName, warehouseId, color, gender, quantity, totalPrice});
            }

            orderProduct_tbl.setModel(model);

            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        createOrder_btn = new javax.swing.JButton();
        oPCtotalPrice = new javax.swing.JLabel();
        companyName_combox = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        orderProduct_tbl = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel1.setText("Create New Order");

        jPanel2.setBackground(new java.awt.Color(153, 153, 255));

        jLabel2.setFont(new java.awt.Font("Arial Hebrew Scholar", 1, 24)); // NOI18N
        jLabel2.setText("Order Information");

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        jLabel3.setText("Company Name");

        createOrder_btn.setText("Create Order");
        createOrder_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createOrder_btnActionPerformed(evt);
            }
        });

        oPCtotalPrice.setText("jLabel4");

        companyName_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        companyName_combox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                companyName_comboxActionPerformed(evt);
            }
        });

        jButton1.setText("+");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(oPCtotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(createOrder_btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(companyName_combox, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(companyName_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(12, 12, 12)
                .addComponent(oPCtotalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(createOrder_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        orderProduct_tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(orderProduct_tbl);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 699, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(298, 298, 298)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createOrder_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createOrder_btnActionPerformed
        // TODO add your handling code here:
        ManagerMenu menuInstance = new ManagerMenu();  // Menu sınıfından yeni bir nesne oluşturuyoruz
        String username = menuInstance.getUsername();
        calculateTotalPrice();
        double totalPrice = Double.parseDouble(oPCtotalPrice.getText());



    }//GEN-LAST:event_createOrder_btnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         String newComapany = JOptionPane.showInputDialog(this, "Yeni Company Girin:", "Yeni Company Girin", JOptionPane.PLAIN_MESSAGE);

        if (newComapany != null && !newComapany.trim().isEmpty()) {
            try {
                OrderDatabase orders = new OrderDatabase();
                String existingCategories = orders.getExistingEnumValues();

                Connection conn = DatabaseConnector.getConnection();
                String alterSql = "ALTER TABLE orders MODIFY COLUMN company_name ENUM("
                        + existingCategories + ", '" + newComapany.trim() + "');";
                PreparedStatement stmt = conn.prepareStatement(alterSql);
                stmt.executeUpdate();
                stmt.close();
                conn.close();

                companyName_combox.addItem(newComapany.trim());

                // Başarı mesajı
                JOptionPane.showMessageDialog(this, "Company başarıyla eklendi!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Company eklenirken hata oluştu: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Company adı boş olamaz!");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void companyName_comboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_companyName_comboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_companyName_comboxActionPerformed

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CreateClientOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateClientOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateClientOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateClientOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreateClientOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> companyName_combox;
    private javax.swing.JButton createOrder_btn;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel oPCtotalPrice;
    private javax.swing.JTable orderProduct_tbl;
    // End of variables declaration//GEN-END:variables
}

package mes;


import mes.Database.WarehouseDatabase;
import mes.model.Basket;
import mes.model.OrderProducts;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mes.Database.CustomerDatabase;
import mes.Database.UserDatabase;
import mes.model.Customer;


public class CreateOrder extends javax.swing.JFrame {

    public CreateOrder() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        updateBasketTable();
        loadCompanies();
        calculateTotalPrice();

    }
      public String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    
        private void loadCompanies() {
        try {
            CustomerDatabase supplierDatabase = new CustomerDatabase();
            List<Customer> suppliers = supplierDatabase.getAllCustomers();

            Set<String> companies = new HashSet<>();

            for (Customer p : suppliers) {
                if (p.getCompanyName()!= null && !p.getCompanyName().isEmpty()) {
                    companies.add(p.getCompanyName().toLowerCase());
                }
            }

            companyName_combox.removeAllItems();
            companyName_combox.addItem("");
            for (String color : companies) {
                companyName_combox.addItem(capitalizeFirstLetter(color));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Renkler yüklenirken bir hata oluştu: " + ex.getMessage());
        }
    }
        
         private void calculateTotalPrice() {
        double totalPrice = 0.0;
        DefaultTableModel tblModel = (DefaultTableModel) basket_tbl.getModel();
        int priceColumnIndex = 4;

        for (int i = 0; i < tblModel.getRowCount(); i++) {
            try {
                String priceStr = tblModel.getValueAt(i, priceColumnIndex).toString();
                double price = Double.parseDouble(priceStr.replace(",", "."));
                totalPrice += price;
            } catch (NumberFormatException ex) {
                System.err.println("Hata: Satır " + (i + 1) + " için fiyat geçersiz: " + ex.getMessage());
            }
        }

        totalPrice_lbl.setText(String.valueOf(totalPrice));
    }


    public void updateBasketTable() {
        DefaultTableModel model = new DefaultTableModel(new String[]{
            "Product ID", "Name", "Quantity", "Warehouse", "Price",}, 0);

        WarehouseDatabase warehouseDtabase = new WarehouseDatabase();

        for (OrderProducts p : Basket.getInstance().getBasketItems()) {
            model.addRow(new Object[]{
                p.getId(),
                p.getName(),
                p.getQuantity(),
                warehouseDtabase.getWarehouseNameById(p.getWarehouse_id()),
                p.getPrice() * p.getQuantity()
            });
        }
        basket_tbl.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        basket_tbl = new javax.swing.JTable();
        addBasket_btn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        createOrder_btn = new javax.swing.JButton();
        companyName_combox = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        totalPrice_lbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        basket_tbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(basket_tbl);

        addBasket_btn.setText("Sepete Ekle");
        addBasket_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBasket_btnActionPerformed(evt);
            }
        });

        jButton1.setText("Sepeti Güncelle");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        createOrder_btn.setText("Sipariş Oluştur");
        createOrder_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createOrder_btnActionPerformed(evt);
            }
        });

        companyName_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        companyName_combox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                companyName_comboxActionPerformed(evt);
            }
        });

        jButton2.setText("+");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        totalPrice_lbl.setText("Total Price");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 899, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addBasket_btn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                        .addGap(55, 55, 55)
                        .addComponent(createOrder_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(companyName_combox, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(117, 117, 117)
                        .addComponent(totalPrice_lbl, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addBasket_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(createOrder_btn)
                    .addComponent(companyName_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(totalPrice_lbl))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addBasket_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBasket_btnActionPerformed
        // TODO add your handling code here:
        new ProductsOrders().setVisible(true);
    }//GEN-LAST:event_addBasket_btnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        updateBasketTable();
                calculateTotalPrice();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void companyName_comboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_companyName_comboxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_companyName_comboxActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        new NewCustomer().setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void createOrder_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createOrder_btnActionPerformed
        // TODO add your handling code here:
        try {
        CustomerDatabase customerDb = new CustomerDatabase();
        WarehouseDatabase warehouseDb = new WarehouseDatabase();
            UserDatabase userDatabase=new UserDatabase();
        
        String selectedCompany = (String) companyName_combox.getSelectedItem();
        if (selectedCompany == null || selectedCompany.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Lütfen bir firma seçin!");
            return;
        }
        String username = ManagerMenu.getUsername();
            if (username == null || username.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Kullanıcı adı bulunamadı! Lütfen tekrar giriş yapın.", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
        int customerId = customerDb.getCustomerIdByCompanyName(selectedCompany);
        if (customerId == -1) {
            JOptionPane.showMessageDialog(this, "Müşteri bulunamadı!");
            return;
        }
        int userId=userDatabase.getIdByName(username);


        double totalPrice = Double.parseDouble(totalPrice_lbl.getText());
        
        int orderId = customerDb.createOrder(totalPrice, customerId,userId);
        if (orderId == -1) {
            JOptionPane.showMessageDialog(this, "Sipariş oluşturulamadı!");
            return;
        }
        
        DefaultTableModel tblModel = (DefaultTableModel) basket_tbl.getModel();
        for (int i = 0; i < tblModel.getRowCount(); i++) {
            int productId = Integer.parseInt(tblModel.getValueAt(i, 0).toString());
            int quantity = Integer.parseInt(tblModel.getValueAt(i, 2).toString());
            double price = Double.parseDouble(tblModel.getValueAt(i, 4).toString());
            int warehouseId = warehouseDb.getWarehouseIdByName(tblModel.getValueAt(i, 3).toString());
            
            customerDb.addProductToBasket(productId, warehouseId, quantity, price, orderId);
        }

        JOptionPane.showMessageDialog(this, "Sipariş başarıyla oluşturuldu!");
        
        Basket.getInstance().getBasketItems().clear();
        updateBasketTable();
        totalPrice_lbl.setText("0.0");

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Sipariş oluşturulurken hata oluştu: " + ex.getMessage());
    }
    }//GEN-LAST:event_createOrder_btnActionPerformed

    public static void main(String args[]) {       
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CreateOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CreateOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CreateOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CreateOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CreateOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBasket_btn;
    private javax.swing.JTable basket_tbl;
    private javax.swing.JComboBox<String> companyName_combox;
    private javax.swing.JButton createOrder_btn;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel totalPrice_lbl;
    // End of variables declaration//GEN-END:variables
}

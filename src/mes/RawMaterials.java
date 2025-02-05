package mes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mes.Database.DatabaseConnector;
import mes.Database.RawMaterialDatabase;
import mes.model.RawMaterial;

public class RawMaterials extends javax.swing.JFrame {

    public RawMaterials() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        loadColor();
        loadTable();

    }

    private void loadTable() {
        try {
            RawMaterialDatabase rawMaterialDatabase = new RawMaterialDatabase();
            List<RawMaterial> rawmaterial = rawMaterialDatabase.getAllRawMaterials();

            DefaultTableModel model = new DefaultTableModel(new String[]{
                "Product ID", "Name", "Color", "Stock", "Price"
            }, 0);

            for (RawMaterial p : rawmaterial) {
                model.addRow(new Object[]{
                    p.getRawProductId(),
                    p.getRawProductName(),
                    p.getRawProductColor(),
                    p.getRawProductStock(),
                    p.getRawProductPrice()
                });
            }

            rawproducts_tbl.setModel(model);

        } catch (Exception ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Error loading table: " + ex.getMessage());
        }
    }

    private void loadColor() {
        try {
            RawMaterialDatabase rawMaterialDatabase = new RawMaterialDatabase();
            List<RawMaterial> rawMaterial = rawMaterialDatabase.getAllRawMaterials();

            Set<String> colors = new HashSet<>();

            for (RawMaterial p : rawMaterial) {
                if (p.getRawProductColor() != null && !p.getRawProductColor().isEmpty()) {
                    colors.add(p.getRawProductColor());
                }
            }

            color_combox.removeAllItems();
            for (String color : colors) {
                color_combox.addItem(color);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Renkler yüklenirken bir hata oluştu: " + ex.getMessage());
        }
    }

    private void filter() {
        String rawproductName = name_txt.getText().toLowerCase();
        String rawproductColor = color_combox.getSelectedItem() != null ? color_combox.getSelectedItem().toString().toLowerCase() : "";
        String rawproductStockStatus = stock_combox.getSelectedItem() != null ? stock_combox.getSelectedItem().toString() : "";

        try {
            RawMaterialDatabase rawMaterialDatabase = new RawMaterialDatabase();
            List<RawMaterial> allRawProducts = rawMaterialDatabase.getAllRawMaterials();

            List<RawMaterial> filteredProducts = new ArrayList<>();
            for (RawMaterial r : allRawProducts) {
                boolean matches = true;

                if (!rawproductName.isEmpty() && !r.getRawProductName().toLowerCase().contains(rawproductName)) {
                    matches = false;
                }

                if (!rawproductColor.isEmpty() && !r.getRawProductColor().toLowerCase().equals(rawproductColor)) {
                    matches = false;
                }

                if (!rawproductStockStatus.isEmpty()) {
                    switch (rawproductStockStatus) {
                        case "In Stock":
                            if (r.getRawProductStock() <= 0) {
                                matches = false;
                            }
                            break;
                        case "Out of Stock":
                            if (r.getRawProductStock() > 0) {
                                matches = false;
                            }
                            break;
                        case "Low Stock":
                            if (r.getRawProductStock() > 3) {
                                matches = false;
                            }
                            break;
                    }
                }

                if (matches) {
                    filteredProducts.add(r);
                }
            }

            DefaultTableModel model = (DefaultTableModel) rawproducts_tbl.getModel();
            model.setRowCount(0);

            for (RawMaterial r : filteredProducts) {
                model.addRow(new Object[]{
                    r.getRawProductId(),
                    r.getRawProductName(),
                    r.getRawProductColor(),
                    r.getRawProductStock(),
                    r.getRawProductPrice()
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ürünler yüklenirken bir hata oluştu: " + ex.getMessage());
        }
    }


private boolean createSupplierOrder(int supplierId, int rawProductId, int orderQuantity) {
    String priceQuery = "SELECT rawproduct_price FROM raw_material WHERE rawproduct_id = ?";
    String insertQuery = """
        INSERT INTO supplier_orders (supplier_id, rawproduct_id, order_quantity, supplier_order_cost, order_date, order_status)
        VALUES (?, ?, ?, ?, CURDATE(), 'Pending')
    """;

    try (Connection conn = DatabaseConnector.getConnection();
         PreparedStatement priceStmt = conn.prepareStatement(priceQuery);
         PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

        priceStmt.setInt(1, rawProductId);
        ResultSet rs = priceStmt.executeQuery();

        if (rs.next()) {
            double price = rs.getDouble("rawproduct_price");
            double totalCost = price * orderQuantity;

            insertStmt.setInt(1, supplierId);
            insertStmt.setInt(2, rawProductId);
            insertStmt.setInt(3, orderQuantity);
            insertStmt.setDouble(4, totalCost);

            return insertStmt.executeUpdate() > 0;
        }
        return false; 
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    private int getSupplierIdForRawMaterial(int rawProductId) {
        String query = "SELECT supplier_id FROM raw_material WHERE rawproduct_id = ?";

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, rawProductId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("supplier_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        rawproducts_tbl = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        color_combox = new javax.swing.JComboBox<>();
        name_txt = new javax.swing.JTextField();
        stock_combox = new javax.swing.JComboBox<>();
        Filter_btn = new javax.swing.JButton();
        clearFilters_btn = new javax.swing.JButton();
        delete_btn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        createOrder_btn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        jLabel4.setText("jLabel4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        rawproducts_tbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(rawproducts_tbl);

        jLabel5.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel5.setText("Raw Materials");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(174, 174, 174)
                        .addComponent(jLabel5)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        color_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        name_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                name_txtActionPerformed(evt);
            }
        });

        stock_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "In Stock", "Low Stock", "Out of Stock" }));

        Filter_btn.setText("Filter");
        Filter_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Filter_btnActionPerformed(evt);
            }
        });

        clearFilters_btn.setText("Clear Filters");
        clearFilters_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearFilters_btnActionPerformed(evt);
            }
        });

        delete_btn.setText("Delete");
        delete_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_btnActionPerformed(evt);
            }
        });

        jButton1.setText("Add Raw Material");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        createOrder_btn.setText("Create Order");
        createOrder_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createOrder_btnActionPerformed(evt);
            }
        });

        jLabel1.setText("Material Name");

        jLabel2.setText("Color");

        jLabel3.setText("Stok Durumu");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(delete_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1)
                            .addComponent(createOrder_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(clearFilters_btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
                            .addComponent(name_txt)
                            .addComponent(color_combox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(stock_combox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(Filter_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(name_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(color_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stock_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Filter_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearFilters_btn)
                .addGap(77, 77, 77)
                .addComponent(delete_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(createOrder_btn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void Filter_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Filter_btnActionPerformed
        filter();
    }//GEN-LAST:event_Filter_btnActionPerformed

    private void clearFilters_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearFilters_btnActionPerformed
        loadTable();
    }//GEN-LAST:event_clearFilters_btnActionPerformed

    private void delete_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_btnActionPerformed
        deleteProduct();
    }//GEN-LAST:event_delete_btnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new NewRawMaterial().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void createOrder_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createOrder_btnActionPerformed
        int selectedRow = rawproducts_tbl.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen sipariş vermek için bir hammadde seçin!");
            return;
        }

        int rawProductId = (int) rawproducts_tbl.getValueAt(selectedRow, 0);
        String rawProductName = (String) rawproducts_tbl.getValueAt(selectedRow, 1);

        String quantityStr = JOptionPane.showInputDialog(this, rawProductName + " için sipariş edilecek miktarı girin:");

        if (quantityStr == null || quantityStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Sipariş miktarı girilmedi!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int orderQuantity;
        try {
            orderQuantity = Integer.parseInt(quantityStr);
            if (orderQuantity <= 0) {
                JOptionPane.showMessageDialog(this, "Lütfen geçerli bir miktar girin!", "Hata", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Geçersiz miktar girdiniz!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int supplierId = getSupplierIdForRawMaterial(rawProductId);
        if (supplierId == -1) {
            JOptionPane.showMessageDialog(this, "Bu hammadde için tedarikçi bulunamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean orderCreated = createSupplierOrder(supplierId, rawProductId, orderQuantity);

        if (orderCreated) {
            JOptionPane.showMessageDialog(this, "Sipariş başarıyla oluşturuldu!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Sipariş oluşturulamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_createOrder_btnActionPerformed

    private void name_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_name_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_name_txtActionPerformed
    private void deleteProduct() {
        int selectedRow = rawproducts_tbl.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için bir ürün seçin!");
            return;
        }

        int productId = (int) rawproducts_tbl.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bu ürünü silmek istediğinize emin misiniz?",
                "Onay", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                RawMaterialDatabase db = new RawMaterialDatabase();
                boolean success = db.deleteProductById(productId);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Ürün ve ilgili ham madde bilgileri başarıyla silindi!");
                    DefaultTableModel model = (DefaultTableModel) rawproducts_tbl.getModel();
                    model.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(this, "Hammadde başka bir ürün tarafından kullanıldığı için silinemedi!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Silme işlemi sırasında hata oluştu: " + e.getMessage());
            }
        }
    }

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
            java.util.logging.Logger.getLogger(RawMaterials.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RawMaterials.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RawMaterials.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RawMaterials.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RawMaterials().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Filter_btn;
    private javax.swing.JButton clearFilters_btn;
    private javax.swing.JComboBox<String> color_combox;
    private javax.swing.JButton createOrder_btn;
    private javax.swing.JButton delete_btn;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField name_txt;
    private javax.swing.JTable rawproducts_tbl;
    private javax.swing.JComboBox<String> stock_combox;
    // End of variables declaration//GEN-END:variables
}

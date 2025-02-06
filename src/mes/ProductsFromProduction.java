package mes;

import mes.Database.DatabaseConnector;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mes.Database.ProductDatabase;
import mes.Database.ProductionDatabase;
import mes.Database.WarehouseDatabase;
import mes.model.Production;

public class ProductsFromProduction extends javax.swing.JFrame {

    private int warehouseID;

    private ProductsFromProduction() {

    }

    public ProductsFromProduction(int warehouseId) {
        this.warehouseID = warehouseId;
        initComponents();
        loadTable(warehouseId);
        loadProductions();
        filter(warehouseId);
                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);


    }

    WarehouseDatabase warehouseDatabase = new WarehouseDatabase();
    ProductDatabase productDatabase = new ProductDatabase();
    ProductionDatabase productionDatabase=new ProductionDatabase();

    private void loadProductions() {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SHOW COLUMNS FROM production LIKE 'status';";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> categories = new ArrayList<>();

            if (rs.next()) {
                String enumType = rs.getString("Type");
                enumType = enumType.substring(enumType.indexOf('(') + 1, enumType.indexOf(')'));
                String[] enumValues = enumType.split(",");

                for (String value : enumValues) {
                    categories.add(value.replace("'", "").trim());
                }
            }

            status_combox.removeAllItems();
            status_combox.addItem("");

            for (String category : categories) {
                status_combox.addItem(category);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void filter(int warehouseID) {
        String status = status_combox.getSelectedItem() != null ? status_combox.getSelectedItem().toString().trim() : "";

        try {
            ProductionDatabase productionDatabase = new ProductionDatabase();
            List<Production> allProducts = productionDatabase.getAllProductions();

            List<Production> filteredProductions = new ArrayList<>();
            for (Production p : allProducts) {
                boolean matches = true;

                if (!status.isEmpty() && !status.equalsIgnoreCase("") && !p.getStatus().equalsIgnoreCase(status)) {
                    matches = false;
                }
                if (!(p.getWarehouseId() == warehouseID)) {
                    matches = false;
                }

                if (matches) {
                    filteredProductions.add(p);
                }
            }

            DefaultTableModel model = (DefaultTableModel) productsFromProduction.getModel();
            model.setRowCount(0);

            for (Production p : filteredProductions) {
                String productName = productDatabase.getProductNameById(p.getProductId());
                String warehouseName = warehouseDatabase.getWarehouseNameById(p.getWarehouseId());
                String rawProductName = warehouseDatabase.getRawProductNameById(p.getRawproductId());
                model.addRow(new Object[]{
                    p.getProductionId(),
                    productName,
                    p.getQuantityProduced(),
                    warehouseName,
                    rawProductName,
                    p.getStartDate(),
                    p.getEndDate(),
                    p.getStatus()
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ürünler yüklenirken bir hata oluştu: " + ex.getMessage());
        }
    }

    private void loadTable(int warehouseId) {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM production WHERE warehouse_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, warehouseId);
            ResultSet rs = pstmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel(new String[]{
                "Production ID", "Product Name", "Quantity Produced", "Warehouse Name", "Raw Product Name", "Start Date", "End Date", "Status"
            }, 0);

            while (rs.next()) {
                int productionId = rs.getInt("production_id");
                int productId = rs.getInt("product_id");
                int quantityProduced = rs.getInt("quantity_produced");
                int warehouseIdFromDB = rs.getInt("warehouse_id");
                int rawProductId = rs.getInt("rawproduct_id");
                String startDate = rs.getString("start_date");
                String endDate = rs.getString("end_date");
                String status = rs.getString("status");

                // Ürün ve depo isimlerini çekmek için ayrı sorgular
                String productName = productDatabase.getProductNameById(productId);
                String warehouseName = warehouseDatabase.getWarehouseNameById(warehouseIdFromDB);
                String rawProductName = warehouseDatabase.getRawProductNameById(rawProductId);

                model.addRow(new Object[]{productionId, productName, quantityProduced, warehouseName, rawProductName, startDate, endDate, status});
            }

            productsFromProduction.setModel(model);

            rs.close();
            pstmt.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Hata oluştu: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        productsFromProduction = new javax.swing.JTable();
        teslimAlındı_btn = new javax.swing.JButton();
        status_combox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        productsFromProduction.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(productsFromProduction);

        teslimAlındı_btn.setText("Teslim alındı");
        teslimAlındı_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                teslimAlındı_btnActionPerformed(evt);
            }
        });

        status_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        status_combox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                status_comboxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(status_combox, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(teslimAlındı_btn))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 993, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(teslimAlındı_btn)
                    .addComponent(status_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void teslimAlındı_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_teslimAlındı_btnActionPerformed
        // TODO add your handling code here:
        int selectedRow = productsFromProduction.getSelectedRow();


        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen iptal etmek için bir sipariş seçin.");
            return;
        }

        int productionId = (int) productsFromProduction.getValueAt(selectedRow, 0);

        try {
            Connection conn = DatabaseConnector.getConnection();

            String checkStatusQuery = "SELECT status FROM production WHERE production_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkStatusQuery);
            checkStmt.setInt(1, productionId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String currentStatus = rs.getString("status");

                if ("In Production".equalsIgnoreCase(currentStatus) && "Cancelled".equalsIgnoreCase(currentStatus)&& "Pending".equalsIgnoreCase(currentStatus)) {
                    JOptionPane.showMessageDialog(this, "Lütfen tamamlanan siparişlerden birini seçin");
                    return;
                }

                String updateQuery = "UPDATE production SET status = 'Delivered' WHERE production_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setInt(1, productionId);
                int updatedRows = updateStmt.executeUpdate();

                if (updatedRows > 0) {
                    int productId = productionDatabase.getProductIdByProductionId(productionId);
                    int total = warehouseDatabase.getTotalStockForProduct(productId);

                    JOptionPane.showMessageDialog(this, "Sipariş başarıyla teslim alındı!");

                    warehouseDatabase.updateProductStock(productId, total);

                    loadTable(warehouseID);
                } else {
                    JOptionPane.showMessageDialog(this, "Sipariş iptal edilirken hata oluştu.");
                }

                updateStmt.close();
            }

            rs.close();
            checkStmt.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Veritabanı hatası: " + ex.getMessage());
        }

    }//GEN-LAST:event_teslimAlındı_btnActionPerformed

    private void status_comboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_status_comboxActionPerformed
        // TODO add your handling code here:
        filter(warehouseID);
    }//GEN-LAST:event_status_comboxActionPerformed

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
            java.util.logging.Logger.getLogger(ProductsFromProduction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ProductsFromProduction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ProductsFromProduction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProductsFromProduction.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ProductsFromProduction().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable productsFromProduction;
    private javax.swing.JComboBox<String> status_combox;
    private javax.swing.JButton teslimAlındı_btn;
    // End of variables declaration//GEN-END:variables
}

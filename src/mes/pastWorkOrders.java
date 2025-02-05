package mes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import mes.Database.DatabaseConnector;
import mes.Database.ProductDatabase;
import mes.Database.ProductionDatabase;
import mes.Database.WarehouseDatabase;
import mes.model.Production;

public class pastWorkOrders extends javax.swing.JFrame {

    public pastWorkOrders() {
        initComponents();
        loadTable();
        loadProductions();
    }

    private void filter() {
        String status = status_combox.getSelectedItem() != null ? status_combox.getSelectedItem().toString().trim() : "";

        try {
            ProductionDatabase productionDatabase = new ProductionDatabase();
            WarehouseDatabase warehouseDatabase = new WarehouseDatabase();
            ProductDatabase productDatabase = new ProductDatabase();
            List<Production> allProducts = productionDatabase.getAllProductions();

            List<Production> filteredProductions = new ArrayList<>();
            for (Production p : allProducts) {
                boolean matches = true;

                if (!status.isEmpty() && !status.equalsIgnoreCase("") && !p.getStatus().equalsIgnoreCase(status)) {
                    matches = false;
                }

                if (matches) {
                    filteredProductions.add(p);
                }
            }

            DefaultTableModel model = (DefaultTableModel) workOrder_tbl.getModel();
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

    void loadTable() {
        try {
            ProductionDatabase productionDatabase = new ProductionDatabase();
            WarehouseDatabase warehouseDatabase = new WarehouseDatabase();
            ProductDatabase productDatabase = new ProductDatabase();
            List<Production> productions = productionDatabase.getAllProductions();

            DefaultTableModel model = new DefaultTableModel(new String[]{
                "Production ID", "Product Name", "Quantity Produced", "Warehouse Name", "Raw Product ID", "Start Date", "End Date", "Status", "User"
            }, 0);

            for (Production p : productions) {
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
                    p.getStatus(),
                    p.getUsername()
                });
            }

            workOrder_tbl.setModel(model);

        } catch (Exception ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Error loading table: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        workOrder_tbl = new javax.swing.JTable();
        status_combox = new javax.swing.JComboBox<>();
        cancelled_btn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        workOrder_tbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(workOrder_tbl);

        status_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        status_combox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                status_comboxActionPerformed(evt);
            }
        });

        cancelled_btn.setText("İptal Et");
        cancelled_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelled_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(status_combox, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(cancelled_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(404, 404, 404))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1021, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelled_btn)
                    .addComponent(status_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(92, 92, 92))
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

    private void status_comboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_status_comboxActionPerformed
        // TODO add your handling code here:
        filter();
    }//GEN-LAST:event_status_comboxActionPerformed

    private void cancelled_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelled_btnActionPerformed
        // TODO add your handling code here:

        int selectedRow = workOrder_tbl.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen iptal etmek için bir sipariş seçin.");
            return;
        }

        int productionId = (int) workOrder_tbl.getValueAt(selectedRow, 0); 

        try {
            Connection conn = DatabaseConnector.getConnection();

            String checkStatusQuery = "SELECT status FROM production WHERE production_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkStatusQuery);
            checkStmt.setInt(1, productionId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String currentStatus = rs.getString("status");

                if ("Completed".equalsIgnoreCase(currentStatus)) {
                    JOptionPane.showMessageDialog(this, "Geçmiş sipariş iptal edilemez.");
                    return;
                }

                String updateQuery = "UPDATE production SET status = 'Cancelled' WHERE production_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setInt(1, productionId);
                int updatedRows = updateStmt.executeUpdate();

                if (updatedRows > 0) {
                    JOptionPane.showMessageDialog(this, "Sipariş başarıyla iptal edildi!");
                    loadTable(); 
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

    }//GEN-LAST:event_cancelled_btnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(pastWorkOrders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(pastWorkOrders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(pastWorkOrders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(pastWorkOrders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new pastWorkOrders().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelled_btn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> status_combox;
    private javax.swing.JTable workOrder_tbl;
    // End of variables declaration//GEN-END:variables
}

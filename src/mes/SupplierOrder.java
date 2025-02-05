package mes;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.time.Instant;
import javax.swing.JOptionPane;
import mes.Database.DatabaseConnector;

public class SupplierOrder extends javax.swing.JFrame {

    public SupplierOrder() {
        initComponents();
        loadPendingOrders();
        loadPastOrders();
    }

    private void loadPendingOrders() {
        DefaultTableModel model = (DefaultTableModel) supplierOrders_tbl.getModel();
        model.setRowCount(0);

        model.setColumnIdentifiers(new Object[]{
            "Order ID", "Supplier Name", "Raw Material", "Order Quantity","Order Cost", "Order Date", "Delivery Date"
        });

        String query = """
        SELECT so.order_id, s.supplier_name, rm.rawproduct_name, so.order_quantity,so.supplier_order_cost, so.order_date, so.expected_delivery_date
        FROM supplier_orders so
        JOIN suppliers s ON so.supplier_id = s.supplier_id
        JOIN raw_material rm ON so.rawproduct_id = rm.rawproduct_id
        WHERE so.order_status = 'Pending'
    """;

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("order_id"),
                    rs.getString("supplier_name"),
                    rs.getString("rawproduct_name"),
                    rs.getInt("order_quantity"),
                    rs.getInt("supplier_order_cost"),
                    rs.getDate("order_date"),
                    rs.getDate("expected_delivery_date")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Siparişler yüklenirken hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadPastOrders() {
        DefaultTableModel model = (DefaultTableModel) pasSupplierOrders_tbl.getModel();
        model.setRowCount(0);

        model.setColumnIdentifiers(new Object[]{
            "Order ID", "Supplier Name", "Raw Material", "Order Quantity","Order Cost", "Order Date", "Delivery Date", "Status"
        });

        String query = """
        SELECT so.order_id, s.supplier_name, rm.rawproduct_name, so.order_quantity , so.supplier_order_cost, so.order_date, so.expected_delivery_date, so.order_status
        FROM supplier_orders so
        JOIN suppliers s ON so.supplier_id = s.supplier_id
        JOIN raw_material rm ON so.rawproduct_id = rm.rawproduct_id
        WHERE so.order_status = 'Received'
    """;

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("order_id"),
                    rs.getString("supplier_name"),
                    rs.getString("rawproduct_name"),
                    rs.getInt("order_quantity"),
                    rs.getInt("supplier_order_cost"),
                    rs.getDate("order_date"),
                    rs.getDate("expected_delivery_date"),
                    rs.getString("order_status")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Geçmiş siparişler yüklenirken hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        supplierOrders_tbl = new javax.swing.JTable();
        siparişAlındı_btn = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        pasSupplierOrders_tbl = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        supplierOrders_tbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(supplierOrders_tbl);

        siparişAlındı_btn.setText("Sipariş Alındı");
        siparişAlındı_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                siparişAlındı_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                .addComponent(siparişAlındı_btn)
                .addGap(75, 75, 75))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(258, 258, 258)
                        .addComponent(siparişAlındı_btn)))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Given Supplier Orders", jPanel1);

        pasSupplierOrders_tbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(pasSupplierOrders_tbl);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 579, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(228, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Past Supplier Order", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void siparişAlındı_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siparişAlındı_btnActionPerformed

        int selectedRow = supplierOrders_tbl.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen güncellemek için bir sipariş seçin.");
            return;
        }

        int orderId = (int) supplierOrders_tbl.getValueAt(selectedRow, 0); // 0. kolon 'Order ID'

        String updateQuery = "UPDATE supplier_orders SET order_status = 'Received', expected_delivery_date = NOW() WHERE order_id = ?";
        String query = "SELECT rawproduct_id, order_quantity FROM supplier_orders WHERE order_id = ?";
        String updatesql = "UPDATE raw_material SET rawproduct_stock = rawproduct_stock + ? WHERE rawproduct_id = ?";

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement stmt = conn.prepareStatement(updateQuery); PreparedStatement sstt = conn.prepareStatement(query); PreparedStatement stmt3 = conn.prepareStatement(updatesql)) {

            sstt.setInt(1, orderId);
            stmt.setInt(1, orderId);
            int rowsAffected = stmt.executeUpdate();

            ResultSet rs = sstt.executeQuery();
            boolean updated = false;

            while (rs.next()) {
                int materialId = rs.getInt("rawproduct_id");
                int quantity = rs.getInt("order_quantity");

                stmt3.setInt(1, quantity);
                stmt3.setInt(2, materialId);
                int rowsAffected2 = stmt3.executeUpdate();

                if (rowsAffected2 > 0) {
                    updated = true;
                }
            }

            if (rowsAffected > 0 && updated) {
                JOptionPane.showMessageDialog(this, "Sipariş başarıyla alındı ve stok güncellendi!");
                loadPendingOrders();
                loadPastOrders();
            } else {
                JOptionPane.showMessageDialog(this, "Sipariş güncellenirken hata oluştu.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Veritabanı hatası: " + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_siparişAlındı_btnActionPerformed

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
            java.util.logging.Logger.getLogger(SupplierOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SupplierOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SupplierOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SupplierOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SupplierOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable pasSupplierOrders_tbl;
    private javax.swing.JButton siparişAlındı_btn;
    private javax.swing.JTable supplierOrders_tbl;
    // End of variables declaration//GEN-END:variables
}

package mes;

import mes.Database.DatabaseConnector;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mes.Database.OrderDatabase;
import mes.Database.ProductDatabase;

public class WarehouseGivenOrders extends javax.swing.JFrame {

    private int warehouseId;

    public WarehouseGivenOrders(int warehouseId) {
        initComponents();
        this.warehouseId = warehouseId;
        loadTable(this.warehouseId);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private WarehouseGivenOrders() {

    }

    ProductDatabase productDatabase = new ProductDatabase();
    OrderDatabase orderDatabase = new OrderDatabase();

    private void loadTable(int warehouseId) {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM basket WHERE warehouse_id = ? AND status = 'Pending'";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, warehouseId);
            ResultSet rs = pstmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel(new String[]{
                "Sipariş Kodu", "Ürün Adı", "Quantity", "Company Name"
            }, 0);

            while (rs.next()) {
                int basketId = rs.getInt("basket_id");
                int productId = rs.getInt("product_id");
                int quantity = rs.getInt("quantity");
                int orderId = rs.getInt("order_id");

                String product = productDatabase.getProductNameById(productId);
                String customerName = orderDatabase.getCompanyNameByOrderId(orderId);

                model.addRow(new Object[]{basketId, product, quantity, customerName});
            }

            givenOrders_tbl.setModel(model);

            rs.close();
            pstmt.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Hata oluştu: " + ex.getMessage());
        }
    }

    private void updateBasketStatus(int basketId) {
        String sql = "UPDATE basket SET status = 'Delivered' WHERE basket_id = ?";

        try (Connection conn = DatabaseConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, basketId);
            int updatedRows = pstmt.executeUpdate();

            if (updatedRows > 0) {
                JOptionPane.showMessageDialog(this, "Ürün başarıyla teslim edildi!");
                loadTable(warehouseId);
            } else {
                JOptionPane.showMessageDialog(this, "Ürün durumu güncellenemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage(), "SQL Hatası", JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        givenOrders_tbl = new javax.swing.JTable();
        send_btn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        givenOrders_tbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(givenOrders_tbl);

        send_btn.setText("Gönder");
        send_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                send_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addComponent(send_btn)
                .addGap(57, 57, 57))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(117, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addComponent(send_btn)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void send_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_send_btnActionPerformed
        // TODO add your handling code here:
        int selectedRow = givenOrders_tbl.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen bir ürün seçin!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Seçili satırdan ilgili verileri al
        int basketId = (int) givenOrders_tbl.getValueAt(selectedRow, 0);
        updateBasketStatus(basketId);

    }//GEN-LAST:event_send_btnActionPerformed

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
            java.util.logging.Logger.getLogger(WarehouseGivenOrders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WarehouseGivenOrders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WarehouseGivenOrders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WarehouseGivenOrders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WarehouseGivenOrders().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable givenOrders_tbl;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton send_btn;
    // End of variables declaration//GEN-END:variables
}

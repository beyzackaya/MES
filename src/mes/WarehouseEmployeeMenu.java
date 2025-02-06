
package mes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mes.Database.DatabaseConnector;


public class WarehouseEmployeeMenu extends javax.swing.JFrame {
    private int employeeId;
    private int warehouseId;

    public WarehouseEmployeeMenu(int employeeId) {
        this.employeeId = employeeId;
        this.warehouseId = getWarehouseIdByEmployee(employeeId); // Çalışanın deposunu al
        initComponents();
    }
    
    private WarehouseEmployeeMenu() {
        initComponents();
                setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    }

    private int getWarehouseIdByEmployee(int employeeId) {
        int warehouseId = -1; // Varsayılan olarak -1 (atanmamış)
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT warehouse_id FROM users WHERE user_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, employeeId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                warehouseId = rs.getInt("warehouse_id");
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return warehouseId;

    
    }
   @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        üretimdenGelenÜrünler_btn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        üretimdenGelenÜrünler_btn.setText("Üretimden gelen ürünler");
        üretimdenGelenÜrünler_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                üretimdenGelenÜrünler_btnActionPerformed(evt);
            }
        });

        jButton1.setText("Customer Orders");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(150, 150, 150)
                .addComponent(üretimdenGelenÜrünler_btn)
                .addGap(171, 171, 171)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(114, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(üretimdenGelenÜrünler_btn)
                    .addComponent(jButton1))
                .addContainerGap(347, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void üretimdenGelenÜrünler_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_üretimdenGelenÜrünler_btnActionPerformed
        // TODO add your handling code here:
         if (warehouseId == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Depo bilgisi bulunamadı!");
        return;
    }
    
    // Sadece kendi deposuna ait üretim siparişlerini göstermek için warehouseId gönder
    new ProductsFromProduction(warehouseId).setVisible(true);

    }//GEN-LAST:event_üretimdenGelenÜrünler_btnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        new WarehouseGivenOrders().setVisible(true);
        
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(WarehouseEmployeeMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WarehouseEmployeeMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WarehouseEmployeeMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WarehouseEmployeeMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
        java.awt.EventQueue.invokeLater(() -> new WarehouseEmployeeMenu().setVisible(true));
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton üretimdenGelenÜrünler_btn;
    // End of variables declaration//GEN-END:variables
}

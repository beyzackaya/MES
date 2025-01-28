package mes;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import mes.Database.ProductionDatabase;
import static mes.Database.ProductionDatabase.updateProductionStatus;
import static mes.Database.ProductionDatabase.updateProductionStatus2;
import mes.model.Production;

public class givenWorkOrders extends javax.swing.JFrame {

    public givenWorkOrders() {
        initComponents();
        loadProductionTable(inProces_tbl, "In Production");
        loadProductionTable(givenWorkOrderstbl, "Pending");
        loadProductionTable(completed_tbl, "Completed");
    }

//    private void inProduction() {
//        loadProductionTable(inProces_tbl, "In Production");
//    }
//
//    private void Pending() {
//        loadProductionTable(inProces_tbl, "Pending");
//    }
//
//    private void completed() {
//        loadProductionTable(inProces_tbl, "Completed");
//    }
    private void updateProductionStatusForSelectedRow(JTable table, String status) {
        int selectedRow = table.getSelectedRow(); // Tablodaki seçili satırın index'i

        // Eğer bir satır seçilmişse
        if (selectedRow != -1) {
            // Seçilen satırdaki "Production ID" değerini alıyoruz
            int productionId = (int) table.getValueAt(selectedRow, 0); // '0' index, "Production ID" kolonunu temsil eder

            // Status güncelleme işlemini yapıyoruz
            boolean success = updateProductionStatus(productionId, status);

            if (success) {
                // İlgili tabloyu güncelliyoruz
                if ("In Production".equals(status)) {
                    loadProductionTable(inProces_tbl, "In Production");
                    loadProductionTable(givenWorkOrderstbl, "Pending");
                    loadProductionTable(completed_tbl, "Completed");
                    JOptionPane.showMessageDialog(this, "Production status updated to 'In Production'.");
                } else if ("Completed".equals(status)) {
                    loadProductionTable(inProces_tbl, "In Production");
                    loadProductionTable(givenWorkOrderstbl, "Pending");
                    loadProductionTable(completed_tbl, "Completed");

                    JOptionPane.showMessageDialog(this, "Production status updated to 'Completed'.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error updating production status.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a production to process.");
        }
    }

    private void updateProductionStatusForSelectedRow2(JTable table, String status) {
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            int productionId = (int) table.getValueAt(selectedRow, 0);

            boolean success = updateProductionStatus2(productionId, status);

            if (success) {
                if ("In Production".equals(status)) {
                    loadProductionTable(inProces_tbl, "In Production");
                    loadProductionTable(givenWorkOrderstbl, "Pending");
                    loadProductionTable(completed_tbl, "Completed");
                    JOptionPane.showMessageDialog(this, "Production status updated to 'In Production'.");
                } else if ("Completed".equals(status)) {
                    loadProductionTable(inProces_tbl, "In Production");
                    loadProductionTable(givenWorkOrderstbl, "Pending");
                    loadProductionTable(completed_tbl, "Completed");

                    JOptionPane.showMessageDialog(this, "Production status updated to 'Completed'.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error updating production status.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a production to process.");
        }
    }

    public void loadProductionTable(JTable table, String status) {
        try {
            ProductionDatabase productionDatabase = new ProductionDatabase();

            List<Production> productions = productionDatabase.getProductionsByStatus(status);

            if (productions.isEmpty()) {
                System.out.println("No productions found for status: " + status);
            }

            DefaultTableModel model = new DefaultTableModel(new String[]{
                "Production ID", "Product ID", "Quantity Produced", "Warehouse ID", "Raw Product ID", "Start Date", "End Date", "Status"
            }, 0);

            for (Production p : productions) {
                model.addRow(new Object[]{
                    p.getProductionId(),
                    p.getProductId(),
                    p.getQuantityProduced(),
                    p.getWarehouseId(),
                    p.getRawproductId(),
                    p.getStartDate(),
                    p.getEndDate(),
                    p.getStatus()
                });
            }

            table.setModel(model);

        } catch (Exception ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Error loading table: " + ex.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        pending_pnl = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        givenWorkOrderstbl = new javax.swing.JTable();
        process_btn = new javax.swing.JButton();
        inProcess_pnl = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        inProces_tbl = new javax.swing.JTable();
        completed_btn = new javax.swing.JButton();
        comleted_pnl = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        completed_tbl = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        givenWorkOrderstbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(givenWorkOrderstbl);

        process_btn.setText("İşleme al");
        process_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                process_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pending_pnlLayout = new javax.swing.GroupLayout(pending_pnl);
        pending_pnl.setLayout(pending_pnlLayout);
        pending_pnlLayout.setHorizontalGroup(
            pending_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pending_pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pending_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1006, Short.MAX_VALUE)
                    .addGroup(pending_pnlLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(process_btn)))
                .addGap(28, 28, 28))
        );
        pending_pnlLayout.setVerticalGroup(
            pending_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pending_pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(process_btn)
                .addGap(15, 15, 15))
        );

        jTabbedPane1.addTab("Pending", pending_pnl);

        inProces_tbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(inProces_tbl);

        completed_btn.setText("Completed");
        completed_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completed_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout inProcess_pnlLayout = new javax.swing.GroupLayout(inProcess_pnl);
        inProcess_pnl.setLayout(inProcess_pnlLayout);
        inProcess_pnlLayout.setHorizontalGroup(
            inProcess_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inProcess_pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(inProcess_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(completed_btn)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 987, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        inProcess_pnlLayout.setVerticalGroup(
            inProcess_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(inProcess_pnlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(completed_btn)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("In Process", inProcess_pnl);

        completed_tbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(completed_tbl);

        jLabel1.setText("Geçmiş İş Emirleri");

        javax.swing.GroupLayout comleted_pnlLayout = new javax.swing.GroupLayout(comleted_pnl);
        comleted_pnl.setLayout(comleted_pnlLayout);
        comleted_pnlLayout.setHorizontalGroup(
            comleted_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(comleted_pnlLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(comleted_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 995, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        comleted_pnlLayout.setVerticalGroup(
            comleted_pnlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(comleted_pnlLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Completed", comleted_pnl);

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

    private void process_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_process_btnActionPerformed
        updateProductionStatusForSelectedRow(givenWorkOrderstbl, "In Production");

    }//GEN-LAST:event_process_btnActionPerformed

    private void completed_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_completed_btnActionPerformed
        updateProductionStatusForSelectedRow2(inProces_tbl, "Completed");

    }//GEN-LAST:event_completed_btnActionPerformed

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
            java.util.logging.Logger.getLogger(givenWorkOrders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(givenWorkOrders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(givenWorkOrders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(givenWorkOrders.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new givenWorkOrders().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel comleted_pnl;
    private javax.swing.JButton completed_btn;
    private javax.swing.JTable completed_tbl;
    private javax.swing.JTable givenWorkOrderstbl;
    private javax.swing.JTable inProces_tbl;
    private javax.swing.JPanel inProcess_pnl;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel pending_pnl;
    private javax.swing.JButton process_btn;
    // End of variables declaration//GEN-END:variables
}

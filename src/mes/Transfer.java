package mes;

import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mes.Database.WarehouseStockDatabase;
import mes.Database.WarehouseDatabase;
import mes.Database.WarehouseTransferDAO;
import mes.model.WareHouse;

public class Transfer extends javax.swing.JFrame {

    private int selectedProductId;
    private String selectedProductName;

    public Transfer(int productId, String productName) {
        this.selectedProductId = productId;
        this.selectedProductName = productName;
        initComponents();
        setTitle("Transfer - " + productName);
        loadProductWarehouses();
        loadTargetWarehouses();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

    }

    private Transfer() {

    }

    private void loadProductWarehouses() {
        WarehouseStockDatabase warehouseDb = new WarehouseStockDatabase();
        List<Map<String, Object>> warehouses = warehouseDb.getWarehousesForProduct(selectedProductId);

        DefaultTableModel model = new DefaultTableModel(new String[]{
            "Depo Adı", "Stok Miktarı"
        }, 0);

        for (Map<String, Object> row : warehouses) {
            model.addRow(new Object[]{
                row.get("warehouse_name"),
                row.get("quantity_in_stock")
            });
        }

        stockStatus_tbl.setModel(model);
    }

    private void loadTargetWarehouses() {
        WarehouseDatabase warehouseDb = new WarehouseDatabase();
        List<WareHouse> warehouses = warehouseDb.getAllWarehouses();

        Warehouse_combox.removeAllItems();
        for (WareHouse w : warehouses) {
            Warehouse_combox.addItem(w.getWarehouseName());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        stockStatus_tbl = new javax.swing.JTable();
        stock_txt = new javax.swing.JTextField();
        Transfer_btn = new javax.swing.JButton();
        Warehouse_combox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        stockStatus_tbl.setModel(new javax.swing.table.DefaultTableModel(
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
        stockStatus_tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                stockStatus_tblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(stockStatus_tbl);

        Transfer_btn.setText("Transfer");
        Transfer_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Transfer_btnActionPerformed(evt);
            }
        });

        Warehouse_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Quantity");

        jLabel2.setText("Warehouse to Ship");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(Transfer_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(33, 33, 33))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(1, 1, 1))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Warehouse_combox, 0, 140, Short.MAX_VALUE)
                            .addComponent(stock_txt))
                        .addGap(14, 14, 14))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(stock_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Warehouse_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(41, 41, 41)
                        .addComponent(Transfer_btn)))
                .addContainerGap(25, Short.MAX_VALUE))
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

    private void Transfer_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Transfer_btnActionPerformed
        {
             int selectedRow = stockStatus_tbl.getSelectedRow();
    
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Lütfen hangi depodan transfer edileceğini seçin!");
        return;
    }

    String fromWarehouseName = (String) stockStatus_tbl.getValueAt(selectedRow, 0); // Kaynak depo adı
    int availableStock = (int) stockStatus_tbl.getValueAt(selectedRow, 1); // Kaynak depodaki mevcut stok

    String toWarehouseName = Warehouse_combox.getSelectedItem().toString(); // Hedef depo adı
    int transferQuantity;

    try {
        transferQuantity = Integer.parseInt(stock_txt.getText().trim());
        if (transferQuantity <= 0) {
            JOptionPane.showMessageDialog(this, "Transfer miktarı pozitif bir sayı olmalıdır.");
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Lütfen geçerli bir miktar girin.");
        return;
    }

    if (transferQuantity > availableStock) {
        JOptionPane.showMessageDialog(this, "Yetersiz stok! Transfer işlemi yapılamaz.");
        return;
    }

    WarehouseDatabase warehouseDb = new WarehouseDatabase();
    WarehouseStockDatabase stockDb = new WarehouseStockDatabase();
    WarehouseTransferDAO transferDb = new WarehouseTransferDAO();

    int fromWarehouseId = warehouseDb.getWarehouseIdByName(fromWarehouseName);
    int toWarehouseId = warehouseDb.getWarehouseIdByName(toWarehouseName);

    if (fromWarehouseId == toWarehouseId) {
        JOptionPane.showMessageDialog(this, "Aynı depoya transfer yapılamaz!");
        return;
    }

    boolean transferSuccess = transferDb.addTransfer(selectedProductId, fromWarehouseId, toWarehouseId, transferQuantity);
    
    if (transferSuccess) {
        stockDb.updateWarehouseStock(selectedProductId, fromWarehouseId, -transferQuantity);
        
        stockDb.updateWarehouseStock(selectedProductId, toWarehouseId, transferQuantity);

        JOptionPane.showMessageDialog(this, "Transfer başarıyla gerçekleştirildi!");
        loadProductWarehouses(); // Tabloyu güncelle
    } else {
        JOptionPane.showMessageDialog(this, "Transfer işlemi başarısız!");
    }

//        int selectedRow = stockStatus_tbl.getSelectedRow();
//        String fromWarehouseName = (String) stockStatus_tbl.getValueAt(selectedRow, 0);  // Depo adı
//        int stockQuantity = (int) stockStatus_tbl.getValueAt(selectedRow, 1);  // Mevcut stok miktarı
//
//        String toWarehouseName = (String) Warehouse_combox.getSelectedItem();
//        int transferQuantity = Integer.parseInt(stock_txt.getText());
//
//        if (fromWarehouseName.equals(toWarehouseName)) {
//            JOptionPane.showMessageDialog(this, "Kaynak depo ile hedef depo aynı olamaz. Lütfen farklı depolar seçiniz.");
//            return;
//        }
//
//        if (transferQuantity > 0 && transferQuantity <= stockQuantity) {
//            WarehouseDatabase warehouseDb = new WarehouseDatabase();
//            int fromWarehouseId = warehouseDb.getWarehouseIdByName(fromWarehouseName);
//            int toWarehouseId = warehouseDb.getWarehouseIdByName(toWarehouseName);
//
//            boolean isTransferred = warehouseDb.processTransfer(selectedProductId, fromWarehouseId, toWarehouseId, transferQuantity);
//
//            if (isTransferred) {
//                JOptionPane.showMessageDialog(this, "Transfer işlemi başarıyla başlatıldı.");
//                run();
//            } else {
//                JOptionPane.showMessageDialog(this, "Transfer işlemi başarısız oldu.");
//            }
//        } else {
//            JOptionPane.showMessageDialog(this, "Geçerli bir miktar giriniz.");
    }             }//GEN-LAST:event_Transfer_btnActionPerformed

    private void stockStatus_tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stockStatus_tblMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_stockStatus_tblMouseClicked

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
            java.util.logging.Logger.getLogger(Transfer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Transfer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Transfer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Transfer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                new Transfer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Transfer_btn;
    private javax.swing.JComboBox<String> Warehouse_combox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable stockStatus_tbl;
    private javax.swing.JTextField stock_txt;
    // End of variables declaration//GEN-END:variables
}

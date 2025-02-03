package mes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        rawproducts_tbl = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        color_combox = new javax.swing.JComboBox<>();
        name_txt = new javax.swing.JTextField();
        stock_combox = new javax.swing.JComboBox<>();
        Filter_btn = new javax.swing.JButton();
        clearFilters_btn = new javax.swing.JButton();
        delete_btn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        color_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(name_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(clearFilters_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Filter_btn)
                    .addComponent(stock_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(color_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delete_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(name_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(color_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(stock_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(Filter_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clearFilters_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(delete_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        // TODO add your handling code here:
                new NewRawMaterial().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed
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
    private javax.swing.JButton delete_btn;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField name_txt;
    private javax.swing.JTable rawproducts_tbl;
    private javax.swing.JComboBox<String> stock_combox;
    // End of variables declaration//GEN-END:variables
}

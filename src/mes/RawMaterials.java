
package mes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class RawMaterials extends javax.swing.JFrame {

    public RawMaterials() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        loadColor();
        loadTable("");

    }
        private void loadTable(String filterQuery) {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM raw_material WHERE 1=1 " + filterQuery;
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel(new String[]{
                "Raw Product ID", "Name","Color", "Stock"}, 0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("rawproduct_id"),
                    rs.getString("rawproduct_name"),
                    rs.getString("rawproduct_color"),
                    rs.getString("rawproduct_stock"),
                });
            }

            rawproducts_tbl.setModel(model);

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Error loading table: " + ex.getMessage());
        }
    }

        private void loadColor() {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String query = "SELECT rawproduct_color FROM raw_material";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            color_combox.removeAllItems();

            while (rs.next()) {
                String color = rs.getString("rawproduct_color");
                color_combox.addItem(color);
            }

            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Kategoriler yüklenirken bir hata oluştu: " + ex.getMessage());
        }
    }
        
        private void filter() {
        String query = "SELECT * FROM raw_material WHERE 1=1"; 

        if (!name_txt.getText().isEmpty()) {
            query += " AND rawproduct_name LIKE '%" + name_txt.getText() + "%'"; // LIKE kullanarak arama
        }

        if (color_combox.getSelectedItem() != null && !color_combox.getSelectedItem().toString().isEmpty()) {
            String selectedColor = color_combox.getSelectedItem().toString();
            query += " AND rawproduct_color = '" + selectedColor + "'"; // Kategoriye göre filtrele
        }

        if (!stock_combox.getSelectedItem().toString().isEmpty()) {
            if (stock_combox.getSelectedItem().toString().equals("In Stock")) {
                query += " AND rawproduct_stock > 0";
            } else if (stock_combox.getSelectedItem().toString().equals("Out of Stock")) {
                query += " AND rawproduct_stock <= 0";
            } else if (stock_combox.getSelectedItem().toString().equals("Low Stock")) {
                query += " AND rawproduct_stock <= 3";
            }
        }

        try (Connection conn = DatabaseConnector.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) rawproducts_tbl.getModel();
            model.setRowCount(0); // Tabloyu sıfırla

            while (rs.next()) {
                Object[] row = new Object[rawproducts_tbl.getColumnCount()];
                for (int i = 0; i < rawproducts_tbl.getColumnCount(); i++) {
                    row[i] = rs.getObject(i + 1); // Veriyi alıp satırlara ekle
                }
                model.addRow(row);
            }

            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(clearFilters_btn)
                    .addComponent(Filter_btn)
                    .addComponent(stock_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(color_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(53, Short.MAX_VALUE))
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
        // TODO add your handling code here:
        loadTable("");
    }//GEN-LAST:event_clearFilters_btnActionPerformed

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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField name_txt;
    private javax.swing.JTable rawproducts_tbl;
    private javax.swing.JComboBox<String> stock_combox;
    // End of variables declaration//GEN-END:variables
}

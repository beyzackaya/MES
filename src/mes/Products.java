/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package mes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Products extends javax.swing.JFrame {

    public Products() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        loadTable("");
        loadCategories();
        loadGenders();
        loadColor();
    }

    private void loadCategories() {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String query = "SELECT product_category FROM products";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            category_combox.removeAllItems();

            while (rs.next()) {
                String categoryName = rs.getString("product_category");
                category_combox.addItem(categoryName);
            }

            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Kategoriler yüklenirken bir hata oluştu: " + ex.getMessage());
        }
    }

    private void loadGenders() {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String query = "SHOW COLUMNS FROM products LIKE 'product_gender'";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            gender_combox.removeAllItems();

            if (rs.next()) {
                String enumType = rs.getString("Type");
                String[] enumValues = enumType.substring(enumType.indexOf('(') + 1, enumType.indexOf(')')).split(",");
                for (String value : enumValues) {
                    gender_combox.addItem(value.trim().replace("'", ""));
                }
            }

            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Kategoriler yüklenirken bir hata oluştu: " + ex.getMessage());
        }
    }

    private void loadColor() {
        try (Connection conn = DatabaseConnector.getConnection()) {
            String query = "SELECT product_color FROM products";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            color_combox.removeAllItems();

            while (rs.next()) {
                String colorName = rs.getString("product_color");
                color_combox.addItem(colorName);
            }

            rs.close();
            pstmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Kategoriler yüklenirken bir hata oluştu: " + ex.getMessage());
        }
    }

    private void filter() {
        String query = "SELECT * FROM products WHERE 1=1"; 

        if (!productname_txt.getText().isEmpty()) {
            query += " AND product_name LIKE '%" + productname_txt.getText() + "%'"; // LIKE kullanarak arama
        }

        if (category_combox.getSelectedItem() != null && !category_combox.getSelectedItem().toString().isEmpty()) {
            String selectedCategory = category_combox.getSelectedItem().toString();
            query += " AND product_category = '" + selectedCategory + "'"; // Kategoriye göre filtrele
        }

        if (color_combox.getSelectedItem() != null && !color_combox.getSelectedItem().toString().isEmpty()) {
            String selectedColor = color_combox.getSelectedItem().toString();
            query += " AND product_color = '" + selectedColor + "'";
        }

        if (gender_combox.getSelectedItem() != null && !gender_combox.getSelectedItem().toString().isEmpty()) {
            String selectedGender = gender_combox.getSelectedItem().toString();
            query += " AND product_gender = '" + selectedGender + "'"; // Cinsiyete göre filtrele
        }
        if (!stock_combox.getSelectedItem().toString().isEmpty()) {
            if (stock_combox.getSelectedItem().toString().equals("In Stock")) {
                query += " AND product_stock > 0";
            } else if (stock_combox.getSelectedItem().toString().equals("Out of Stock")) {
                query += " AND product_stock <= 0";
            } else if (stock_combox.getSelectedItem().toString().equals("Low Stock")) {
                query += " AND product_stock <= 3";
            }
        }

        try (Connection conn = DatabaseConnector.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            DefaultTableModel model = (DefaultTableModel) products_tbl.getModel();
            model.setRowCount(0); // Tabloyu sıfırla

            while (rs.next()) {
                Object[] row = new Object[products_tbl.getColumnCount()];
                for (int i = 0; i < products_tbl.getColumnCount(); i++) {
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

        menuBar1 = new java.awt.MenuBar();
        menu1 = new java.awt.Menu();
        menu2 = new java.awt.Menu();
        popupMenu1 = new java.awt.PopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        products_tbl = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        clearFilters_combox = new javax.swing.JButton();
        filter_btn = new javax.swing.JButton();
        color_combox = new javax.swing.JComboBox<>();
        stock_combox = new javax.swing.JComboBox<>();
        category_combox = new javax.swing.JComboBox<>();
        gender_combox = new javax.swing.JComboBox<>();
        productname_txt = new javax.swing.JTextField();

        menu1.setLabel("File");
        menuBar1.add(menu1);

        menu2.setLabel("Edit");
        menuBar1.add(menu2);

        popupMenu1.setLabel("popupMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        products_tbl.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(products_tbl);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(35, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        clearFilters_combox.setText("Clear Filters");
        clearFilters_combox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearFilters_comboxActionPerformed(evt);
            }
        });

        filter_btn.setText("apply");
        filter_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filter_btnActionPerformed(evt);
            }
        });

        color_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        stock_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ","Low Stock", "In Stock", "Out of Stock"}));

        category_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Products", "Raw Material" }));

        gender_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(clearFilters_combox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(filter_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(stock_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(color_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(category_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gender_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(48, 48, 48))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(productname_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(gender_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(category_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(color_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(stock_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(productname_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(filter_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clearFilters_combox)
                .addGap(27, 27, 27))
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

    private void filter_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filter_btnActionPerformed
        // TODO add your handling code here:
        filter();

    }//GEN-LAST:event_filter_btnActionPerformed

    private void clearFilters_comboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearFilters_comboxActionPerformed
        // TODO add your handling code here:
        loadTable("");
    }//GEN-LAST:event_clearFilters_comboxActionPerformed

    private void loadTable(String filterQuery) {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SELECT * FROM products WHERE 1=1 " + filterQuery; // Filtreyi burada kullanacağız
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            // JTable'ın modelini oluşturun
            DefaultTableModel model = new DefaultTableModel(new String[]{
                "Product ID", "Name", "Gender", "Color", "Stock", "Category"
            }, 0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getString("product_gender"),
                    rs.getString("product_color"),
                    rs.getString("product_stock"),
                    rs.getString("product_category")
                });
            }

            products_tbl.setModel(model);

            rs.close();
            pstmt.close();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Error loading table: " + ex.getMessage());
        }
    }

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
            java.util.logging.Logger.getLogger(Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Products.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Products().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> category_combox;
    private javax.swing.JButton clearFilters_combox;
    private javax.swing.JComboBox<String> color_combox;
    private javax.swing.JButton filter_btn;
    private javax.swing.JComboBox<String> gender_combox;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private java.awt.Menu menu1;
    private java.awt.Menu menu2;
    private java.awt.MenuBar menuBar1;
    private java.awt.PopupMenu popupMenu1;
    private javax.swing.JTextField productname_txt;
    private javax.swing.JTable products_tbl;
    private javax.swing.JComboBox<String> stock_combox;
    // End of variables declaration//GEN-END:variables
}

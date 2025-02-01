package mes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import mes.Database.DatabaseConnector;
import mes.Database.ProductDatabase;
import mes.model.Product;


public class Products extends javax.swing.JFrame {

    public Products() {
        initComponents();
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        loadTable();
        loadCategories();
        loadGenders();
        loadColor();
    }

    private void loadCategories() {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SHOW COLUMNS FROM products LIKE 'product_category';";
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

            category_combox.removeAllItems();
            category_combox.addItem("");

            for (String category : categories) {
                category_combox.addItem(category);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadGenders() {
        try {
            Connection conn = DatabaseConnector.getConnection();
            String sql = "SHOW COLUMNS FROM products LIKE 'product_gender';";
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

            gender_combox.removeAllItems();
            gender_combox.addItem("");

            for (String category : categories) {
                gender_combox.addItem(category);
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String capitalizeFirstLetter(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    private void loadColor() {
        try {
            ProductDatabase productDatabase = new ProductDatabase();
            List<Product> products = productDatabase.getAllProducts();

            Set<String> colors = new HashSet<>();

            for (Product p : products) {
                if (p.getProductColor() != null && !p.getProductColor().isEmpty()) {
                    colors.add(p.getProductColor().toLowerCase());
                }
            }

            color_combox.removeAllItems();
            color_combox.addItem("");
            for (String color : colors) {
                color_combox.addItem(capitalizeFirstLetter(color));
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Renkler yüklenirken bir hata oluştu: " + ex.getMessage());
        }
    }

    private void deleteProduct() {
        int selectedRow = products_tbl.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Lütfen silmek için bir ürün seçin!");
            return;
        }

        int productId = (int) products_tbl.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "Bu ürünü silmek istediğinize emin misiniz?",
                "Onay", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                ProductDatabase db = new ProductDatabase();
                boolean success = db.deleteProductById(productId);

                if (success) {
                    JOptionPane.showMessageDialog(this, "Ürün ve ilgili ham madde bilgileri başarıyla silindi!");
                    DefaultTableModel model = (DefaultTableModel) products_tbl.getModel();
                    model.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(this, "Ürün silinemedi! Veritabanı hatası olabilir.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Silme işlemi sırasında hata oluştu: " + e.getMessage());
            }
        }
    }

    private void filter() {
        String productName = productname_txt.getText().trim().toLowerCase();
        String productCategory = category_combox.getSelectedItem() != null ? category_combox.getSelectedItem().toString().trim() : "";
        String productColor = color_combox.getSelectedItem() != null ? color_combox.getSelectedItem().toString().trim() : "";
        String productGender = gender_combox.getSelectedItem() != null ? gender_combox.getSelectedItem().toString().trim() : "";
        String productStockStatus = stock_combox.getSelectedItem() != null ? stock_combox.getSelectedItem().toString().trim() : "";

        try {
            ProductDatabase productDatabase = new ProductDatabase();
            List<Product> allProducts = productDatabase.getAllProducts();

            List<Product> filteredProducts = new ArrayList<>();
            for (Product p : allProducts) {
                boolean matches = true;

                if (!productName.isEmpty() && !p.getProductName().toLowerCase().contains(productName)) {
                    matches = false;
                }

                if (!productCategory.isEmpty() && !productCategory.equalsIgnoreCase("") && !p.getProductCategory().equalsIgnoreCase(productCategory)) {
                    matches = false;
                }

                if (!productColor.isEmpty() && !productColor.equalsIgnoreCase("") && !p.getProductColor().equalsIgnoreCase(productColor)) {
                    matches = false;
                }

                if (!productGender.isEmpty() && !productGender.equalsIgnoreCase("") && !p.getProductGender().equalsIgnoreCase(productGender)) {
                    matches = false;
                }

                if (!productStockStatus.isEmpty() && !productStockStatus.equalsIgnoreCase("")) {
                    switch (productStockStatus) {
                        case "In Stock":
                            if (p.getProductStock() <= 0) {
                                matches = false;
                            }
                            break;
                        case "Out of Stock":
                            if (p.getProductStock() > 0) {
                                matches = false;
                            }
                            break;
                        case "Low Stock":
                            if (p.getProductStock() > 3) {
                                matches = false;
                            }
                            break;
                    }
                }

                if (matches) {
                    filteredProducts.add(p);
                }
            }

            DefaultTableModel model = (DefaultTableModel) products_tbl.getModel();
            model.setRowCount(0);

            for (Product p : filteredProducts) {
                model.addRow(new Object[]{
                    p.getProductId(),
                    p.getProductName(),
                    capitalizeFirstLetter(p.getProductGender()),
                    capitalizeFirstLetter(p.getProductColor()),
                    p.getProductStock(),
                    capitalizeFirstLetter(p.getProductCategory()),
                    p.getProductPrice()
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
        addProduct_btn = new javax.swing.JButton();
        delete_btn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        transfer_btn = new javax.swing.JButton();

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
        products_tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                products_tblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(products_tbl);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 646, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58))
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        clearFilters_combox.setText("Clear Filters");
        clearFilters_combox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearFilters_comboxActionPerformed(evt);
            }
        });

        filter_btn.setText("Filter");
        filter_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filter_btnActionPerformed(evt);
            }
        });

        color_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        stock_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ","Low Stock", "In Stock", "Out of Stock"}));

        category_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Products", "Raw Material" }));

        gender_combox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Item 2", "Item 3", "Item 4" }));

        addProduct_btn.setText("Add Product");
        addProduct_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addProduct_btnActionPerformed(evt);
            }
        });

        delete_btn.setText("Delete");
        delete_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_btnActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel1.setText("Name");

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel2.setText("Gender");

        jLabel3.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel3.setText("Color");

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel4.setText("Stock");

        jLabel5.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        jLabel5.setText("Category");

        transfer_btn.setText("Transfer");
        transfer_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transfer_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(transfer_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(addProduct_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(36, 36, 36)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(gender_combox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(productname_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel2)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(category_combox, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(filter_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(stock_combox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(color_combox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(clearFilters_combox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addComponent(delete_btn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(97, 97, 97)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(productname_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gender_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(category_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(color_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(stock_combox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addComponent(filter_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearFilters_combox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 120, Short.MAX_VALUE)
                .addComponent(transfer_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(delete_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addProduct_btn)
                .addGap(111, 111, 111))
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
        filter();

    }//GEN-LAST:event_filter_btnActionPerformed

    private void clearFilters_comboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearFilters_comboxActionPerformed
        loadTable();
    }//GEN-LAST:event_clearFilters_comboxActionPerformed

    private void addProduct_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addProduct_btnActionPerformed
        // TODO add your handling code here:
        new newProduct().setVisible(true);

    }//GEN-LAST:event_addProduct_btnActionPerformed

    private void delete_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_btnActionPerformed
        deleteProduct();
    }//GEN-LAST:event_delete_btnActionPerformed

    private void transfer_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transfer_btnActionPerformed
        // TODO add your handling code here:
         int selectedRow = products_tbl.getSelectedRow();
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(this, "Lütfen bir ürün seçin!");
        return;
    }

    int productId = (int) products_tbl.getValueAt(selectedRow, 0);
    String productName = (String) products_tbl.getValueAt(selectedRow, 1);

    Transfer transferForm = new Transfer(productId, productName);
    transferForm.setVisible(true);
        
    }//GEN-LAST:event_transfer_btnActionPerformed

    private void products_tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_products_tblMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_products_tblMouseClicked
    

    void loadTable() {
        try {
            ProductDatabase productDatabase = new ProductDatabase();
            List<Product> products = productDatabase.getAllProducts();

            DefaultTableModel model = new DefaultTableModel(new String[]{
                "Product ID", "Name", "Gender", "Color", "Stock", "Category", "Price"
            }, 0);

            for (Product p : products) {
                model.addRow(new Object[]{
                    p.getProductId(),
                    p.getProductName(),
                    p.getProductGender(),
                    p.getProductColor(),
                    p.getProductStock(),
                    p.getProductCategory(),
                    p.getProductPrice()
                });
            }

            products_tbl.setModel(model);

        } catch (Exception ex) {
            ex.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Error loading table: " + ex.getMessage());
        }
    }

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Product.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Products().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addProduct_btn;
    private javax.swing.JComboBox<String> category_combox;
    private javax.swing.JButton clearFilters_combox;
    private javax.swing.JComboBox<String> color_combox;
    private javax.swing.JButton delete_btn;
    private javax.swing.JButton filter_btn;
    private javax.swing.JComboBox<String> gender_combox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
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
    private javax.swing.JButton transfer_btn;
    // End of variables declaration//GEN-END:variables
}
